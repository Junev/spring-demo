@echo off
 
rem 设置程序名称
set SERVICE_EN_NAME=MyTask
set SERVICE_CH_NAME=MyTask
 
rem 设置java路径
set JAVA_HOME=%JAVA_HOME%
 
rem 设置程序依赖及程序入口类
cd .
set BASEDIR=%CD%
set CLASSPATH=%BASEDIR%\mytask-0.0.1-SNAPSHOT.jar
set MAIN_CLASS=org.springframework.boot.loader.JarLauncher
 
rem 设置prunsrv路径
set SRV=%BASEDIR%\prunsrv.exe
set MGR=%BASEDIR%\prunmgr.exe
 
rem 设置日志路径及日志文件前缀
set LOGPATH=%BASEDIR%
 
rem 输出信息
echo SERVICE_NAME: %SERVICE_EN_NAME%
echo JAVA_HOME: %JAVA_HOME%
echo MAIN_CLASS: %MAIN_CLASS%
echo prunsrv path: %SRV%
 
rem 设置jvm
if "%JVM%" == "" goto findJvm
if exist "%JVM%" goto foundJvm
:findJvm
set "JVM=%JAVA_HOME%\jre\bin\server\jvm.dll"
if exist "%JVM%" goto foundJvm
echo can not find jvm.dll automatically,
echo please use COMMAND to localation it
echo then install service
set JVM=C:\Program Files\Java\jdk1.8.0_271\jre\bin\server\jvm.dll
go
goto foundJvm
:foundJvm
echo 正在安装服务...
rem 安装
%SRV% //IS//%SERVICE_EN_NAME% --DisplayName="%SERVICE_CH_NAME%" ^
        "--Classpath=%CLASSPATH%" "--Install=%SRV%" ^
        "--JavaHome=%JAVA_HOME%" "--Jvm=%JVM%" --JvmMs=256 --JvmMx=1024 ^
        --Startup=auto ^
        --JvmOptions=-Djcifs.smb.client.dfs.disabled=false ^
        ++JvmOptions=-Djcifs.resolveOrder=DNS ^
        --StartMode=jvm ^
        --StartClass=%MAIN_CLASS% ^
        --StartMethod=main ^
        --StopMode=jvm ^
        --StopClass=%MAIN_CLASS% ^
        --StopMethod=main ^
        --StopParams= ^
        --StopTimeout=3 ^
        --LogPath=%LOGPATH% ^
        --StdOutput=auto ^
        --StdError=auto
echo 安装服务完成。
:end
pause
