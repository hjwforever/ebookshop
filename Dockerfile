#从dcokerhub上拉取openjdk8
FROM openjdk:8-jdk-alpine

#在宿主机的/var/lib/docker目录下创建一个临时文件并把它链接到容器中的/tmp目录
VOLUME /tmp

#拷贝打包后的jar包并且重命名
ADD target/*.jar server.jar

#容器外露到服务器的端口
EXPOSE 8081

# 为了缩短 Tomcat 的启动时间，添加java.security.egd的系统属性指向/dev/urandom作为 ENTRYPOINT
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dserver.port=8081","-jar","/server.jar"]