DOCKER_TAG ?= latest

docker/build/slim:
	echo "Build slim docker image using multistage build"
	docker build -t larmic/spring-boot-in-docker-slim:$(DOCKER_TAG) -f src/main/docker/Dockerfile-slim .

docker/build/jvm:
	echo "Build bigger jvm docker image using multistage build"
	docker build -t larmic/spring-boot-in-docker-jvm:$(DOCKER_TAG) -f src/main/docker/Dockerfile-jvm .

docker/build/maven:
	echo "Build bigger jvm docker image using maven"
	./mvnw clean package -Pdocker-using-spotify-plugin

docker/build/jib:
	echo "Build bigger jvm docker image using maven"
	./mvnw clean compile jib:dockerBuild -Pdocker-using-jib-plugin

docker/build/spring:
	echo "Build bigger jvm docker image using spring boot maven plugin"
	./mvnw spring-boot:build-image

docker/run/slim:
	docker run --rm -p 8080:8080 larmic/spring-boot-in-docker-slim:$(DOCKER_TAG)

docker/run/jvm:
	docker run --rm -p 8080:8080 larmic/spring-boot-in-docker-jvm:$(DOCKER_TAG)

docker/run/maven:
	docker run --rm -p 8080:8080 larmic/spring-boot-in-docker-maven:$(DOCKER_TAG)

docker/run/jib:
	docker run --rm -p 8080:8080 larmic/spring-boot-in-docker-jib:$(DOCKER_TAG)

docker/run/spring:
	docker run --rm -p 8080:8080 spring-boot-in-docker:0.0.1-SNAPSHOT

docker/clean:
	echo "Clean up dangling images"
	docker images -a | grep none | awk '{ print $3; }' | xargs docker rmi --force

http-call:
	curl -i -H "Accept: application/json" --request GET http://localhost:8080/