package de.larmic.es.elasticsearch;

import de.larmic.es.testing.ElasticsearchContextInitializer;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@ContextConfiguration(initializers = {ElasticsearchContextInitializer.class})
class TweetRepositoryIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private TweetRepository tweetRepository;

    @BeforeEach
    public void setUp() throws IOException {
        deleteIndexIfExists(restHighLevelClient);
        createIndex(restHighLevelClient);
    }

    @Test
    public void postATweet() throws IOException {
        final var response = testRestTemplate.exchange("/", HttpMethod.POST, new HttpEntity<>("simple tweet"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        refreshIndex(restHighLevelClient);

        final var tweet = tweetRepository.getTweet(response.getBody());

        assertThat(tweet).isPresent();
        assertThat(tweet.get().message()).isEqualTo("simple tweet");
    }

    private void refreshIndex(RestHighLevelClient client) throws IOException {
        final var request = new RefreshRequest(TweetDocument.documentIndex);
        client.indices().refresh(request, RequestOptions.DEFAULT);
    }

    private void createIndex(RestHighLevelClient client) throws IOException {
        client.indices().create(new CreateIndexRequest(TweetDocument.documentIndex), RequestOptions.DEFAULT);
    }

    private void deleteIndexIfExists(RestHighLevelClient client) throws IOException {
        if (this.indexExists(client, TweetDocument.documentIndex)) {
            client.indices().delete(new DeleteIndexRequest(TweetDocument.documentIndex), RequestOptions.DEFAULT);
        }
    }

    private boolean indexExists(RestHighLevelClient client, String index) throws IOException {
        return client.indices().exists(new GetIndexRequest(index), RequestOptions.DEFAULT);
    }
}