# Use Maven with JDK 21 for the build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Use a JDK 21 runtime base image for the final image
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY --from=build /app/target/project-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/project-0.0.1-SNAPSHOT.jar"]
