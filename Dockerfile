# Aşama 1: Derleme
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
# Anahtar nokta: İçteki habersitesi klasöründekileri kopyala
COPY habersitesi/ . 
RUN mvn clean package -DskipTests

# Aşama 2: Çalıştırma
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
