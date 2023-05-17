keycloak_host = http://localhost:8085
keycloak_realm = spring-boot-services
keycloak_client_id = spring-boot-service-1
keycloak_demo_user = larmic
keycloak_demo_user_pw = test

keycloak_start:
	docker compose -f keycloak/docker-compose.yml up -d
	@echo "Waiting for keycloak to be ready..."
	@bash -c 'until curl --output /dev/null --silent --fail $(keycloak_host)/health/ready; do echo -n .;sleep 1; done'
	@echo
	@echo "Keycloak is ready!"

keycloak_stop:
	docker compose -f keycloak/docker-compose.yml down

keycloak_setup_user_and_roles:
	docker run --rm --network="host" -i -t -v ./:/workdir jetbrains/intellij-http-client:231.9011.14 \
      -L VERBOSE \
      -e setup \
      -v ./keycloak/http/http-client.env.json \
      ./keycloak/http/setup_user.http

java-build-application:
	mvn clean package

java-run-application:
	mvn spring-boot:run

http_get_larmic_access_token:
	curl -X POST '$(keycloak_host)/realms/$(keycloak_realm)/protocol/openid-connect/token' \
     --header 'Content-Type: application/x-www-form-urlencoded' \
     --data-urlencode 'grant_type=password' \
     --data-urlencode 'client_id=$(keycloak_client_id)' \
     --data-urlencode 'username=$(keycloak_demo_user)' \
     --data-urlencode 'password=$(keycloak_demo_user_pw)'