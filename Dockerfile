FROM openjdk:17
EXPOSE 10000
ADD target/batch-processing-service.jar batch-processing-service.jar
ENTRYPOINT["java","-jar","/batch-processing-service.jar"]