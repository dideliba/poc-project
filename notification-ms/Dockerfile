FROM ibm-semeru-runtimes:open-17.0.4.1_1-jre-jammy
VOLUME /tmp

ARG JAR_FILE
COPY ${JAR_FILE} notification-ms-0.0.2-SNAPSHOT.jar

EXPOSE 9082
ENTRYPOINT ["java","-jar","/notification-ms-0.0.2-SNAPSHOT.jar"]


