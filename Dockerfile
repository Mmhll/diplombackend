#FROM openjdk:17-alpine
#ADD target/mycompanybackend.jar mycompanybackend.jar
#ENTRYPOINT ["kotlin", "-jar","mycompanybackend.jar"]
#EXPOSE 8001

#FROM openjdk:8-jdk-alpine
#
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]