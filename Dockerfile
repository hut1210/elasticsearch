FROM openjdk:8

#此处的*为正则表达式，表示以.jar结尾的同步录下的文件（这里的jar是maven打包好的）
COPY /*.jar /app.jar

CMD ["--server.port=8083"]

EXPOSE 8083

ENTRYPOINT ["java","-jar","/app.jar"]