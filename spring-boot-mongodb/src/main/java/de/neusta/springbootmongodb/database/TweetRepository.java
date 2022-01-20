package de.neusta.springbootmongodb.database;

import de.neusta.springbootmongodb.database.model.TweetEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TweetRepository extends MongoRepository<TweetEntity, UUID> {
}
