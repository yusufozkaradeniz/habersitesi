FROM maven:3.8.5-openjdk-17 AS build
# Kodların olduğu klasöre gitmesini sağlıyoruz
COPY habersitesi /app
WORKDIR /app
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
# Oluşan dosyayı doğru yerden kopyalıyoruz
COPY --from=build /app/target/*.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]
