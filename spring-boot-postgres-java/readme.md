# MockMVC testing with Spring Boot and JUnit5

Simple example demonstrating how Spring Boot, Postgresql, Testcontainers and JUnit 5 can play together.

This demo is based on [larmic/spring-boot-rest-services](https://github.com/larmic/spring-boot-rest-services).

## Used technologies

* Spring Boot >= 2.7.x
* Postgresql >= 14.x  
* Testcontainers >= 1.17.x
* JUnit >= 5.8.x

## Requirements

* Java 17
* Maven >= 3.2.1 
* Docker >= 3.0 (for database and integration tests)

### Clone repository and build project

In this repository are different database test examples. A fast unit test and a slow integration tests.

For further information and the difference between unit and integration tests 
see [Youtube (German)](https://youtu.be/_CGvdhRc9DE) or [GitHub](https://github.com/larmic/unit-testing-best-bad-practices).

Unit test ```de.larmic.postgres.database.TweetRepositoryTest``` will be started in maven phase ```test```.
Integrations test ```de.larmic.postgres.database.TweetRepositoryIT``` will be started in maven phase ```verify```.

```sh
$ https://github.com/larmic/spring-boot-postgres
$ mvn clean verify
```

### Local testing

```sh
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
