FROM openjdk:21-oracle

WORKDIR /app

COPY build/libs/2_7_work-0.0.1-SNAPSHOT.jar app.jar

ENV STUDENT_GENERATOR=true
ENV RANDOM_MAX_STUDENT_COUNT=30

CMD ["java", "-jar", "app.jar"]