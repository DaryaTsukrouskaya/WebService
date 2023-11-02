FROM openjdk:17
ARG JAR_FILE
COPY target/WebService-0.0.1-SNAPSHOT.jar /WebService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","WebService-0.0.1-SNAPSHOT.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]