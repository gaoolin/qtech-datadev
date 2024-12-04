# 构建阶段
FROM registry.cn-shanghai.aliyuncs.com/qtech/maven:3.6-jdk-8 AS builder

WORKDIR /usr/src/app

# 复制 pom.xml 和 settings.xml，利用缓存
COPY ./pom.xml /usr/src/app/pom.xml
COPY ./settings.xml /usr/src/app/settings.xml

# 下载依赖
RUN mvn dependency:go-offline -s /usr/src/app/settings.xml

# 复制源代码
COPY ./src /usr/src/app/src

# 编译打包
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests -s /usr/src/app/settings.xml dependency:resolve

# 运行阶段
FROM registry.cn-shanghai.aliyuncs.com/kolenz/openjdk:8-jre-alpine-font
LABEL maintainer=gaoolin@gmail.com

# 环境变量
ENV PARAMS="--server.port=8080"
ENV LOG_PATH=/home/qtech/logs
ENV SPRING_PROFILES_ACTIVE=prod

# 创建日志目录
RUN mkdir -p ${LOG_PATH}

EXPOSE 8080

# 复制构建阶段的输出
COPY --from=builder /usr/src/app/target/*.jar /home/app.jar
COPY --from=builder /usr/src/app/target/libs /home/libs/

WORKDIR /home

# 健康检查
HEALTHCHECK --interval=30s --timeout=30s --retries=5 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 启动命令
ENTRYPOINT ["/bin/sh", "-c", "java -Dloader.path=/home/libs/ -Dfile.encoding=utf8 -Djava.security.egd=file:/dev/./urandom -Dlogging.file.path=${LOG_PATH} -jar /home/app.jar ${PARAMS} --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
