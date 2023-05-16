keycloak_start:
	docker compose up -d
	@echo -n "Waiting for keycloak to be ready..."
	@bash -c 'until curl --output /dev/null --silent --fail http://localhost:8085/health/ready; do echo -n .;sleep 1; done'
	@echo
	@echo "Keycloak is ready!"

keycloak_stop:
	docker compose down

setup_user:
	docker run --rm --network="host" -i -t -v ./:/workdir jetbrains/intellij-http-client:231.9011.14 \
      -L VERBOSE \
      -e setup \
      -v ./http/http-client.env.json \
      ./http/setup_user.http

http_get_larmic_token:
	curl -X POST 'http://localhost:8085/realms/spring-boot-services/protocol/openid-connect/token' \
     --header 'Content-Type: application/x-www-form-urlencoded' \
     --data-urlencode 'grant_type=password' \
     --data-urlencode 'client_id=spring-boot-service-1' \
     --data-urlencode 'username=larmic' \
     --data-urlencode 'password=test'