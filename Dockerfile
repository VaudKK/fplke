FROM maven:3-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar ms-authentication.jar

EXPOSE 8080

CMD ["java","-Dspring.profiles.active=production","-jar","ms-authentication.jar"]


##
## Build stage
##
#FROM openjdk:17-jdk-slim AS build
#COPY . .
#RUN mvn clean package -DskipTests
#FROM openjdk:17-jdk-slim
#COPY --from=build /target/*.jar ms-authentication.jar
#ENTRYPOINT ["java","-Dspring.profiles.active=production","-jar","rest-api-template.jar"]
#
#
#COPY src /home/app/src
#COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package
#
##
## Package stage
##
#FROM openjdk:17-jdk-slim
#COPY --from=build /home/app/target/*.jar /usr/local/lib/ms-authentication.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/usr/local/lib/ms-authentication.jar"]
#
#
#
#FROM openjdk:17-jdk-slim AS build
#COPY . .
#RUN mvn clean package -DskipTests
#FROM openjdk:17-jdk-slim
#COPY --from=build /target/*.jar ms-authentication.jar
#ENTRYPOINT ["java","-Dspring.profiles.active=production","-jar","ms-authentication.jar"]