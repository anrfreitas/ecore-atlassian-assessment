FROM adoptopenjdk/openjdk11:latest

WORKDIR /app

EXPOSE 8080

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests

CMD (./mvnw spring-boot:run)
