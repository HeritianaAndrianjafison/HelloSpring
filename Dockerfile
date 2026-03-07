FROM openjdk:17.0.2-jdk-slim

WORKDIR /app

# Copier le jar compilé
COPY target/*.jar app.jar

# Exposer le port interne (peut rester 8080)
EXPOSE 8586

# Démarrer l'application sans forcer un port
ENTRYPOINT ["java", "-jar", "/app/app.jar"]