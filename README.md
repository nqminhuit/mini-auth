# mini-auth
Create a mini application to manage list of users with the following available
features:
- login as a user with role Admin
- see the list of users with role Customer
- create new user with role Customer
- delete exists user with role Customer
- update exists user with role Customer

Non-functional requirements:
- Backend: Spring Boot + Java/Kotlin
- Frontend: React/Angular/Vue.js
- Flyway
- Postgres database
- Gradle
- Add some example tests to show how you would test the code
- Use and protect secure endpoints with JWT token
- Enable Swagger for generating documentation for API endpoints
- Upload your code to Github/Gitlab/Bitbucket.
- (Optional) Prepare script that will build backend/frontend and launch local docker-
compose configuration with all integrated components(backend/frontend/database)

---

# Project Setup
## Build:
```
$ ./gradlew clean assemble
```
## Deploy:
```
$ java -jar boot/build/libs/boot-<app_version>.jar
```
## Test with curl:
```
$ curl -X GET http://localhost:8080/customer?name=batman
$ curl -X POST http://localhost:8080/customer/create -H 'Content-Type:application/json' --data '{"username": "Bruce Wayne", "role": "Billionaire"}'
$ curl -X GET http://localhost:8080/customers
```
should return a JSON similar to this:
```
{
    "id":0,
    "username":"batman",
    "role":"customer"
}
```
## Execute Unit tests:
```
$ ./gradlew test
```
