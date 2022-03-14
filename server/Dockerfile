FROM openjdk:17-oracle
ARG JAR_FILE=build/libs/*.war
COPY ${JAR_FILE} app.war/
ENTRYPOINT ["java", "-jar", "app.war", "--spring.profiles.active=dev"]