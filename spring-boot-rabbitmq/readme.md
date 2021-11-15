# Integration testing with Spring Boot, RabbitMQ, Testcontainers and JUnit5

Simple example demonstrating how testcontainers, RabbitMQ and JUnit 5 can play together.

A simple test will be converted into JSON and send by [RabbitSender.kt](src/main/kotlin/de/larmic/rabbitmq/service/RabbitSender.kt) to rabbit.
Message will be received by [RabbitReceiver.kt](src/main/kotlin/de/larmic/rabbitmq/service/RabbitReceiver.kt).

## Used technologies

* Spring Boot >= 2.5.5
* Kotlin >= 1.5.31
* Rabbit 3
* Testcontainers >= 1.16.0
* JUnit >= 5.8.1

## Requirements

* Java 11
* Maven >= 3.2.1 (Kotlin comes as maven dependency)
* Docker >= 3.0 (for integration tests)

##### Clone repository and build project

Integration test ```RabbitIT``` will be started in maven phase ```verify```.

```sh
$ git clone https://github.com/larmic/spring-boot-demos
$ mvn clean verify -f spring-boot-rabbitmq
```

##### Local testing

```sh
# start local rabbit
$ docker compose -f misc/rabbitmq/docker-compose.yml up -d

# start application
$ mvn spring-boot:run

# Post a new message
$ curl -i -H "Content-Type: text/plain" --request POST --data 'hello, this is a rabbit message!' http://localhost:8080/
```
