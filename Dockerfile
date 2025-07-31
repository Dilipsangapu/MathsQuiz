# Stage 1: Build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY . .

# Use Maven Wrapper if available, else install Maven manually
RUN chmod +x mvnw || true
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Update with actual jar file name from /target folder
COPY --from=build /app/target/QuizGame-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
