# Keycloak Spring Boot integration example

Simple example using keycloak and bind two spring boot services with rest api.

![System View](assets/system_view.png)

## Used technologies

* Spring Boot >= 2.7.x
* Keycloak >= 16.x.x
* Postgres >= 15.x (Keycloak database)

## Requirements

* Java 17
* Maven >= 3.2.1
* Docker >= 3.0 (to run Keycloak)

## Build project

### Clone project

```sh 
$ git clone https://github.com/larmic/spring-boot-keycloak
```

### Keycloak

Check the [Keycloak setup](keycloak/readme.md) to start a Keycloak service

After installing Keycloak and register service clients, roles and users you can start Keycloak and retrieve an `access token`.

```sh 
$ cd keycloak && docker compose up
$ curl -X POST 'http://localhost:8085/auth/realms/spring-boot-services/protocol/openid-connect/token' \
 --header 'Content-Type: application/x-www-form-urlencoded' \
 --data-urlencode 'grant_type=password' \
 --data-urlencode 'client_id=spring-boot-service-1' \
 --data-urlencode 'username=larmic' \
 --data-urlencode 'password=test'
```

### Spring Boot Services

#### Build services

```sh 
$ mvn -f spring-boot-keycloak-service-1/pom.xml clean package
$ mvn -f spring-boot-keycloak-service-2/pom.xml clean package
```

#### Start services

```sh 
$ mvn -f spring-boot-keycloak-service-1/pom.xml spring-boot:run
$ mvn -f spring-boot-keycloak-service-2/pom.xml spring-boot:run
```

### Test services

#### You can call REST services in your browser

[Unsecure hello of service 1](http://localhost:8081/unsecure/hello)

[Unsecure hello of service 2](http://localhost:8082/unsecure/hello) calls service 1

[Unsecure hello of service 1](http://localhost:8081/secure/hello) redirects to Keycloak

[Unsecure hello of service 2](http://localhost:8082/secure/hello) redirects to Keycloak and calls service 1

#### Or you can use command line

```sh 
$ curl -i http://localhost:8081/unsecure/hello
$ curl -i http://localhost:8082/unsecure/hello
```

Without sending an `access token` to secured services you will get a redirect

```sh 
$ curl -i http://localhost:8081/secure/hello
HTTP/1.1 302
...
Date: Mon, 14 Jun 2021 09:47:20 GMT
```

#### Get and copy `access token` and add authorization header

```sh 
$ curl -X POST 'http://localhost:8085/auth/realms/spring-boot-services/protocol/openid-connect/token' \
 --header 'Content-Type: application/x-www-form-urlencoded' \
 --data-urlencode 'grant_type=password' \
 --data-urlencode 'client_id=spring-boot-service-1' \
 --data-urlencode 'username=larmic' \
 --data-urlencode 'password=test'
 
$ curl -H "Authorization: Bearer <ACCESS_TOKEN>" http://localhost:8081/secure/hello
curl -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ0Z3J3dEZmaExIYjVBOVRzS1dlM2J0RmVYSDlvMWJZR05RQWJnTEhvcFRzIn0.eyJleHAiOjE2ODMxMDQyNzQsImlhdCI6MTY4MzEwMzk3NCwianRpIjoiNzgwOGIzNDUtMWRhYS00MjAzLWI2MWUtYmM4MTVmZjk0N2QxIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDg1L3JlYWxtcy9zcHJpbmctYm9vdC1zZXJ2aWNlcyIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiIwY2ExNmQ0OC1jZDkwLTQ0NDMtYmY1Mi02NmJiMjg5NjNkYzgiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJzcHJpbmctYm9vdC1zZXJ2aWNlLTEiLCJzZXNzaW9uX3N0YXRlIjoiZTNmMTliMTgtNzZlZS00YjE2LWI3YzUtNGMzZGQxOTcwYWJhIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODEiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtc3ByaW5nLWJvb3Qtc2VydmljZXMiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIiwidXNlciJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsInNpZCI6ImUzZjE5YjE4LTc2ZWUtNGIxNi1iN2M1LTRjM2RkMTk3MGFiYSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoibGFybWljIiwiZ2l2ZW5fbmFtZSI6IiIsImZhbWlseV9uYW1lIjoiIn0.sc1ilEKrxYaENWKV0hdhjKwHfHeqiKhsrhVD-UVItWRip8tHiR9dcAs5U4JefKQWPyIy6Gs0tM3k-DPTz5kgjYPajNbVvab34d2J2sjTLeg19CrqtFE0EVRhxcVSB9LUxoVo73HdL03jiHK3m3JXCEC_T6dLETYHvW7VuSkEXs8-sCA2TdrilsrZhC3w5iLOWgsLAEqEs2o4pFgasM72I3U8xOGyJMK56uqG9tzKB863Y1_pwsyhfSt-oKxTDNZBcJ-Y0Y0yWEBk_7GEYZnJ9Dkron8vtpSTJ9lYwXOW4aUmZSN3CkIdS1lZMPH6X4_JvpIhMG6yoZF2kAxjtilfMQ","expires_in":300,"refresh_expires_in":1800,"refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJlYjJhMTEzMi1kNmZhLTQ0YTUtYmU0NC0zYmI0OGI0NjRlYzAifQ.eyJleHAiOjE2ODMxMDU3NzQsImlhdCI6MTY4MzEwMzk3NCwianRpIjoiMzBhYjMyOTgtNDdjOS00M2E3LWIwM2MtOWVkNjg4MzU0MGZlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDg1L3JlYWxtcy9zcHJpbmctYm9vdC1zZXJ2aWNlcyIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4NS9yZWFsbXMvc3ByaW5nLWJvb3Qtc2VydmljZXMiLCJzdWIiOiIwY2ExNmQ0OC1jZDkwLTQ0NDMtYmY1Mi02NmJiMjg5NjNkYzgiLCJ0eXAiOiJSZWZyZXNoIiwiYXpwIjoic3ByaW5nLWJvb3Qtc2VydmljZS0xIiwic2Vzc2lvbl9zdGF0ZSI6ImUzZjE5YjE4LTc2ZWUtNGIxNi1iN2M1LTRjM2RkMTk3MGFiYSIsInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsInNpZCI6ImUzZjE5YjE4LTc2ZWUtNGIxNi1iN2M1LTRjM2RkMTk3MGFiYSJ9.MjZIPIhefktbi0D65knlfyxSZW6tYXabh7uQMEPhFIw" http://localhost:8081/secure/hello
```