FROM mcr.microsoft.com/java/jre:11-zulu-alpine

RUN echo -e 'https://mirrors.aliyun.com/alpine/v3.6/main/\nhttps://mirrors.aliyun.com/alpine/v3.6/community/' > /etc/apk/repositories

RUN apk add tzdata && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
     && echo "Asia/Shanghai" > /etc/timezone \
     && apk del tzdata

ADD ./target/normal-user-server.jar app.jar
ENTRYPOINT ["java","-Xms256m","-Xmx256m", "-Dspring.profiles.active=prod", "-jar","/app.jar"]
