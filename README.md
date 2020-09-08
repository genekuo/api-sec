# API Security

## Installation
export JAVA_HOME=$(/usr/libexec/java_home -v11)

## REST endpoint
mkcert -install

cd to project root
mkcert -pkcs12 localhost
(default password: changeit)

## Stateless token
mvn clean compile exec:java \
-Djava.security.egd=file:/dev/urandom \
-Dkeystore.password=changeit

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"test","password": "password"}' \
https://localhost:4567/users

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-u test:password \
-H 'Content-Type: application/json' \
-X POST https://localhost:4567/sessions

## Standard JWT
mvn clean compile exec:java \
-Djava.security.egd=file:/dev/urandom \
-Dkeystore.password=changeit

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"test","password": "password"}' \
https://localhost:4567/users

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-u test:password \
-H 'Content-Type: application/json' \
-X POST https://localhost:4567/sessions

pass token value to the debugger on https://jwt.io

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-H 'Authorization: Bearer <token>' \
-d '{"name":"test space","owner":"test"}' \
https://localhost:4567/spaces

### Authenticated encryption
keytool -genseckey -keyalg AES -keysize 256 \
-alias aes-key -keystore keystore.p12 -storepass changeit

mvn clean compile exec:java \
-Djava.security.egd=file:/dev/urandom \
-Dkeystore.password=changeit

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"test","password": "password"}' \
https://localhost:4567/users

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-u test:password \
-H 'Content-Type: application/json' \
-X POST https://localhost:4567/sessions

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-H 'Authorization: Bearer <token>' \
-d '{"name":"test space","owner":"test"}' \
https://localhost:4567/spaces

### Encrypted JWT
mvn clean compile exec:java \
-Djava.security.egd=file:/dev/urandom \
-Dkeystore.password=changeit

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"test","password": "password"}' \
https://localhost:4567/users

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-u test:password \
-H 'Content-Type: application/json' \
-X POST https://localhost:4567/sessions

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-H 'Authorization: Bearer eyJlbmMiOiJBMTI4Q0JDLUhTMjU2IiwiYWxnIjoiZGlyIn0..RMnSCAAEALi8v5wz5By6hQ.z_695UN2VbYzAaVOsf36qNZtKqgb0g4v99XW1PglVSLipADjdBqy4gimq2iply5GdHJjdXCYPE69I87C7NWZxwAzdBSz7rJHwo0uLbBIElc.BTSQp_JsdeC5wzZONMym1w' \
-d '{"name":"test space","owner":"test"}' \
https://localhost:4567/spaces

### Types



