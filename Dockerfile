FROM openjdk:17.0.2-jdk-slim

WORKDIR /app

# Copier le jar compilé
COPY target/*.jar app.jar

# Exposer le port interne par défaut de Spring Boot (contournable à la création du conteneur)
EXPOSE 8080

# Démarrer l'application sans forcer le port ni l'adresse
# Le port peut être mappé depuis l'extérieur via docker run -p <host_port>:8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]