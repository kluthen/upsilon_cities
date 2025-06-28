# Image de base : Java 17 avec Maven
FROM maven:3.9.5-eclipse-temurin-17-alpine

# Installer outils utiles
RUN apk add --no-cache bash curl git

# Créer un utilisateur non-root
RUN addgroup -S vscode && adduser -S vscode -G vscode
USER vscode

# Dossier de travail
WORKDIR /workspace

# Le code sera monté automatiquement ici