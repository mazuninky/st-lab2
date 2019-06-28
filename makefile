postgres:
	docker run --rm --name pg-docker -e POSTGRES_USER=todo -e POSTGRES_PASSWORD=12345 -e POSTGRES_DB=todo -d -p 5432:5432 postgres:alpine

migrate:
	flyway -user=todo -password=12345 -url=jdbc:postgresql://localhost:5432/ -locations=./data/src/main/resources/db/migration migrate

dropDB:
	docker rm -f pg-docker
