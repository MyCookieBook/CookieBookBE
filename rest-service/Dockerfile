FROM gradle:openjdk-15-slim AS builder
WORKDIR /app
ADD . /app
RUN gradle clean build

FROM openjdk:15-jre-slim
WORKDIR /app
COPY --from=builder /app/build/libs/rest-service-0.0.1-SNAPSHOT.jar rest-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/rest-service.jar"]