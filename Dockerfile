FROM openjdk:17.0.2-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080 8081 8082  # Exposer tous les ports possibles

ENTRYPOINT ["java", "-jar", "/app/app.jar"]