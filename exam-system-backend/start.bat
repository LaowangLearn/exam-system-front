@echo off
echo 设置环境变量...
set "JAVA_HOME=D:\programming\java\windowsx64\jdk-8u381-windows-x64"
set "MAVEN_HOME=D:\programming\Maven\apache-maven-3.8.8"
set "PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%"

echo 验证环境...
echo Java Home: %JAVA_HOME%
echo Maven Home: %MAVEN_HOME%
java -version
echo.
mvn -version

echo.
echo 启动项目...
mvn spring-boot:run

pause
