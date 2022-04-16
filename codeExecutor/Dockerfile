FROM maven:3.5-jdk-11 AS build
COPY /manager/src/* /usr/src/app/src/
COPY manager/src/main/resources/docker/codeexecutor/docker-file-pom.xml /usr/src/app
RUN mvn -f /usr/src/app/docker-file-pom.xml clean package

FROM gcr.io/distroless/java
COPY --from=0 /usr/src/app/target/CodeExecutionService-1.0.0-SNAPSHOT.jar /usr/app/CodeExecutionService-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/usr/app/CodeExecutionService-1.0.0-SNAPSHOT.jar"]