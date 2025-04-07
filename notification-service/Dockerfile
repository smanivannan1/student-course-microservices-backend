# Step 1: Use an official JDK image
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set the working directory
WORKDIR /app

# Step 3: Copy the built JAR file into the container
COPY build/libs/notification-service-*.jar app.jar

# Step 4: Expose the port your service runs on
EXPOSE 8084

# Step 5: Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]

