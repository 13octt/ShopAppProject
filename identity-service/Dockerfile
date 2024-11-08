# Sử dụng image Java từ OpenJDK làm base image
FROM openjdk:17-jdk-slim as build

# Cài đặt Maven (hoặc Gradle) trong container để xây dựng ứng dụng
RUN apt-get update && apt-get install -y maven

# Chỉ định thư mục làm việc trong container
WORKDIR /app

# Sao chép mã nguồn vào thư mục làm việc trong container
COPY . /app

# Dùng Maven để build ứng dụng (Cập nhật với lệnh build của bạn nếu sử dụng Gradle)
RUN mvn clean install -DskipTests

# Chạy ứng dụng Spring Boot từ file JAR đã build được
FROM openjdk:17-jdk-slim

# Chỉ định thư mục làm việc trong container cho giai đoạn chạy ứng dụng
WORKDIR /app

# Sao chép file JAR đã build từ giai đoạn build vào image
COPY --from=build /app/target/*.jar /app/app.jar

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Mở cổng ứng dụng (thường mặc định là 8080)
EXPOSE 8080
