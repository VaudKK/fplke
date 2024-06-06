FROM openjdk:17-jdk-slim AS build
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:17-jdk-slim
COPY --from=build /target/*.jar ms-authentication.jar
ENTRYPOINT ["java","-Dspring.profiles.active=production","-jar","ms-authentication.jar"]