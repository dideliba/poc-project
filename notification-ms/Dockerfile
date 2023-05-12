FROM adoptopenjdk/openjdk11-openj9:alpine-slim
VOLUME /tmp

ARG JAR_FILE
COPY ${JAR_FILE} notification-ms-0.0.2-SNAPSHOT.jar

EXPOSE 9082
ENTRYPOINT ["java","-jar","/notification-ms-0.0.2-SNAPSHOT.jar"]


