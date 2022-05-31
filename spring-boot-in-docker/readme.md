# Spring Boot as docker service

There are several ways to package a spring boot application in docker.

### Used technologies

* Spring Boot >= 2.6.x

### Requirements

* Java 17
* Maven >= 3.2.1
* Docker >= 3.0

## Spring Boot in Docker using Dockerfile (jvm)

See [Makefile](Makefile) and [dockerfile](src/main/docker/Dockerfile-jvm).

__Advantages__
* pure docker is used (multistage dockerfile)
* easy to integrate in ci pipelines

__Disadvantages__
* docker images size

## Spring Boot in Docker using Dockerfile (slim)

See [Makefile](Makefile) and [dockerfile](src/main/docker/Dockerfile-slim).

__Advantages__
* pure docker is used (multistage dockerfile)
* easy to integrate in ci pipelines
* jlink is used to reduce docker size (154MB)

__Disadvantages__
* docker file is complex

## Spring Boot in Docker using maven plugin

See [Makefile](Makefile) and [dockerfile](src/main/docker/Dockerfile-maven).

__Advantages__
* easy to understand

__Disadvantages__
* docker images size
* integrate in ci pipelines requires both (maven and docker)

## Spring Boot in Docker using spring boot maven plugin

See [Makefile](Makefile) and [Spring Boot documentation](https://spring.io/blog/2021/01/04/ymnnalft-easy-docker-image-creation-with-the-spring-boot-maven-plugin-and-buildpacks).

__Advantages__
* easy to understand
* no extra configuration needed

__Disadvantages__
* docker images size
* integrate in ci pipelines requires both (maven and docker)
* hidden mechanism (i.e. base image)

## Build docker images and examples

```sh
# clone project
$ git clone https://github.com/larmic/spring-boot-demos.git

# build and run docker slim image
$ make docker/build/slim
$ make docker/run/slim

# build and run docker jvm image
$ make docker/build/jvm
$ make docker/run/jvm

# build and run docker jvm image using maven
$ make docker/build/maven
$ make docker/run/maven

# build and run docker jvm image using spring boot maven plugin
$ make docker/build/spring
$ make docker/run/spring

# see image sizes
$ docker images
REPOSITORY                          TAG               IMAGE ID       CREATED              SIZE
larmic/spring-boot-in-docker-maven  latest            9b3bbfc30ec9   5 seconds ago        477MB
larmic/spring-boot-in-docker-jvm    latest            e56a0ca2ed6e   About a minute ago   477MB
larmic/spring-boot-in-docker-slim   latest            f8f3f810c892   2 minutes ago        154MB
spring-boot-in-docker                0.0.1-SNAPSHOT   e0194cf2d9c1   42 years ago         290MB


# GET Hello World
$ make http-call
curl -i -H "Accept: application/json" --request GET http://localhost:8080/
HTTP/1.1 200 
Content-Type: application/json
Content-Length: 11
Date: Sat, 26 Mar 2022 12:36:30 GMT

Hello World%   
```

