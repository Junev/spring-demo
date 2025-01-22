@echo off
set SERVICE_NAME=MyTask

echo Stopping the service...
prunsrv //SS//%SERVICE_NAME%
echo Service stopped.
