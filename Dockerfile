FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/team-standings-web*.jar

WORKDIR /opt/app

COPY ${JAR_FILE} team-standings-web.jar

ENTRYPOINT ["java", "-jar", "team-standings-web.jar"]