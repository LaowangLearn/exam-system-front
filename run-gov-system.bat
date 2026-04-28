# 政务一体化平台启动脚本

# 后端启动
cd %~dp0gov-system-backend
call mvn clean package -DskipTests
java -jar target\gov-affairs-platform-1.0.0.jar

# 另开窗口启动前端
start cmd /k "cd %~dp0gov-system-frontend && npm install && npm run dev"
