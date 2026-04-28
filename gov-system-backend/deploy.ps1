# 政务一体化平台部署脚本

# Windows PowerShell部署脚本

# 1. 数据库初始化
Write-Host "正在初始化数据库..." -ForegroundColor Green
# 使用H2数据库自动初始化，或使用以下命令连接外部数据库:
# docker run -d --name gov-db -p 5432:5432 -e POSTGRES_PASSWORD=gov2026 -e POSTGRES_DB=govdb harbor.gov.cn/postgres:14

# 2. 后端部署
Write-Host "正在启动后端服务..." -ForegroundColor Green
cd ../gov-system-backend
# Maven打包
mvn clean package -DskipTests
# 运行
java -jar target/gov-affairs-platform-1.0.0.jar

# 3. 前端部署
Write-Host "正在构建前端..." -ForegroundColor Green
cd ../gov-system-frontend
npm install
npm run build

# 4. Nginx配置
Write-Host "正在配置Nginx..." -ForegroundColor Green
# 复制dist目录到nginx html目录
# 配置nginx.conf反向代理到后端8080端口

# 5. 启动命令
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "政务一体化平台部署完成!" -ForegroundColor Green
Write-Host "后端地址: http://localhost:8080" -ForegroundColor Yellow
Write-Host "前端地址: http://localhost:3000" -ForegroundColor Yellow
Write-Host "数据库控制台: http://localhost:8080/h2-console" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Cyan
