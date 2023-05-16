# Keycloak setup

### Initial setup

Keycloak will be started as docker service by using [Makefile](Makefile). 

```shell
# start keycloak server (admin user is admin:admin)
$ make keycloak_start

# initial setup (user larmic:test and user role mapping)
$ make setup_user

# get user larmic access token
# validate token on https://jwt.io/
$ make http_get_larmic_token

# stop and remove keycloak stuff
$ make keycloak_stop
```

### Test Keycloak

You can retrieve an access token by sending a `POST` request.

```shell 
# syntax
$ curl -X POST '<KEYCLOAK_SERVER_URL>/realms/<REALM_NAME>/protocol/openid-connect/token' \
 --header 'Content-Type: application/x-www-form-urlencoded' \
 --data-urlencode 'grant_type=password' \
 --data-urlencode 'client_id=<CLIENT_ID>' \
 --data-urlencode 'username=<USERNAME>' \
 --data-urlencode 'password=<PASSWORD>'

$ make http_get_larmic_token
```

After that you can validate your `access_token` on [jwt.io](https://jwt.io/).