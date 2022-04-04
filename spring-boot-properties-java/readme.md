# Spring Boot and Properties

Simple example demonstrating how Spring Boot and properties can play together.

## Used technologies

* Spring Boot >= 2.6.x
* JUnit >= 5.8.x

## Requirements

* Java 17
* Maven >= 3.2.1 

##### Clone repository and build project

```sh
$ git clone https://github.com/larmic/spring-boot-properties
$ mvn clean package
```

##### Local testing

```sh
# default properties
$ java -jar target/spring-boot*.jar

# override by application-stage.properties
$ java -jar -Dspring.profiles.active=stage target/spring-boot*.jar

# override by command line
$ java -jar target/spring-boot*.jar --hello.value="cli properties"

# override properties in local folder
# rename backup properties
$ mv application.properties.demo application.properties
$ java -jar target/spring-boot*.jar

# override by environment parameters
$ HELLO_VALUE="environment property" java -jar target/spring-boot*.jar
```