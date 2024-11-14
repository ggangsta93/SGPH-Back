FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/sgph-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} sgph-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "sgph-0.0.1-SNAPSHOT.jar" ]