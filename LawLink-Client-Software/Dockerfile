FROM gradle:7.5.0-jdk17

WORKDIR /app

COPY ./build/libs/LegalClientSoftware-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]