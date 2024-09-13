FROM registry.cn-shanghai.aliyuncs.com/qtech/maven:3.6-jdk-8 AS builder

WORKDIR /usr/src/app
COPY ./pom.xml /usr/src/app/pom.xml
COPY ./settings.xml /usr/src/app/settings.xml
RUN mvn dependency:go-offline -s /usr/src/app/settings.xml
COPY ./src /usr/src/app/src
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests  -s /usr/src/app/settings.xml dependency:resolve


FROM registry.cn-shanghai.aliyuncs.com/kolenz/openjdk:8-jre-alpine-font
LABEL maintainer=gaoolin@gmail.com


ENV PARAMS="--server.port=8080"

EXPOSE 8080

COPY --from=builder /usr/src/app/target/*.jar /home/app.jar
COPY --from=builder /usr/src/app/target/libs /home/libs/

WORKDIR /home

# CMD exec java -Dloader.path="/home/libs/" -jar /home/app.jar
# CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
ENTRYPOINT ["/bin/sh", "-c", "java -Dloader.path=/home/libs/ -Dfile.encoding=utf8 -Djava.security.egd=file:/dev/./urandom -jar /home/app.jar ${PARAMS}"]
