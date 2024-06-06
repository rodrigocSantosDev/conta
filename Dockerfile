FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/conta-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080 5005
ENTRYPOINT ["java", "-jar", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "app.jar"]
