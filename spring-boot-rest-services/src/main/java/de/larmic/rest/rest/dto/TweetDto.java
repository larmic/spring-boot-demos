package de.larmic.rest.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TweetDto {

    private final String id;
    private final String message;

    @JsonCreator
    public TweetDto(@JsonProperty("id") String id, @JsonProperty("message") String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
