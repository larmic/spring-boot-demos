package de.larmic.postgres.rest.dto;

public class TweetDto {

    private final String id;
    private final String message;

    public TweetDto(String id, String message) {
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
