package de.larmic.rest.rest;

import de.larmic.rest.rest.dto.TweetDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
public class TweetController {

    private final InMemoryRepository inMemoryRepository;

    public TweetController(final InMemoryRepository inMemoryRepository) {
        this.inMemoryRepository = inMemoryRepository;
    }

    @PostMapping("/")
    public TweetDto tweet(@RequestBody(required = true) final String message) {
        final var dto = new TweetDto(UUID.randomUUID().toString(), message);
        this.inMemoryRepository.tweets.put(dto.id(), dto);
        return dto;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetDto> readTweet(@PathVariable final String id) {
        if (this.inMemoryRepository.tweets.containsKey(id)) {
            return ResponseEntity.ok(this.inMemoryRepository.tweets.get(id));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    public Collection<TweetDto> readAllTweets() {
        return this.inMemoryRepository.tweets.values();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TweetDto> updateTweet(@PathVariable final String id, @RequestBody String message) {
        if (this.inMemoryRepository.tweets.containsKey(id)) {
            final var dto = new TweetDto(id, message);
            this.inMemoryRepository.tweets.put(id, dto);

            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTweet(@PathVariable final String id) {
        if (this.inMemoryRepository.tweets.containsKey(id)) {
            this.inMemoryRepository.tweets.remove(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
