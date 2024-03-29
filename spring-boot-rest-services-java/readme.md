# MockMVC testing with Spring Boot and JUnit5

Simple example demonstrating how Spring Boot, REST-API, mockmvc and JUnit 5 can play together.

## Used technologies

* Spring Boot >= 3.0.x
* JUnit >= 5.9.x

## Requirements

* Java 17
* Maven >= 3.2.1 

##### Clone repository and build project

Test ```TweetControllerTest``` will be started in maven phase ```package```.

```sh
$ git clone https://github.com/larmic/spring-boot-rest-services-java
$ mvn clean package
```

##### Local testing

```sh
# start application
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
