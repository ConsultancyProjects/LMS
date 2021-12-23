FROM openjdk:8-jdk-alpine
MAINTAINER consultancyprojects
COPY target/tutor-0.0.1-SNAPSHOT.jar tutor-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/tutor-0.0.1-SNAPSHOT.jar"]
