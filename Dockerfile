FROM node:13.7.0-alpine as build-angular
COPY mini-auth-ng /usr/src/app/mini-auth/mini-auth-ng
WORKDIR /usr/src/app/mini-auth/mini-auth-ng
RUN npm install
RUN npm run build --prod
RUN ls -l /usr/src/app/mini-auth/mini-auth-ng/dist/

FROM openjdk:8-jdk-alpine as gradle-builder
COPY . /usr/src/app/mini-auth
WORKDIR /usr/src/app/mini-auth
RUN ./gradlew assemble
CMD exec java -Djava.security.egd=file:/dev/./urandom -jar mini-auth-boot/build/libs/mini-auth-boot-0.0.1-SNAPSHOT.jar
# ENTRYPOINT exec java -Djava.security.egd=file:/dev/./urandom -jar mini-auth-boot/build/libs/mini-auth-boot-0.0.1-SNAPSHOT.jar





# FROM openjdk:8-jdk-alpine
# VOLUME /tmp
# ARG JAVA_OPTS
# ENV JAVA_OPTS=$JAVA_OPTS
# ADD build/libs/mini-auth-0.0.1.jar mini-auth.jar
# EXPOSE 8080
# ENTRYPOINT exec java $JAVA_OPTS -jar mini-auth.jar
# # For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
# #ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar mini-auth.jar

# $ sudo docker image build -t mini-auth:1.0.0
