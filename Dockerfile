FROM openjdk:17-alpine
ADD target/mycompanybackend.jar mycompanybackend.jar
ENTRYPOINT ["java", "-jar","mycompanybackend.jar"]
EXPOSE 8000