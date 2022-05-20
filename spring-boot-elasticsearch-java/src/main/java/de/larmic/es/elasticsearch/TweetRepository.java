package de.larmic.es.elasticsearch;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.larmic.es.rest.Tweet;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Optional;

import static de.larmic.es.elasticsearch.TweetDocument.documentIndex;

@Repository
public class TweetRepository {

    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public TweetRepository(final RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    public String storeTweet(String tweet) throws IOException {
        final var document = new TweetDocument(tweet);
        final var writeValueAsString = mapper.writeValueAsString(document);
        final var request = new IndexRequest(documentIndex);
        request.source(writeValueAsString, XContentType.JSON);
        return restHighLevelClient.index(request, RequestOptions.DEFAULT).getId();
    }

    public Optional<Tweet> getTweet(String id) throws IOException {
        final var getRequest = new GetRequest(documentIndex, id);
        final var getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

        if (getResponse.isExists()) {
            final var sourceAsString = getResponse.getSourceAsString();
            final var tweetDocument = mapper.readValue(sourceAsString, TweetDocument.class);
            return Optional.of(new Tweet(getResponse.getId(), tweetDocument.message()));
        } else {
            return Optional.empty();
        }
    }
}
