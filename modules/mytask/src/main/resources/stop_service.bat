@echo off
set SERVICE_NAME=MyTask

echo Stopping the service...
prunsrv //SS//%SERVICE_NAME%
echo Service stopped.

echo 请通过任务管理器关闭
taskkill /IM prunsrv.exe /F
