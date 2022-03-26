DOCKER_TAG ?= latest

docker/build:
	docker build -t larmic/spring-boot-in-docker:$(DOCKER_TAG) -f src/main/docker/Dockerfile-slim .

docker/run:
	docker run --rm -p 8080:8080 larmic/spring-boot-in-docker:$(DOCKER_TAG)

docker/clean:
	docker images -a | grep none | awk '{ print $3; }' | xargs docker rmi --force

http-call:
	curl -i -H "Accept: application/json" --request GET http://localhost:8080/