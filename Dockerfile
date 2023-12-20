FROM openjdk:17

ARG WORKDIR=/opt/freud

WORKDIR ${WORKDIR}
COPY target/lib ./lib

EXPOSE 5508

ENV LANG=zh_CN.UTF-8
ENV LANGUAGE=zh_CN.UTF-8
ENV LC_ALL=zh_CN.UTF-8
ENV TZ=Asia/Shanghai
ENV SPRING_PROFILES_ACTIVE=prod

COPY target/*.jar freud.jar

ENTRYPOINT java -jar freud.jar