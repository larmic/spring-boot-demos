package de.neusta.springbootmongodb.database;

import de.neusta.springbootmongodb.database.model.EventEntity;
import de.neusta.springbootmongodb.testcontainers.MongoDbTestSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MongoDbIT extends MongoDbTestSetup {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void saveEventEntityToDb() {
        UUID id = UUID.randomUUID();
        LocalDateTime createDate = LocalDateTime.now();
        String eventId = "test-event";
        String event = "{\"type\": \"test-event\"}";
        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(id);
        eventEntity.setCreateDate(createDate);
        eventEntity.setEventId(eventId);
        eventEntity.setEvent(event);

        eventRepository.save(eventEntity);

        assertTrue(eventRepository.findById(id).isPresent());

        EventEntity retrievedEventEntity = eventRepository.findById(id).get();

        assertThat(retrievedEventEntity.getCreateDate()).isCloseTo(createDate, within(1, ChronoUnit.SECONDS));
        assertThat(retrievedEventEntity.getEventId()).isEqualTo(eventId);
        assertThat(retrievedEventEntity.getEvent()).isEqualTo(event);
    }
}
