start:
	docker compose up -d

stop:
	docker compose stop

clean: stop
	docker compose rm
	docker volume rm postgresql_postgres_data