# Keycloak setup

### Initial setup

Keycloak (+ Postgres) will be started as docker service. 
A volume `postgres_data` will be created to store persistent Keycloak data.

1. Start Keycloak

```sh 
$ docker compose up
```

2. Open `http://localhost:8085/admin/` and login with `admin` and `admin`.
3. Add User `larmic` and set credentials password to `test`.


Without using `imports/realm-export.json` you have to create realm, client and role by yourself.
3. Create realm `spring-boot-services`.
4. Create client `spring-boot-service-1` and add Valid Redirect URL `http://localhost:8081/*`.
5. Create client `spring-boot-service-2` and add Valid Redirect URL `http://localhost:8082/*`.
6. Create role `user`.
7. Assign role `user` to user `larmic`.

### Test Keycloak

You can retrieve an access token by sending a `POST` request.

```sh 
$ curl -X POST '<KEYCLOAK_SERVER_URL>/auth/realms/<REALM_NAME>/protocol/openid-connect/token' \
 --header 'Content-Type: application/x-www-form-urlencoded' \
 --data-urlencode 'grant_type=password' \
 --data-urlencode 'client_id=<CLIENT_ID>' \
 --data-urlencode 'username=<USERNAME>' \
 --data-urlencode 'password=<PASSWORD>'
```

```sh 
$ curl -X POST 'http://localhost:8085/auth/realms/spring-boot-services/protocol/openid-connect/token' \
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