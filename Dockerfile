FROM adoptopenjdk/openjdk11:latest

WORKDIR /app

EXPOSE 8080

# ./mvnw dependency:go-offline

CMD (./mvnw spring-boot:run)
