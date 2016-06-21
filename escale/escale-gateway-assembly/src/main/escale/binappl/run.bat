setLocal EnableDelayedExpansion

REM autor: rcastillejo
REM set JAVA_HOME="C:\Program Files\Java\jdk1.7.0_45"
set JAVA_BIN="%JAVA_HOME%"\bin


REM Aplicacion
set APP_NAME=escale

REM Indicamos el nombre del main-class
set MAIN-CLASS=com.sacooliveros.escale.etl.Server

set DIR="%cd%"

REM Carpeta de configuracion
set DIR_CONFIG=%DIR%\config

REM Directorios que contiene el Classpath
set DIRS_JARS=%DIR%\libs

REM Directorio que contiene log
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
set JAVA_OPTS=-DLOGAPP=%LOG%
set JVM_ARGS=-Xms16m -Xmx16m

REM echo %CLSPATH%

REM Invocamos a la JVM para ejecutar la aplicacion
REM echo %JAVA_HOME%\bin\java -classpath %DIR_CONFIG%;%CLSPATH% %JVM_ARGS% %JAVA_OPTS% %MAIN-CLASS%
%JAVA_BIN%\java -classpath %DIR_CONFIG%;%CLSPATH% %JVM_ARGS% %JAVA_OPTS% %MAIN-CLASS% > %APP_NAME%.log
