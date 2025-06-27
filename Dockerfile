# Use an OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy JAR (replace with your actual JAR file name)
COPY target/support-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on (e.g., 8080)
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-Dmanagement.metrics.enable.process=false", "-jar", "app.jar"]

