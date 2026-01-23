Write-Host "========================================"
Write-Host "Starting Exam System Backend"
Write-Host "========================================"

# 设置 Java 路径
$env:JAVA_HOME = "D:\programming\java\windowsx64\jdk-8u381-windows-x64\progrom"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# 检查 Java 版本
Write-Host "`nJava Version:"
java -version

# 查找 Maven
$mavenHome = $null

# 检查常见的 Maven 安装位置
$possibleLocations = @(
    "C:\Program Files\Apache\maven",
    "C:\apache-maven",
    "C:\Program Files\JetBrains\IntelliJ IDEA 2024.3.1.1\plugins\maven\lib\maven3",
    "C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2024.3.1.1\plugins\maven\lib\maven3"
)

foreach ($location in $possibleLocations) {
    if (Test-Path $location) {
        $mavenHome = $location
        break
    }
}

# 搜索所有 IntelliJ IDEA 安装目录
if ($null -eq $mavenHome) {
    Get-ChildItem "C:\Program Files\JetBrains" -Directory -ErrorAction SilentlyContinue | ForEach-Object {
        $mavenPath = Join-Path $_.FullName "plugins\maven\lib\maven3"
        if (Test-Path $mavenPath) {
            $mavenHome = $mavenPath
            return
        }
    }
}

if ($null -eq $mavenHome) {
    Write-Host "`nERROR: Maven not found!" -ForegroundColor Red
    Write-Host "`nPlease use one of these options:" -ForegroundColor Yellow
    Write-Host "1. Open IntelliJ IDEA and run the project from there (Recommended)"
    Write-Host "   - Open project in IDEA"
    Write-Host "   - Wait for Maven to sync"
    Write-Host "   - Right-click ExamSystemBackendApplication.java"
    Write-Host "   - Select 'Run ExamSystemBackendApplication.main()'"
    Write-Host "`n2. Install Maven manually:"
    Write-Host "   - Download from: https://maven.apache.org/download.cgi"
    Write-Host "   - Extract to C:\apache-maven"
    Write-Host "   - Run this script again"
    Read-Host "`nPress Enter to exit"
    exit 1
}

Write-Host "`nUsing Maven from: $mavenHome"
$env:PATH = "$mavenHome\bin;$env:PATH"

# 清理并编译
Write-Host "`nCleaning and compiling project..."
& mvn clean compile

if ($LASTEXITCODE -ne 0) {
    Write-Host "`nBuild failed! Please check the errors above." -ForegroundColor Red
    Read-Host "`nPress Enter to exit"
    exit $LASTEXITCODE
}

# 运行项目
Write-Host "`nStarting Spring Boot application..."
& mvn spring-boot:run

Read-Host "`nPress Enter to exit"
