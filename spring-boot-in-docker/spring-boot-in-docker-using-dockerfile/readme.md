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
$ make docker/build/slim
# run slim docker container
$ make docker/run/slim

# build docker jvm image
$ make docker/build/jvm
# run jvm docker container
$ make docker/run/jvm

# see image sizes
$ docker images
REPOSITORY                          TAG       IMAGE ID       CREATED              SIZE
larmic/spring-boot-in-docker-jvm    latest    e56a0ca2ed6e   About a minute ago   477MB
larmic/spring-boot-in-docker-slim   latest    f8f3f810c892   2 minutes ago        154MB

# GET Hello World
$ make http-call
curl -i -H "Accept: application/json" --request GET http://localhost:8080/
HTTP/1.1 200 
Content-Type: application/json
Content-Length: 11
Date: Sat, 26 Mar 2022 12:36:30 GMT

Hello World%   
```
