# ─── Stage 1: Build Spring Boot backend ──────────
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY backend/pom.xml .
RUN mvn dependency:go-offline -B
COPY backend/src ./src
RUN mvn package -DskipTests -B

# ─── Stage 2: Runtime (full JRE — ImageIO works out of box) ──
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

# Include poster images; ensure world-writable (Railway injects random UID)
COPY backend/uploads /app/uploads
RUN mkdir -p /app/uploads/posters && chmod -R 777 /app/uploads

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT:8080}"]
