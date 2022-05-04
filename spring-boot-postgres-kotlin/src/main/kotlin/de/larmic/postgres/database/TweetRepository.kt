package de.larmic.postgres.database

import de.larmic.postgres.database.model.TweetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TweetRepository : JpaRepository<TweetEntity?, Long?>