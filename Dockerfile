FROM openjdk:8-jdk-alpine             
ARG JAR_FILE=target/FileSystem-0.0.1-SNAPSHOT/WEB-INF/lib/*.jar                    
COPY ${JAR_FILE} app.jar               
ENTRYPOINT ["java","-jar","/app.jar"]