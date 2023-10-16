# Demo application with Keycloak

This is a demo application to experiment oauth2 client and resource server with Keycloak as authentication provider.

## Requirements
 - Gradle
 - Docker
 - Java 17 or later

## How to run
Run Keycloak as a docker container anc set up a client and a few users. Use the following command line to execute it:
```shell
docker run -d -p 8080:8080 \
  --name keycloak \
  --restart=unless-stopped  \
  -e KEYCLOAK_ADMIN=admin \
  -e KEYCLOAK_ADMIN_PASSWORD=admin \
  quay.io/keycloak/keycloak:22.0.4 start-dev
```
## Complementary UI project
A React project that demonstrates the exchange of the Authorization code with a jwt token can be found [Here](https://github.com/jlcorradi/keycloak-integration-demo-ui). 