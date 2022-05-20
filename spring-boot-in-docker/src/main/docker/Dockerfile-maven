FROM eclipse-temurin:18

MAINTAINER Lars Michaelis <mail@larmic.de>

ARG JAR_FILE
COPY target/${JAR_FILE} /spring-boot-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/spring-boot-app.jar"]