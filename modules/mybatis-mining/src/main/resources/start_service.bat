@echo off
set SERVICE_NAME=MyTask

echo Starting the service...
prunsrv //ES//%SERVICE_NAME%
echo Service started.
