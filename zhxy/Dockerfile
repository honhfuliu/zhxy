FROM openjdk:17
EXPOSE 8080
ADD zhxy-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]
#ENTRYPOINT ["java", "-jar", "/app.jar"]