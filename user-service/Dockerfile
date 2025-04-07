# Use OpenJDK 17 base image
FROM openjdk:17-jdk-slim

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=docker

# Create working directory
WORKDIR /app

# Copy the built jar file from the host (make sure you build it with Gradle first)
COPY build/libs/user-service-*.jar user-service.jar

# Copy application.properties if needed (optional if it’s already inside the JAR)
# COPY src/main/resources/application.properties src/main/resources/application.properties

# Expose the port your app runs on
EXPOSE 8081

# Run the jar file
ENTRYPOINT ["java", "-jar", "user-service.jar"]

