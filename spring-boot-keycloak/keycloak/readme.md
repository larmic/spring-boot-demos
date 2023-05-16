# Keycloak setup

### Initial setup

Keycloak (+ Postgres) will be started as docker service. 
A volume `postgres_data` will be created to store persistent Keycloak data.

1. Start Keycloak

```sh 
$ docker compose up
$ docker run --rm --network="host" -i -t -v $PWD:/workdir jetbrains/intellij-http-client:231.9011.14 \
  -L VERBOSE \
  -e setup \
  -v http/http-client.env.json \
  http/setup_user.http
```

### Test Keycloak

You can retrieve an access token by sending a `POST` request.

```sh 
$ curl -X POST '<KEYCLOAK_SERVER_URL>/realms/<REALM_NAME>/protocol/openid-connect/token' \
 --header 'Content-Type: application/x-www-form-urlencoded' \
 --data-urlencode 'grant_type=password' \
 --data-urlencode 'client_id=<CLIENT_ID>' \
 --data-urlencode 'username=<USERNAME>' \
 --data-urlencode 'password=<PASSWORD>'
```

```sh 
$ curl -X POST 'http://localhost:8085/realms/spring-boot-services/protocol/openid-connect/token' \
 --header 'Content-Type: application/x-www-form-urlencoded' \
 --data-urlencode 'grant_type=password' \
 --data-urlencode 'client_id=spring-boot-service-1' \
 --data-urlencode 'username=larmic' \
 --data-urlencode 'password=test'
```

After that you can validate your `access_token` on [jwt.io](https://jwt.io/). 

### Clean up Keyloak

Stop Keycloak service by

```sh 
$ docker compose down --volumes
```