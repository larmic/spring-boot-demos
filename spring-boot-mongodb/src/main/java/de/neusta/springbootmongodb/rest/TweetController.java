package de.neusta.springbootmongodb.rest;

import de.neusta.springbootmongodb.database.TweetRepository;
import de.neusta.springbootmongodb.database.model.TweetEntity;
import de.neusta.springbootmongodb.rest.dto.TweetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TweetController {

    private final TweetRepository tweetRepository;

    @PostMapping("/")
    public TweetDto tweet(@RequestBody final String message) {
        final var entity = this.tweetRepository.save(new TweetEntity(message));

        return mapToDto(entity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetDto> readTweet(@PathVariable final String id) {
        final var entityOptional = this.tweetRepository.findById(UUID.fromString(id));

        if (entityOptional.isPresent()) {
            final var entity = entityOptional.get();
            return ResponseEntity.ok(mapToDto(entity));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    public Collection<TweetDto> readAllTweets() {
        return this.tweetRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TweetDto> updateTweet(@PathVariable final String id, @RequestBody String message) {
        final var entityOptional = this.tweetRepository.findById(UUID.fromString(id));

        if (entityOptional.isPresent()) {
            final var entity = entityOptional.get();
            entity.setMessage(message);
            entity.setLastUpdateDate(LocalDateTime.now());
            this.tweetRepository.save(entity);

            return ResponseEntity.ok(mapToDto(entity));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTweet(@PathVariable final String id) {
        if (this.tweetRepository.existsById(UUID.fromString(id))) {
            this.tweetRepository.deleteById(UUID.fromString(id));
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    private TweetDto mapToDto(final TweetEntity entity) {
        return new TweetDto(entity.getId().toString(), entity.getMessage());
    }
}
