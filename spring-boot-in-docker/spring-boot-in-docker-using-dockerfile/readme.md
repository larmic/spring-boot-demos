# Spring Boot as docker service

Simple example demonstrating how Spring Boot can be packaged as docker container 
using native [Dockerfile](src/main/docker/Dockerfile-slim).

[Dockerfile](src/main/docker/Dockerfile-slim) is a multistage docker file and using JLink to build a custom JRE to reduce image size.

## Used technologies

* Spring Boot >= 2.6.x

## Requirements

* Java 17
* Maven >= 3.2.1 
* Docker >= 3.0

##### Clone repository and build project

See [Makefile](Makefile).

```sh
# clone project
$ git clone https://github.com/larmic/spring-boot-demos.git

# build docker slim image
$ docker build -t larmic/spring-boot-in-docker:latest -f src/main/docker/Dockerfile-slim .

# start application
$ docker run --rm -p 8080:8080 larmic/spring-boot-in-docker:latest

# GET Hello World
$ curl -i -H "Accept: application/json" --request GET http://localhost:8080/
```
