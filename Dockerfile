FROM gcr.io/distroless/java17-debian11
ARG VERSION
COPY ./build/libs/auditserver-${VERSION}.jar /app/main.jar
WORKDIR /app
CMD ["main.jar"]
