# FROM 指明该镜像依赖于哪个基础镜像。
# SpringBoot依赖于Maven和Java。Maven用于构建依赖，Java用于执行代码。因此引用这个镜像。
# 这个镜像帮助我们打包了Maven3.5和Java8。
# 用这个镜像就不用自己去写安装Maven和Java的命令。
FROM maven:3.5-jdk-8-alpine as builder

# WORKDIR：指定镜像的工作目录。
# COPY：将本地的代码放到容器镜像中。
# 我们需要将项目的代码放到镜像中。
# 将pom.xml放到当前目录（app）中，将src文件夹放到./src目录（app/src）中。
# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# RUN 命令来执行Maven的打包命令。
# Build a release artifact.
RUN mvn package -DskipTests

# 要运行镜像的时候会默认执行这个命令。
# 这个命令可以在启动Docker的时候覆盖掉。
# Run the web service on container startup.
CMD ["java","-jar","/app/target/father-backend-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]