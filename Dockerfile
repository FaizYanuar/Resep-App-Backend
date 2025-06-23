# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper files
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Build dependencies
# Using the pom.xml and mvnw, this downloads all necessary libraries
RUN ./mvnw dependency:go-offline

# Copy the rest of the application source code
COPY src ./src

# Package the application into a JAR file
RUN ./mvnw package -DskipTests

# The final command to run the application
# It will find the JAR file in the 'target' directory
CMD ["java", "-jar", "target/*.jar"]