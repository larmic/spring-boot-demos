package de.larmic.springbootmongodb.database.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Document("tweet")
public class TweetEntity {

    @Id
    private UUID id;

    private String message;

    private LocalDateTime createDate = LocalDateTime.now();

    private LocalDateTime lastUpdateDate = LocalDateTime.now();

    public TweetEntity(final String message) {
        this.message = message;
        this.id = UUID.randomUUID();
    }
}
