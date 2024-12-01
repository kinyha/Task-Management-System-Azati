# Build stage
FROM gradle:8.6.0-jdk21 AS build
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle gradle
COPY src src
RUN gradle bootJar --no-daemon

# Run stage
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/build/libs/app.jar ./
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]