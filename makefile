postgres:
	docker run --rm --name pg-docker -e POSTGRES_USER=todo -e POSTGRES_PASSWORD=12345 -e POSTGRES_DB=todo -d -p 5432:5432 postgres:alpine

dropDB:
	docker rm -f pg-docker
