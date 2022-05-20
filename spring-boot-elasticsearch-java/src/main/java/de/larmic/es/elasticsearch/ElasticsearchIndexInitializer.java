package de.larmic.es.elasticsearch;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

import static de.larmic.es.elasticsearch.TweetDocument.documentIndex;

@Component
public class ElasticsearchIndexInitializer implements CommandLineRunner {

    private final RestHighLevelClient restHighLevelClient;
    private final Environment environment;

    public ElasticsearchIndexInitializer(final RestHighLevelClient restHighLevelClient, final Environment environment) {
        this.restHighLevelClient = restHighLevelClient;
        this.environment = environment;
    }

    @Override
    public void run(String... args) throws IOException {
        if (!runningInTestMode()) {
            createIndexIfNotExists(documentIndex);
        }
    }

    private boolean runningInTestMode() {
        return Arrays.stream(environment.getActiveProfiles()).toList().contains("it");
    }

    private void createIndexIfNotExists(final String index) throws IOException {
        if (indexDoesNotExists(index)) {
            // see https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.x/java-rest-high-create-index.html
            final var createIndexRequest = new CreateIndexRequest(index);
            // mapping will be created automatically with first document index
            //createIndexRequest.mapping(type, "message", "type=text")
            restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        }
    }

    private boolean indexDoesNotExists(String index) throws IOException {
        return !restHighLevelClient.indices().exists(new GetIndexRequest(index), RequestOptions.DEFAULT);
    }
}
