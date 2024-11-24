FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY /Server/pom.xml .
COPY /Server/src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim AS final

WORKDIR /app

COPY --from=build /app/target/Server-0.0.1-SNAPSHOT.jar /app

# Set file permissions
RUN chmod +r /app/Server-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/Server-0.0.1-SNAPSHOT.jar"]
