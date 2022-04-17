# This works under the assumption that context path to docker build is passed as absolute path for codeExecutor directory
FROM openjdk:11-jre-slim
RUN mkdir -p /usr/local
WORKDIR /usr/local
COPY target/codeExecutor-1.0-SNAPSHOT.jar codeExecutor-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","codeExecutor-1.0-SNAPSHOT.jar"]