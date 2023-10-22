docker run --rm --name db-docker ^
-e POSTGRES_PASSWORD=%DB_PASSWORD% ^
-e POSTGRES_USER=%DB_USERNAME% ^
-e POSTGRES_DB=shopping-list ^
-p 5430:5432 ^
postgres:13