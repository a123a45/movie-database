# ─── Stage 1: Build Spring Boot backend ──────────
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY backend/pom.xml .
RUN mvn dependency:go-offline -B
COPY backend/src ./src
RUN mvn package -DskipTests -B

# ─── Stage 2: Runtime ────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Alpine JRE lacks native image codecs; install them for ImageIO JPEG/PNG
RUN apk add --no-cache libjpeg-turbo libpng ttf-dejavu

RUN addgroup -S app && adduser -S app -G app
USER app

COPY --from=builder /app/target/*.jar app.jar

# Include poster images
COPY backend/uploads /app/uploads

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT:8080}"]
