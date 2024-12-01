FROM eclipse-temurin:17-jdk-alpine
COPY build/libs/vrss-api-*.jar vrss-api.jar
EXPOSE 8080
ENV JAVA_OPTS="-Dspring.profiles.active=tb"
ENTRYPOINT exec java $JAVA_OPTS -jar vrss-api.jar