FROM openjdk:17

WORKDIR /build

ENV SECRET_KEY=secret

ENV LIFE_TIME=600000

ADD /target/AuthService-0.0.1-SNAPSHOT.jar ./auth-service.jar

EXPOSE 8080

CMD java -jar auth-service.jar --name AuthService