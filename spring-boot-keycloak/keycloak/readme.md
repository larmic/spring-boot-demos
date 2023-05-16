# Keycloak setup

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