FROM openjdk:17
EXPOSE 10000
ADD target/batch-processing-service-0.0.1-SNAPSHOT.jar batch-processing-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT["java","-jar","/batch-processing-service-0.0.1-SNAPSHOT.jar"]