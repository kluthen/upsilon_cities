# Image de base officielle Go avec Alpine (légère)
FROM golang:1.21-alpine

# Installer outils utiles (git, bash, etc.)
RUN apk add --no-cache git bash

# Créer un utilisateur non-root pour le développement
RUN addgroup -S vscode && adduser -S vscode -G vscode

USER vscode
WORKDIR /workspace

# Le dossier /workspace sera monté par VS Code automatiquement