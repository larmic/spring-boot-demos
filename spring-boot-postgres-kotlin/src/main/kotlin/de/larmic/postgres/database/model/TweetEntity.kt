package de.larmic.postgres.database.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tweet")
class TweetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tweet_generator")
    @SequenceGenerator(name = "tweet_generator", sequenceName = "tweet_id_seq", allocationSize = 1)
    val id: Long = 0L,

    @Column(name = "message", nullable = false)
    var message: String,

    @Column(name = "create_date", nullable = false)
    val createDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "last_update_date", nullable = false)
    var lastUpdateDate: LocalDateTime = LocalDateTime.now(),
)