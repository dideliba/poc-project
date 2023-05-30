FROM ibm-semeru-runtimes:open-17.0.4.1_1-jre-jammy

VOLUME /tmp

ARG JAR_FILE
COPY ${JAR_FILE} gateway-ms-0.0.2-SNAPSHOT.jar

EXPOSE 9090
ENTRYPOINT ["java","-jar","/gateway-ms-0.0.2-SNAPSHOT.jar"]


