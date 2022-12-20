# Integration testing with Spring Boot, Elasticsearch, Testcontainers and JUnit5

Simple example demonstrating how testcontainers, Elasticsearch and JUnit 5 can play together.

## Used technologies

* Spring Boot >= 2.7.x
* Kotlin >= 1.6.x
* Elasticsearch >= 8.5.3 with [new java client](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/usage.html)
* Testcontainers >= 1.17.x
* JUnit >= 5.8.x

## Requirements

* Java 17
* Maven >= 3.2.1 (Kotlin comes as maven dependency)
* Docker >= 3.0 (for integration tests)

##### Clone repository and build project

Integration test ```TweetControllerIT``` will be started in maven phase ```verify```.

```sh
$ git clone https://github.com/larmic/spring-boot-demos
$ mvn clean verify -f spring-boot-elasticsearch
```

##### Local testing

```sh
# start local elasticsearch
$ docker run --rm -p 9200:9200 -p 9300:9300 --name testcontainers-junit5-demo -e "discovery.type=single-node" -e "xpack.security.enabled=false" -e "cluster.name=elasticsearch" docker.elastic.co/elasticsearch/elasticsearch-oss:7.9.2

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
