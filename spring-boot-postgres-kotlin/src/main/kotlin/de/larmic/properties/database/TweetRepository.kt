package de.larmic.properties.database

import de.larmic.properties.database.model.TweetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TweetRepository : JpaRepository<TweetEntity?, Long?>