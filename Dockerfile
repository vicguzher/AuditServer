FROM openjdk:17-alpine
ARG VERSION
COPY ./build/libs/auditserver-${VERSION}.jar /app/main.jar
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "main.jar"]
