#FROM openjdk:8-jdk-alpine
#MAINTAINER xuranus

#WORKDIR /home/his/backend/target
#CMD ["java","-jar","neuhospital-0.0.1-SNAPSHOT.jar"]

#EXPOSE 8080

FROM maven:3.5.3-jdk-8
MAINTAINER xuranus

WORKDIR /home/his/backend 

CMD ["mvn","package"]
CMD ["java","-jar","-Djava.net.preferIPv4Stack=true","target/neuhospital-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080