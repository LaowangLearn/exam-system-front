Write-Host "========================================"
Write-Host "Starting Exam System Backend"
Write-Host "========================================"

# 设置 Java 路径
$env:JAVA_HOME = "D:\programming\java\windowsx64\jdk-8u381-windows-x64\program"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# 检查 Java 版本
Write-Host "`nJava Version:"
java -version

# 设置 Maven 路径（直接使用您指定的路径）
$env:MAVEN_HOME = "D:\programming\Maven\apache-maven-3.8.8"
$env:PATH = "$env:MAVEN_HOME\bin;$env:PATH"

# 检查 Maven 版本
Write-Host "`nMaven Version:"
mvn -version

# 切换到项目目录
Set-Location -Path "D:\programming\projects\99examsystem\exam-system-backend"

# 清理并编译
Write-Host "`nCleaning and compiling project..."
mvn clean compile

if ($LASTEXITCODE -ne 0) {
    Write-Host "`nBuild failed! Please check the errors above." -ForegroundColor Red
    Read-Host "`nPress Enter to exit"
    exit $LASTEXITCODE
}

# 运行项目
Write-Host "`nStarting Spring Boot application..."
mvn spring-boot:run

Read-Host "`nPress Enter to exit"
