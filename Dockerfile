FROM openjdk:17.0.2-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8081 8082 8083

ENTRYPOINT ["java", "-jar", "/app/app.jar"]