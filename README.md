# API Security

## Installation
export JAVA_HOME=$(/usr/libexec/java_home -v11)

## REST endpoint
mvn clean compile exec:java

curl -i -d '{"name": "test space", "owner": "demo"}' http://localhost:4567/spaces

## Injection attack
curl -i -d "{\"name\": \"test'space\", \"owner\": \"demo\"}" http://localhost:4567/spaces

curl -i -d "{\"name\": \"test\",\"owner\": \"'); DROP TABLE spaces; --\"}" http://localhost:4567/spaces

curl -i -d '{"name": "test space", "owner": "demo"}' http://localhost:4567/spaces

curl -i -d "{\"name\": \"', '');DROP TABLE spaces; --\",\"owner\": \"\"}" http://localhost:4567/spaces

## Input validation
curl -i -d '{"name": "test space", "owner": "a really long username that is more than 30 characters long"}' http://localhost:4567/spaces

## XSS
curl -i -H "Content-Type: application/json" -d '{"name": "test space", "owner": "demo"}' http://localhost:4567/spaces