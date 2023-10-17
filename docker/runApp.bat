docker run --rm --name chat-bot ^
--network="host" ^
-p 8080:8080 ^
chat-bot-image:0.0.1-SNAPSHOT