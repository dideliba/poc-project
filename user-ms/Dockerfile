FROM ibm-semeru-runtimes:open-17.0.4.1_1-jre-jammy

VOLUME /tmp


ARG JAR_FILE
COPY ${JAR_FILE} user-ms-0.0.2-SNAPSHOT.jar


EXPOSE 9080
ENTRYPOINT ["java","-XX:+UnlockExperimentalVMOptions","-XX:MaxRAMFraction=1","-XshowSettings:vm","-jar","/user-ms-0.0.2-SNAPSHOT.jar"]


