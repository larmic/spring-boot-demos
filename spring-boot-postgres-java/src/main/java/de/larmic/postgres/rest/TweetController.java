package de.larmic.postgres.rest;

import de.larmic.postgres.database.TweetRepository;
import de.larmic.postgres.database.model.TweetEntity;
import de.larmic.postgres.rest.dto.TweetDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class TweetController {

    private final TweetRepository tweetRepository;

    public TweetController(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @PostMapping("/")
    public TweetDto tweet(@RequestBody final String message) {
        final var entity = this.tweetRepository.save(new TweetEntity(message));

        return mapToDto(entity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetDto> readTweet(@PathVariable final String id) {
        if (this.tweetRepository.existsById(Long.valueOf(id))) {
            final var entity = this.tweetRepository.getById(Long.valueOf(id));
            return ResponseEntity.ok(mapToDto(entity));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    public Collection<TweetDto> readAllTweets() {
        return this.tweetRepository.findAll().stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TweetDto> updateTweet(@PathVariable final String id, @RequestBody String message) {
        if (this.tweetRepository.existsById(Long.valueOf(id))) {
            final var entity = this.tweetRepository.getById(Long.valueOf(id));
            entity.setMessage(message);
            entity.setLastUpdateDate(LocalDateTime.now());
            this.tweetRepository.save(entity);

            return ResponseEntity.ok(mapToDto(entity));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTweet(@PathVariable final String id) {
        if (this.tweetRepository.existsById(Long.valueOf(id))) {
            this.tweetRepository.deleteById(Long.valueOf(id));
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    private TweetDto mapToDto(final TweetEntity entity) {
        return new TweetDto(entity.getId().toString(), entity.getMessage());
    }
}
