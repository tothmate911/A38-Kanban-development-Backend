# Build stage
FROM maven:4.0.0-rc-4-eclipse-temurin-25 AS build
COPY . /tmp/kanban
RUN mvn -f /tmp/kanban/pom.xml clean install

# Run stage
FROM eclipse-temurin:25-jdk-alpine
WORKDIR /
COPY --from=build /tmp/kanban/target/kanban*.jar kanban.jar
EXPOSE 8080
CMD ["java",  "-jar", "kanban.jar"]