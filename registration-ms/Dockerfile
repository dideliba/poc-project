FROM ibm-semeru-runtimes:open-17.0.4.1_1-jre-jammy
VOLUME /tmp

ARG JAR_FILE
COPY ${JAR_FILE} registration-ms-0.0.2-SNAPSHOT.jar

EXPOSE 9081
ENTRYPOINT ["java","-XX:+UnlockExperimentalVMOptions","-XX:MaxRAMFraction=1","-XshowSettings:vm","-jar","/registration-ms-0.0.2-SNAPSHOT.jar"]


