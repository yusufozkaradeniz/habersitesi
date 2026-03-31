# 1. Aşama: Derleme
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY habersitesi/ .
RUN mvn clean package -DskipTests

# 2. Aşama: Çalıştırma (Hata veren kısmı düzelttim)
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
