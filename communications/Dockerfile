FROM openjdk:11-jdk-slim
COPY build/libs/communications-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080:8080
CMD ["java", "-jar", "app.jar"]