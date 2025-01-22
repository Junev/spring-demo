@echo off
set SERVICE_NAME=MyTask

echo Uninstalling the service...
prunsrv //DS//%SERVICE_NAME%
echo Service uninstalled successfully.
