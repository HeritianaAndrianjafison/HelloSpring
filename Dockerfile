FROM openjdk:17.0.2-jdk-slim

WORKDIR /app

# Copier le jar compilé
COPY target/*.jar app.jar

# Exposer le port interne par défaut de Spring Boot
EXPOSE 8080

# Démarrer l'application sans forcer un port interne
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--server.address=0.0.0.0"]