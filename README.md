# API Security

## Installation
export JAVA_HOME=$(/usr/libexec/java_home -v11)

## REST endpoint
mkcert -install

cd to project root
mkcert -pkcs12 localhost
(default password: changeit)

mvn clean compile exec:java

## Same-origin policy
https://localhost:4567/natter.html

### Create a user
curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"demo","password": "password"}' \
https://localhost:4567/users

## Token-based authentication

### Create a user
curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"test","password": "password"}' \
https://localhost:4567/users

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-i -u test:password \
-H 'Content-Type: application/json' \
-X POST https://localhost:4567/sessions

### Create a user
curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"test","password": "password"}' \
https://localhost:4567/users

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-i -c /tmp/cookies -u test:password \
-H 'Content-Type: application/json' \
-X POST https://localhost:4567/sessions

curl -b /tmp/cookies \
--cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"name":"test space","owner":"test"}' \
https://localhost:4567/spaces

### CSRF
curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"test","password": "password"}' \
https://localhost:4567/users

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-i -c /tmp/cookies -u test:password \
-H 'Content-Type: application/json' \
-X POST https://localhost:4567/sessions

curl -b /tmp/cookies \
--cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-H 'X-CSRF-Token: <CSRF-token>' \
-d '{"name":"test space","owner":"test"}' \
https://localhost:4567/spaces

### Login UI
https://localhost:4567/login.html

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"test","password": "password"}' \
https://localhost:4567/users

submit username/password and check devtool

### Logout

