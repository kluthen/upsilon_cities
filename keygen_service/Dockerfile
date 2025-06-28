# Étape 1 : build de l'exécutable Go
FROM golang:1.21-alpine AS builder

WORKDIR /app

# Copier les fichiers go.mod et go.sum pour le cache des dépendances
COPY go.mod go.sum ./
RUN go mod download

# Copier tout le code source
COPY . .

# Compiler l'application en mode statique et sans debug
RUN CGO_ENABLED=0 GOOS=linux go build -o keygen_service ./cmd/main.go

# Étape 2 : image finale minimale
FROM alpine:latest

# Installer ca-certificates pour les connexions HTTPS (si besoin)
RUN apk --no-cache add ca-certificates

WORKDIR /app

# Copier le binaire compilé depuis le builder
COPY --from=builder /app/keygen_service .

# Port d'écoute (adapter selon ton code)
EXPOSE 9000

# Variable d'environnement par défaut (modifiable via docker-compose)
ENV REDIS_HOST=redis
ENV REDIS_PORT=6379

# Commande de démarrage
CMD ["./keygen_service"]