FROM registry.cn-shanghai.aliyuncs.com/qtech/maven:3.6-jdk-8 AS builder

WORKDIR /usr/src/app
COPY ./pom.xml /usr/src/app/pom.xml
COPY ./settings.xml /usr/src/app/settings.xml
RUN mvn dependency:go-offline -s /usr/src/app/settings.xml
COPY ./src /usr/src/app/src
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests  -s /usr/src/app/settings.xml

# 如果基础镜像中没有时区数据，需要安装 tzdata 包
RUN apt-get update && apt-get install -y tzdata

# 设置时区为Asia/Shanghai
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

FROM registry.cn-shanghai.aliyuncs.com/kolenz/openjdk:8-jre-alpine-font
LABEL maintainer=gaoolin@gmail.com

ENV PARAMS="--server.port=8080"

EXPOSE 8080

COPY --from=builder /usr/src/app/target/*.jar /home/app.jar
COPY --from=builder /usr/src/app/target/libs /home/libs/

WORKDIR /home

# 设置时区为Asia/Shanghai
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 指定了jvm启动的内存大小
ENTRYPOINT ["/bin/sh", "-c", "java -Dloader.path=/home/libs/ -Dfile.encoding=utf8 -Djava.security.egd=file:/dev/./urandom -jar -Xmx2048m -Xms2048m /home/app.jar ${PARAMS}"]
