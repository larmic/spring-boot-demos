# Spring Boot as docker service

Simple example demonstrating how Spring Boot can be packaged as docker container 
using native [Dockerfile](Dockerfile).

[Dockerfile](Dockerfile) is a multistage docker file and using JLink to build a custom JRE to reduce image size.

## Used technologies

* Spring Boot >= 2.6.2

## Requirements

* Java 17
* Maven >= 3.2.1 
* Docker >= 3.0

##### Clone repository and build project

Dockerfile will used to create image.

```sh
$ docker build -t larmic/spring-boot-in-docker-using-dockerfile:latest .
```

##### Local testing

```sh
# start application
$ docker run --rm -p 8080:8080 larmic/spring-boot-in-docker-using-dockerfile

# GET Hello World
$ curl -i -H "Accept: application/json" --request GET http://localhost:8080/
```
