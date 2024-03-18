FROM openjdk:17
EXPOSE 10000
ADD target/*.jar batch-processing-service-1.0.jar
ENTRYPOINT ["java","-jar","/batch-processing-service-1.0.jar"]