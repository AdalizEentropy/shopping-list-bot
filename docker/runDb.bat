docker run --rm --name pg-docker ^
-e POSTGRES_PASSWORD=shoppingpassword ^
-e POSTGRES_USER=shoppinguser ^
-e POSTGRES_DB=shopping-list ^
-p 5430:5432 ^
postgres:13