FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 1234

ENTRYPOINT ["java","-jar","/app/app.jar","--server.address=0.0.0.0","--server.port=1234"]