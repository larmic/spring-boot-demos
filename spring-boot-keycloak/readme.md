# Keycloak Spring Boot integration example

Simple example using keycloak and bind two spring boot services with rest api.

## Used technologies

* Spring Boot >= 3.x
* Keycloak >= 21.x

## Requirements

* Java 17
* Maven >= 3.2.1
* Docker >= 3.0 (to run Keycloak)

## Build and run project

### Keycloak

Keycloak can be started as docker service by using [Makefile](Makefile).

```shell
# start keycloak server (admin user is admin:admin)
$ make keycloak_start

# initial setup (add user larmic:test and add user role mapping)
$ make keycloak_setup_user_and_roles
RUN SUCCESSFUL

# retrieve user larmic access token
# validate it on https://jwt.io/
$ make http_get_larmic_access_token

# stop and remove keycloak stuff
$ make keycloak_stop
```

### Spring Boot Service

```shell
# clone project
$ git clone https://github.com/larmic/spring-boot-demos

# build java application 
$ make java-build-application

# start java service
$ make java-run-application
```

## Test services

### You can call REST services in your browser

[Call unsecured hello api](http://localhost:8080/unsecure/hello)  
[Call secured hello api](http://localhost:8080/secure/hello) redirects to Keycloak

#### Or you can use command line

```shell 
# call unsecured hello api
$ make http-get-hello-unsecure
RUN SUCCESSFUL

# call secured hello api without access token
$ make http-get-hello-secure-with-token
RUN SUCCESSFUL

$ make http-get-hello-secure-without-token
RUN SUCCESSFUL
```