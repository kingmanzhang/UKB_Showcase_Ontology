# Use an official Maven image as the base image
FROM maven:3.8.6-openjdk-11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy necessary files from the current directory to the container's working directory
COPY pom.xml .
COPY src ./src

# Build the project using Maven and create the JAR file
RUN mvn clean package

# Use a smaller base image for running the application
#FROM openjdk:11-jre-slim
FROM openjdk:11

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage to the current directory
#COPY --from=build /app/target/*-jar-with-dependencies.jar app.jar
COPY --from=build /app/target/*-shaded.jar app.jar

# Command to run the JAR file
CMD ["java", "-jar", "app.jar", "ontologize", "-o", "/app/data/ontology/ukb_showcase_ontology.ttl"]