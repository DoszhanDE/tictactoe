FROM openjdk:18-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} tictactoe-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/tictactoe-0.0.1-SNAPSHOT.jar"]
