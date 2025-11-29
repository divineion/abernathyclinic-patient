FROM eclipse-temurin:24-jre

LABEL description="Backend service for patient management"
EXPOSE 8081

WORKDIR /app

COPY ./target/abernathyclinic-patient-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar" ]
