# 1. Aşama: Derleme
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
# Alt klasöre girmesi için
COPY habersitesi/ . 
RUN mvn clean package -DskipTests

# 2. Aşama: Çalıştırma (En garanti imaj)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
