# Étape 1 : build Maven (avec cache des dépendances)
FROM maven:3.9.5-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : image runtime minimaliste
FROM eclipse-temurin:17-jre-alpine

# Crée un utilisateur non-root pour la prod
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Répertoire de l'application
WORKDIR /app

# Copie du JAR depuis l'étape de build
COPY --from=builder /app/target/*.jar app.jar

# Permissions
RUN chown appuser:appgroup app.jar

USER appuser

# Port utilisé par Spring Boot (modifiable via application.properties)
EXPOSE 8080

# Lancement de l'app
ENTRYPOINT ["java", "-jar", "app.jar"]