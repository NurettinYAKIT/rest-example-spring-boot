FROM maven:3.6.3-openjdk-15-slim AS build
ARG github_token
ENV GITHUB_TOKEN=$github_token
RUN mkdir -p /opt/app
WORKDIR /opt/app
COPY pom.xml .github/settings.xml ./
RUN mvn -e -B  --settings settings.xml dependency:resolve --no-transfer-progress
COPY src ./src
RUN mvn -e -B package --settings settings.xml -Dmaven.test.skip=true --no-transfer-progress

FROM adoptopenjdk/openjdk15:alpine-slim
RUN mkdir -p /opt/app
COPY --from=build /opt/app/target/rest-api-example.jar /opt/app

ENV PORT 8080
ENTRYPOINT ["java","-Xmx512m", "-Djava.security.egd=file:/dev/./urandom","-jar","/opt/app/rest-api-example.jar"]
EXPOSE $PORT
