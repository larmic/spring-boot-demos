package de.neusta.springbootmongodb.database.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Document("events")
public class EventEntity {

    @Id
    private UUID id;

    private LocalDateTime createDate;

    private String eventId;

    private String event;
}
