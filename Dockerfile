# --- Build stage ---
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build Spring Boot JAR (bỏ test để nhanh)
RUN mvn clean package -DskipTests

# --- Runtime stage ---
FROM openjdk:17-jdk-slim

# Tạo thư mục chứa JAR
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render yêu cầu app bind tới 0.0.0.0:$PORT
ENV JAVA_OPTS="-Dserver.address=0.0.0.0 -Dserver.port=\$PORT"
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
