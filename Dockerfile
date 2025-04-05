FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/codingexercise-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ARG SPRING_PROFILES=default
ENV SPRING_PROFILES_ACTIVE=dev
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","--enable-preview", "-jar","/app.jar"]