FROM gradle:8.3.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build
RUN mkdir /app && cp /home/gradle/src/build/libs/payment-service.jar /app/payment-service.jar

FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
RUN mkdir /app
COPY --from=build /app /app
WORKDIR /app
ENTRYPOINT ["java","-jar","payment-service.jar"]
