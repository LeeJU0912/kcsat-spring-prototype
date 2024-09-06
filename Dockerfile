FROM bellsoft/liberica-openjdk-alpine:latest
LABEL authors="leeju"

CMD ["./gradlew", "clean", "build"]

VOLUME /tmp

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "/app.jar"]