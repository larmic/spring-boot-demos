# Spring Boot as docker service

There are several ways to package a spring boot application to docker file.

## Spring Boot in Docker using maven plugin

See [spring-boot-in-docker-using-maven-plugin](spring-boot-in-docker-using-maven-plugin) to see, how docker image build can be added to `mvn package`
and `mvn deploy`.

* maven plugin integrates docker in maven phases
* size 275MB

## Spring Boot in Docker using Dockerfile

See [spring-boot-in-docker-using-dockerfile](spring-boot-in-docker-using-dockerfile).

* pure docker is used (multistage dockerfile)
* easy to integrate in ci pipelines
* jlink is used to reduce docker size (154MB)
