# Keycloak setup

### Initial setup

Keycloak will be started as docker service by using [Makefile](Makefile). 

```shell
# start keycloak server (admin user is admin:admin)
$ make keycloak_start

# initial setup (user larmic:test and user role mapping)
$ make setup_user

# stop and remove keycloak stuff
$ make keycloak_stop
```

### Test Keycloak

You can retrieve an access token by sending a `POST` request.

```shell 
$ curl -X POST '<KEYCLOAK_SERVER_URL>/realms/<REALM_NAME>/protocol/openid-connect/token' \
 --header 'Content-Type: application/x-www-form-urlencoded' \
 --data-urlencode 'grant_type=password' \
 --data-urlencode 'client_id=<CLIENT_ID>' \
 --data-urlencode 'username=<USERNAME>' \
 --data-urlencode 'password=<PASSWORD>'

$ curl -X POST 'http://localhost:8085/realms/spring-boot-services/protocol/openid-connect/token' \
 --header 'Content-Type: application/x-www-form-urlencoded' \
 --data-urlencode 'grant_type=password' \
 --data-urlencode 'client_id=spring-boot-service-1' \
 --data-urlencode 'username=larmic' \
 --data-urlencode 'password=test'
```

After that you can validate your `access_token` on [jwt.io](https://jwt.io/).