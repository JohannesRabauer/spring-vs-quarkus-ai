# Stage 1: Build the Spring Boot application
FROM maven:3.9.11-eclipse-temurin-25 AS build

# Set working directory
WORKDIR /app

# Copy Maven project files
COPY pom.xml .
COPY src ./src

# Package the application (skip tests for faster builds)
RUN mvn clean package -DskipTests

# Stage 2: Run the Spring Boot application
FROM eclipse-temurin:25_36-jre-alpine

# Set working directory
WORKDIR /app

# Copy the built Spring Boot jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8081

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
