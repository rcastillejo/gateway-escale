set SERVICE_NAME=ESCALEService
set PR_INSTALL=D:\rcastillejo\Documentos\Tesis-Eduardo\fuentes\gateway-escale\escale\escale-gateway-assembly\target\escale\prunsrv.exe
 
REM Service log configuration
set PR_LOGPREFIX=%SERVICE_NAME%
set PR_LOGPATH=D:\rcastillejo\Documentos\Tesis-Eduardo\fuentes\gateway-escale\escale\escale-gateway-assembly\target\escale\logs
set PR_STDOUTPUT=D:\rcastillejo\Documentos\Tesis-Eduardo\fuentes\gateway-escale\escale\escale-gateway-assembly\target\escale\logs\stdout.txt
set PR_STDERROR=D:\rcastillejo\Documentos\Tesis-Eduardo\fuentes\gateway-escale\escale\escale-gateway-assembly\target\escale\logs\stderr.txt
set PR_LOGLEVEL=Debug
 
 
set DIR=D:\rcastillejo\Documentos\Tesis-Eduardo\fuentes\gateway-escale\escale\escale-gateway-assembly\target\escale
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
set JAVA_HOME="C:\Program Files\Java\jdk1.7.0_45"
set PR_CLASSPATH=D:\rcastillejo\Documentos\Tesis-Eduardo\fuentes\gateway-escale\escale\escale-gateway-assembly\target\escale\libs\.;D:\rcastillejo\Documentos\Tesis-Eduardo\fuentes\gateway-escale\escale\escale-gateway-assembly\target\escale\config\.
 
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