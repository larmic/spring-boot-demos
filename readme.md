# Spring Boot sample projects

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

The goal of the project is to show simple demos of using [Spring Boot](https://spring.io/projects/spring-boot) with different technologies.

The examples are limited to Maven and JVM >= 17 ([Java](https://www.java.com/de/) and [Kotlin](https://kotlinlang.org/)). 
Specifically used technologies are described below in the table.

| Demo                                             | Technologies                                | Content                                                                | Build status                                                                                                                                                                                                    |
|--------------------------------------------------|---------------------------------------------|------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [properties](spring-boot-properties-java)        | `java`                                      | Using and override spring boot properties                              | [![status](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-properties.yml/badge.svg)](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-properties.yml)       |
| [properties](spring-boot-properties-kotlin)      | `kotlin`                                    | Using and override spring boot properties                              | [![status](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-properties.yml/badge.svg)](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-properties.yml)       |
| [rest service](spring-boot-rest-services-java)   | `java`, `rest`, `slice tests`               | Implementing rest services and using mockmvc                           | [![status](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-rest-services.yml/badge.svg)](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-rest-services.yml) |
| [rest service](spring-boot-rest-services-kotlin) | `kotlin`, `rest`, `slice tests`             | Implementing rest services and using mockmvc                           | [![status](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-rest-services.yml/badge.svg)](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-rest-services.yml) |
| [docker](spring-boot-in-docker)                  | `java`, `docker`                            | Package spring boot application in docker in different ways            | [![status](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-in-docker.yml/badge.svg)](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-in-docker.yml)         |
| [postgres](spring-boot-postgres-java)            | `java`, `postgres`, `testcontainers`        | Using spring boot and postgresql and use testcontainers for testing    | [![status](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-postgres.yml/badge.svg)](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-postgres.yml)           |
| [postgres](spring-boot-postgres-kotlin)          | `kotlin`, `postgres`, `testcontainers`      | Using spring boot and postgresql and use testcontainers for testing    | [![status](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-postgres.yml/badge.svg)](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-postgres.yml)           |
| [elasticsearch](spring-boot-elasticsearch)       | `kotlin`, `elasticsearch`, `testcontainers` | Using spring boot and elasticsearch and use testcontainers for testing | [![status](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-elasticsearch.yml/badge.svg)](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-elasticsearch.yml) |
| [keycloak](spring-boot-keycloak)                 | `java`, `keycloak`, `spring-boot-2.7.x`     | Using spring boot and keycloak in one docker-compose                   | [![status](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-keycloak.yml/badge.svg)](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-keycloak.yml)           |
| [rabbit mq](spring-boot-rabbitmq)                | `kotlin`, `rabbitmq`, `testcontainers`      | Using spring boot and rabbit mq and use testcontainers for testing     | [![status](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-rabbitmg.yml/badge.svg)](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-rabbitmg.yml)           |
| [mongodb](spring-boot-mongodb)                   | `java`, `mongodb`, `testcontainers`         | Using spring boot and mongodb and use testcontainers for testing       | [![status](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-mongodb.yml/badge.svg)](https://github.com/larmic/spring-boot-demos/actions/workflows/spring-boot-mongodb.yml)             |
