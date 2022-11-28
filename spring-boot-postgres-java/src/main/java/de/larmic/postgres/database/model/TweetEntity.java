package de.larmic.postgres.database.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tweet")
public class TweetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tweet_generator")
    @SequenceGenerator(name = "tweet_generator", sequenceName = "tweet_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "last_update_date", nullable = false)
    private LocalDateTime lastUpdateDate = LocalDateTime.now();

    public TweetEntity() {
        // required by jpa
    }

    public TweetEntity(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
