package de.larmic.es.rest;

import de.larmic.es.elasticsearch.TweetRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TweetController {

    private final TweetRepository tweetRepository;

    public TweetController(final TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @PostMapping("/")
    public String tweet(@RequestBody String tweet) throws IOException {
        return tweetRepository.storeTweet(tweet);
    }
}
