DOCKER_TAG ?= latest

docker/build/slim:
	echo "Build slim docker image using multistage build"
	docker build -t larmic/spring-boot-in-docker-slim:$(DOCKER_TAG) -f src/main/docker/Dockerfile-slim .

docker/build/jvm:
	echo "Build bigger jvm docker image using multistage build"
	docker build -t larmic/spring-boot-in-docker-jvm:$(DOCKER_TAG) -f src/main/docker/Dockerfile-jvm .

docker/run/slim:
	docker run --rm -p 8080:8080 larmic/spring-boot-in-docker-slim:$(DOCKER_TAG)

docker/run/jvm:
	docker run --rm -p 8080:8080 larmic/spring-boot-in-docker-jvm:$(DOCKER_TAG)

docker/clean:
	echo "Clean up dangling images"
	docker images -a | grep none | awk '{ print $3; }' | xargs docker rmi --force

http-call:
	curl -i -H "Accept: application/json" --request GET http://localhost:8080/