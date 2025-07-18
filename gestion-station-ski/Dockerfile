FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/gestion-station-ski-1.0.jar app.jar

EXPOSE 8089

CMD ["java", "-jar", "app.jar"]
