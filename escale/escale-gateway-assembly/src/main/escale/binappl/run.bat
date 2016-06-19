setLocal EnableDelayedExpansion

REM autor: rcastillejo
set JAVA_HOME="C:\Program Files\Java\jdk1.7.0_79"
set JAVA_BIN=%JAVA_HOME%\bin


REM Aplicacion
set APP_NAME=evaluador

REM Indicamos el nombre del main-class
set MAIN-CLASS=com.sacooliveros.escale.etl.Server

REM Carpeta de configuracion
set DIR_CONFIG=config\%APP_NAME%

REM Directorios que contiene el Classpath
set DIRS_JARS=libs\%APP_NAME%

REM Directorio que contiene log
set LOG=log\%APP_NAME%

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
set JAVA_OPTS=-Ds2j.id=1 -Ds2j.home=%DIR_CONFIG%  -DLOGAPP=%LOG%
set JVM_ARGS=-Xms128m -Xmx128m

REM echo %CLSPATH%

REM Invocamos a la JVM para ejecutar la aplicacion
REM echo %JAVA_HOME%\bin\java -classpath %DIR_CONFIG%;%CLSPATH% %JVM_ARGS% %JAVA_OPTS% %MAIN-CLASS%
%JAVA_BIN%\java -classpath %DIR_CONFIG%;%CLSPATH% %JVM_ARGS% %JAVA_OPTS% %MAIN-CLASS% > %APP_NAME%.log