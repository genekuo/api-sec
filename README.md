# API Security

## Installation
export JAVA_HOME=$(/usr/libexec/java_home -v11)

## REST endpoint
mkcert -install

cd to project root
mkcert -pkcs12 localhost
(default password: changeit)

## CORS
[API server]
mvn clean compile exec:java

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"test","password": "password"}' \
https://localhost:4567/users

[UI from a different origin]
mvn clean compile exec:java -Dexec.args=9999
https://localhost:9999/login.html
submit usrename/password to API server
create space/test to API server

## Non-cookie token
mvn clean compile exec:java -Djava.security.egd=file:/dev/urandom

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"test","password": "password"}' \
https://localhost:4567/users

[No Set-Cookie header in the response]
curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-i -u test:password \
-H 'Content-Type: application/json' \
-X POST https://localhost:4567/sessions

[Pass token value in X-CSRF-Token]
curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-i -H 'Content-Type: application/json' \
-H 'X-CSRF-Token: <token>' \
-d '{"name":"test space","owner":"test"}' \
https://localhost:4567/spaces

### Tokens in web storage
mvn clean compile exec:java -Djava.security.egd=file:/dev/urandom

curl --cacert "$(mkcert -CAROOT)/rootCA.pem" \
-H 'Content-Type: application/json' \
-d '{"username":"test","password": "password"}' \
https://localhost:4567/users

mvn clean compile exec:java -Dexec.args=9999
https://localhost:9999/login.html
submit usrename/password to API server
create space/test to API server

### HMAC
[cd to the root folder]
keytool -genseckey -keyalg HmacSHA256 -keysize 256 \
-alias hmac-key -keystore keystore.p12 \
-storetype PKCS12 \
-storepass changeit

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


