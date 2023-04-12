FROM maven:3.8.1-openjdk-17 as target
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ build/src/
RUN mvn package

FROM openjdk:17-alpine
ADD target/mycompanybackend.jar mycompanybackend.jar
ENTRYPOINT ["java", "-jar","mycompanybackend.jar"]
EXPOSE 8080