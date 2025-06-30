# Image de base : Java 17 avec Maven
FROM maven:3.9.10-eclipse-temurin-24-alpine

# Installer outils utiles
RUN apk add --no-cache bash curl git

EXPOSE 8080

# Dossier de travail
WORKDIR /workspace

# Le code sera monté automatiquement ici