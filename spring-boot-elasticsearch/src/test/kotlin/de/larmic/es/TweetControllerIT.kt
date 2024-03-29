package de.larmic.es

import co.elastic.clients.elasticsearch.ElasticsearchClient
import de.larmic.es.elasticsearch.TweetRepository
import de.larmic.es.rest.Tweet
import de.larmic.testing.ElasticsearchContextInitializer
import de.larmic.testing.createIndex
import de.larmic.testing.deleteIndexIfExists
import de.larmic.testing.refreshIndex
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@ContextConfiguration(initializers = [ElasticsearchContextInitializer::class])
class TweetControllerIT {

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    private lateinit var esClient: ElasticsearchClient

    @Autowired
    private lateinit var tweetRepository: TweetRepository

    @BeforeEach
    fun setUp() {
        esClient.deleteIndexIfExists().createIndex()
    }

    @Test
    fun `post a tweet`() {
        val response = testRestTemplate.exchange("/", HttpMethod.POST, HttpEntity("simple tweet"), String::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        esClient.refreshIndex()

        val tweet = tweetRepository.getTweet(response.body!!)!!
        assertThat(tweet.message).isEqualTo("simple tweet")
    }

    @Test
    fun `get a tweet`() {
        val tweetId = tweetRepository.storeTweet("other tweet")

        esClient.refreshIndex()

        val response = testRestTemplate.exchange("/$tweetId", HttpMethod.GET, null, Tweet::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!.message).isEqualTo("other tweet")
    }

    @Test
    fun `get a not existing tweet`() {
        val response = testRestTemplate.exchange("/not-existing", HttpMethod.GET, null, Tweet::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(response.body).isNull()
    }

    @Test
    fun `get all tweets`() {
        val tweet1Id = tweetRepository.storeTweet("some tweet 1")

        esClient.refreshIndex()

        val response = testRestTemplate.exchange("/", HttpMethod.GET, null, object : ParameterizedTypeReference<List<Tweet>>() {})
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!![0].id).isEqualTo(tweet1Id)
        assertThat(response.body!![0].message).isEqualTo("some tweet 1")
    }

    @Test
    fun `get all tweets with no tweet exists`() {
        val response = testRestTemplate.exchange("/", HttpMethod.GET, null, object : ParameterizedTypeReference<List<Tweet>>() {})
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEmpty()
    }

    @Test
    fun `delete a tweet`() {
        val tweetId = tweetRepository.storeTweet("tweet to delete")

        esClient.refreshIndex()

        val response = testRestTemplate.exchange("/$tweetId", HttpMethod.DELETE, null, TweetRepository.RestStatus::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!).isEqualTo(TweetRepository.RestStatus.OK)

        esClient.refreshIndex()

        assertThat(tweetRepository.getTweet(tweetId)).isNull()
    }

    @Test
    fun `delete a not existing tweet`() {
        val response = testRestTemplate.exchange("/not-existing", HttpMethod.DELETE, null, TweetRepository.RestStatus::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!).isEqualTo(TweetRepository.RestStatus.NOT_FOUND)
    }

    @Test
    fun `update a tweet`() {
        val tweetId = tweetRepository.storeTweet("tweet to change")

        esClient.refreshIndex()

        val response = testRestTemplate.exchange("/$tweetId", HttpMethod.PUT, HttpEntity("tweet changed"), TweetRepository.RestStatus::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!).isEqualTo(TweetRepository.RestStatus.OK)

        esClient.refreshIndex()

        assertThat(tweetRepository.getTweet(tweetId)!!.message).isEqualTo("tweet changed")
    }

    @Test
    fun `update a not existing tweet`() {
        val response = testRestTemplate.exchange("/not-existing", HttpMethod.PUT, HttpEntity("tweet changed"), TweetRepository.RestStatus::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!).isEqualTo(TweetRepository.RestStatus.NOT_FOUND)
    }
}