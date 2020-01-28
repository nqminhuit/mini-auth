FROM node:13.7.0-alpine as build-angular
COPY mini-auth-ng /usr/src/app/mini-auth/mini-auth-ng
WORKDIR /usr/src/app/mini-auth/mini-auth-ng
RUN npm install
RUN npm run build --prod

FROM openjdk:8-jdk-alpine as gradle-builder
COPY . /usr/src/app/mini-auth
WORKDIR /usr/src/app/mini-auth
RUN ./gradlew assemble
CMD exec java -jar mini-auth-boot/build/libs/mini-auth-boot-0.0.1-SNAPSHOT.jar
