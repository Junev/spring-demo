FROM adoptopenjdk/openjdk8
ENV TIME_ZONE Asia/Shanghai
RUN cp /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime \
    && echo "${TIME_ZONE}" > /etc/timezone
ADD mqtt-0.0.1-SNAPSHOT.jar /usr/src/myapp/mqtt.jar
WORKDIR /usr/src/myapp
CMD ["java", "-jar", "mqtt.jar"]
