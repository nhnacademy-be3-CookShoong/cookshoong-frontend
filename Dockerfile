FROM eclipse-temurin:11
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} cookshoong-front.jar

ENTRYPOINT ["java", "-jar", "/cookshoong-front.jar"]
