package de.larmic.es.elasticsearch;

public record TweetDocument(String message) {

    public static String documentIndex = "twitter";

}
