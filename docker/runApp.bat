docker run --rm --name shopping-list-bot ^
--network="host" ^
-p 8080:8080 ^
-e spring.datasource.username=%DB_USERNAME% ^
-e spring.datasource.password=%DB_PASSWORD% ^
-e telegram.bot.username=%BOT_NAME% ^
-e telegram.bot.token=%BOT_TOKEN% ^
-e DB_ADDRESS=localhost:5430 ^
-e DB_SCHEME=shopping-list ^
adalizaentropy/shopping-list-bot:1.0.0