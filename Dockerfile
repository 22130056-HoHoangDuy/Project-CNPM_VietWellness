# --- Build stage: dùng Maven để build JAR ---
FROM maven:3.8.5-openjdk-17 AS build

# Thiết lập thư mục làm việc
WORKDIR /app

# Copy pom và source vào container
COPY pom.xml .
COPY src ./src

# Build package, bỏ qua test để nhanh
RUN mvn clean package -DskipTests

# --- Runtime stage: chỉ chứa JRE và JAR đã build ---
FROM openjdk:17-jdk-slim

# Thiết lập thư mục làm việc
WORKDIR /app

# Copy file JAR từ build stage vào
COPY --from=build /app/target/*.jar app.jar

# Mở port 8080 (điều này chỉ để document; Render sẽ bind qua biến PORT)
EXPOSE 8080

# ENTRYPOINT gọi Java trực tiếp, Spring Boot sẽ đọc server.port từ application.properties
ENTRYPOINT ["java", "-jar", "app.jar"]
