# API Security

## Installation
export JAVA_HOME=$(/usr/libexec/java_home -v11)

## Request and response security
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

## Rate limiting
for i in {1..5} 
do
    curl -i -d "{\"owner\":\"test\",\"name\":\"space$i\"}" -H 'Content-Type: application/json' http://localhost:4567/spaces; 
done

## HTTP Basic Authentication
jshell> new String(
java.util.Base64.getDecoder().decode("ZGVtbzpjaGFuZ2VpdA=="), "UTF-8")
$1 ==> "demo:changeit"

curl -H 'Content-Type: application/json' -d '{"name": "test space", "owner": "demo"}' http://localhost:4567/spaces

curl -H 'Content-Type: application/json' -d '{"username":"demo","password": "password"}' http://localhost:4567/users

curl -u demo:password -H 'Content-Type: application/json' -d '{"name": "test space", "owner": "demo"}' http://localhost:4567/spaces

## HTTPS and HTTP Strict-Transport-Security (HSTS)
https://mkcert.dev

brew install mkcert

mkcert -install
    Created a new local CA at "/.../Library/Application Support/mkcert"
    Sudo password:
    The local CA is now installed in the system trust store!
    The local CA is now installed in Java's trust store! 

cd to project root
mkcert -pkcs12 localhost
(default password: changeit)

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"demo","password": "password"}' \
https://localhost:4567/users

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-u demo:password \
-H 'Content-Type: application/json' \
-d '{"name": "test space", "owner": "demo"}' \
https://localhost:4567/spaces

for a browser, implements HTTP Strict-Transport-Security (HSTS) in afterAfter filter
response.header("Strict-Transport-Security", "max-age=31536000");
for development, set a short max-age attribute

mkcert -uninstall

## Audit logging
curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"demo","password": "password"}' \
https://localhost:4567/users

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-u demo:password \
-H 'Content-Type: application/json' \
-d '{"name": "test space", "owner": "demo"}' \
https://localhost:4567/spaces

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-u demo:password \
-H 'Content-Type: application/json' \
-d '{"author": "demo", "message": "test message"}' \
https://localhost:4567/spaces/1/messages

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
https://localhost:4567/logs | jq

## Access control
curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"demo","password": "password"}' \
https://localhost:4567/users

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-i -d '{"name":"test space","owner":"demo"}' \
-H 'Content-Type: application/json' \
https://localhost:4567/spaces

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-i -d '{"name":"test space","owner":"demo"}' \
-H 'Content-Type: application/json' \
-u demo:password \
https://localhost:4567/spaces

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-u demo:password \
-H 'Content-Type: application/json' \
-d '{"author": "demo", "message": "test message"}' \
https://localhost:4567/spaces/1/messages

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"demo2","password": "password"}' \
https://localhost:4567/users

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-i -u demo2:password \
https://localhost:4567/spaces/1/messages/1

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-i -u demo:password \
https://localhost:4567/spaces/1/messages/1

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-u demo:password \
-H 'Content-Type: application/json' \
-d '{"username":"demo2","permissions": "r"}' \
https://localhost:4567/spaces/1/members

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-i -u demo2:password \
https://localhost:4567/spaces/1/messages/1

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"evildemo2","password": "password"}' \
https://localhost:4567/users

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-u demo2:password \
-H 'Content-Type: application/json' \
-d '{"username":"evildemo2","permissions": "rwd"}' \
https://localhost:4567/spaces/1/members

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-i -X DELETE -u evildemo2:password \
https://localhost:4567/spaces/1/messages/1
