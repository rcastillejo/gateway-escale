setLocal EnableDelayedExpansion

set JAVA_HOME="C:\Program Files\Java\jdk1.7.0_79"


set APP_NAME=ESCALE
set SERVICE_NAME=%APP_NAME%Service
set DISPLAY_NAME="%APP_NAME% Service"
set DESCRIPTION="Servicio ETL para la sincronizacion de instituciones de ESCALE"

set DIR=D:\UPC\Eduardo\fuentes\gateway-escale\escale\escale-gateway-assembly\target\escale


set DIR_CONFIG=%DIR%\config
set DIRS_JARS=%DIR%\libs
set LOG=%DIR%\log
REM Iniciamos las variables para concatenar el Classpath
set CLSPATH=

REM echo %DIR_CONFIG%
REM echo %DIR_JARS%
FOR %%D IN (%DIRS_JARS%) DO (
                FOR %%F IN (%%D\*.jar) DO (
                               REM echo "%%F"
                              SET CLSPATH=!CLSPATH!;%%F
                )
)

REM echo %CLSPATH%

REM instalando servicio %APP_NAME%

prunsrv //IS//%SERVICE_NAME% --DisplayName=%DISPLAY_NAME% --Description=%DESCRIPTION% --Install=prunsrv.exe --Jvm=auto --Classpath=%DIR_CONFIG%;%CLSPATH% --StartMode=Java --StopMode=Java --StartClass=com.sacooliveros.escale.etl.Server --StopClass=com.sacooliveros.escale.etl.Server --Startup=auto --JavaHome=%JAVA_HOME% --JvmMs=16m --JvmMx=16m --JvmOptions=-DLOGAPP=%LOG% --LogLevel=Debug --StdOutput=%APP_NAME%-out.log --StdError=%APP_NAME%-stderr.log
