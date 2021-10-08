# MockMVC testing with Spring Boot and JUnit5

Simple example demonstrating how Spring Boot, Postgresql, Testcontainers and JUnit 5 can play together.

This demo is build on [larmic/spring-boot-rest-services](https://github.com/larmic/spring-boot-rest-services).

## Used technologies

* Spring Boot >= 2.5.4
* Postgresql >= 13.3  
* Testcontainers >= 1.16.0
* JUnit >= 5.8.0

## Requirements

* Java 11
* Maven >= 3.2.1 
* Docker >= 3.0 (for integration tests)

##### Clone repository and build project

Integrations test ```TweetRepositoryIT``` will be started in maven phase ```verify```.

```ssh
$ https://github.com/larmic/spring-boot-postgres
$ mvn clean verify
```

##### Local testing

```ssh
# start postgresql
$ docker compose -f misc/postgresql/docker-compose.yml up

# start spring boot application
$ mvn spring-boot:run

# HTTP request examples
# Get all tweets
$ curl -i -H "Accept: application/json" --request GET http://localhost:8080/

# Post a new tweet
$ curl -i -H "Content-Type: application/json" --request POST --data 'hello, this is a tweet!' http://localhost:8080/

# Read a specific tweet     
$ curl -i -H "Accept: application/json" --request GET http://localhost:8080/{tweet-id}      
 
# Delete a specific tweet
$ curl -i -H "Accept: application/json" --request DELETE http://localhost:8080/{tweet-id}

# Update a specific tweet    
$ curl -i -H "Content-Type: application/json" "Accept: application/json" --request PUT --data 'hello, this is a changed tweet!' http://localhost:8080/{tweet-id}        
```
