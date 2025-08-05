FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the entire project
COPY . .

# Make mvnw executable
RUN chmod +x backend/mvnw

# Build the application
RUN cd backend && ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "backend/target/therapy-0.0.1-SNAPSHOT.jar"] 