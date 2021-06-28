package de.larmic.rest.rest;

import de.larmic.rest.rest.dto.TweetDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryRepository {

    public final Map<String, TweetDto> tweets = new HashMap<>();

}
