set SERVICE_NAME=ESCALEService
set DIR="D:\UPC\Eduardo\fuentes\gateway-escale\escale\escale-gateway-assembly\target\escale"
set PR_INSTALL=%DIR%\prunsrv.exe
 
REM Service log configuration
set PR_LOGPREFIX=%SERVICE_NAME%
set PR_LOGPATH=%DIR%\logs
set PR_STDOUTPUT=%DIR%\logs\stdout.txt
set PR_STDERROR=%DIR%\logs\stderr.txt
set PR_LOGLEVEL=Debug
 
 
set DIR_CONFIG=%DIR%\config
set DIRS_JARS=%DIR%\libs
set CLSPATH=

FOR %%D IN (%DIRS_JARS%) DO (
	FOR %%F IN (%%D\*.jar) DO (
		REM echo "%%F"
		SET CLSPATH=!CLSPATH!;%%F
	)
)

REM claspath %CLSPATH%

REM Path to java installation
REM set PR_JVM=C:\Program Files\Java\jre7\bin\server\jvm.dll
set JAVA_HOME="C:\Program Files\Java\jdk1.7.0_79"
set PR_CLASSPATH=%CLSPATH%;%DIR_CONFIG%

REM Startup configuration
set PR_STARTUP=auto
set PR_STARTMODE=Java
set PR_STARTCLASS=com.sacooliveros.escale.etl.Server
REM set PR_STARTMETHOD=start
 
REM Shutdown configuration
set PR_STOPMODE=Java
set PR_STOPCLASS=com.sacooliveros.escale.etl.Server
REM set PR_STOPMETHOD=stop
 
REM JVM configuration
set PR_JVMMS=256
set PR_JVMMX=1024
set PR_JVMSS=4000
set PR_JVMOPTIONS=-Duser.language=DE;-Duser.region=de
REM Install servic 
prunsrv.exe //IS//%SERVICE_NAME%