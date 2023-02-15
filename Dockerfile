FROM openjdk:8-jdk-alpine

WORKDIR /app

VOLUME /tmp

COPY ./target/nctn-backend-0.0.1-SNAPSHOT.jar.jar app.jar

ENTRYPOINT ["java","-jar","./app.jar"]

