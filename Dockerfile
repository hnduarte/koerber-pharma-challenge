FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/koerber-pharma-challenge-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]