# Spring Boot and MongoDB

Simple example demonstrating how Spring Boot, MongoDB, Testcontainers and JUnit 5 can play together.

## Used technologies

* Spring Boot >= 2.6.2
* MongoDB >= 5.0.5
* Testcontainers >= 1.16.2
* JUnit >= 5.8.0

## Requirements

* Java 17
* Maven >= 3.2.1
* Docker >= 3.0 (for integration tests)

##### Clone repository and build project

Integration tests ```TweetRepositoryIT``` and ```TweetControllerIT``` will be started in maven phase ```verify```.

```sh
$ git clone https://github.com/larmic/spring-boot-demos.git
$ mvn clean verify
```

##### Local testing

```sh
# start postgresql
$ docker compose -f etc/mongodb/docker-compose.yaml up

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
