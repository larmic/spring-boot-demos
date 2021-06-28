# Spring Boot as docker service

Simple example demonstrating how Spring Boot can be packaged as docker container 
using [spotify dockerfile-maven-plugin](https://github.com/spotify/dockerfile-maven).

## Used technologies

* Spring Boot 2.5.1
* spotify/dockerfile-maven plugin 1.4.13

## Requirements

* Java 11
* Maven >= 3.2.1 
* Docker >= 3.0 (for integration tests)

##### Clone repository and build project

Maven package phase will be create docker image.

```ssh
$ https://github.com/larmic/spring-boot-rest-services
$ mvn clean package
```

##### Local testing

```ssh
# start application
$ docker run --rm -p 8080:8080 larmic/spring-boot-in-docker

# GET Hello World
$ curl -i -H "Accept: application/json" --request GET http://localhost:8080/
```
