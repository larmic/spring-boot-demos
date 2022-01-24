package de.larmic.springbootmongodb.rest.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TweetDto {

    private String id;
    private String message;
}
