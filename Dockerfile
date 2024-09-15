# Use Maven image to build the application
FROM maven:3.9.5 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file and resolve the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the application files and build the project
COPY . .
RUN mvn clean package -DskipTests -X

# Use a lightweight OpenJDK image to run the application
FROM openjdk:17-jdk-slim

# Copy the built JAR file from the Maven build stage
COPY --from=build /app/target/*.jar /app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Set the command to run the application
CMD ["java", "-jar", "/app.jar"]
