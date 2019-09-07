migrate:
	flyway -user=todo -password=12345 -url=jdbc:postgresql://localhost:5432/ -locations=filesystem:./data/src/main/resources/db/migration migrate
