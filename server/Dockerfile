FROM openjdk:17-oracle
ARG JAR_FILE=build/libs/server-1.0.00.war
COPY ${JAR_FILE} app.war
ENTRYPOINT ["java", "-jar", "app.war", "--spring.profiles.active=prod"]