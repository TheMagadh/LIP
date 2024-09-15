FROM maven:3.9.5 AS build
WORKDIR /aap
COPY pom.xml
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean
RUN mvn package -DskipTests -X

FROM openjdk
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD["java","-jar","app.jar"]
