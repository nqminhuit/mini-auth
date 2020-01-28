# Project Setup
## Build:
```
$ cd mini-auth/
$ ./gradlew clean
$ ./gradlew npmRunBuild
$ ./gradlew assemble
```

## Deploy:
```
$ java -jar mini-auth-boot/build/libs/mini-auth-boot-<app_version>.jar
```

## Application business:
- Access application at: http://localhost:8080
- You will be redirected to login page: http://localhost:8080/login
- Provide credentials (see the table below)
- Upon successful login, you will be redirected to http://localhost:8080/customer
- Only user with ADMIN role can perform "List all customers" function

## Initial list of credentials:
|ID|Username|Role|password
|---|---|---|---|
|  0 | batman   | admin | m1n14uth
|  1 | joker    | user  | m1n14uth
|  2 | catwoman | user  | m1n14uth

## Swagger:
- UI: http://localhost:8080/swagger-ui.html
- raw: http://localhost:8080/v2/api-docs
