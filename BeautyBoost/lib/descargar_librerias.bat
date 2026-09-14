@echo off
setlocal
cd /d "%~dp0"
echo Beauty Boost - descarga de librerias
set "BASE=https://repo.maven.apache.org/maven2"

call :download "mysql-connector-j-8.4.0.jar" "%BASE%/com/mysql/mysql-connector-j/8.4.0/mysql-connector-j-8.4.0.jar"
call :download "jakarta.servlet.jsp.jstl-3.0.1.jar" "%BASE%/org/glassfish/web/jakarta.servlet.jsp.jstl/3.0.1/jakarta.servlet.jsp.jstl-3.0.1.jar"
call :download "jakarta.mail-api-2.1.3.jar" "%BASE%/jakarta/mail/jakarta.mail-api/2.1.3/jakarta.mail-api-2.1.3.jar"
call :download "angus-mail-2.0.4.jar" "%BASE%/org/eclipse/angus/angus-mail/2.0.4/angus-mail-2.0.4.jar"
call :download "jakarta.activation-api-2.1.3.jar" "%BASE%/jakarta/activation/jakarta.activation-api/2.1.3/jakarta.activation-api-2.1.3.jar"
call :download "angus-activation-2.0.2.jar" "%BASE%/org/eclipse/angus/angus-activation/2.0.2/angus-activation-2.0.2.jar"
call :download "jbcrypt-0.4.jar" "%BASE%/org/mindrot/jbcrypt/0.4/jbcrypt-0.4.jar"

echo.
echo Listo. Abre BeautyBoost en NetBeans y ejecuta Clean and Build.
pause
exit /b 0

:download
if exist "%~1" (
  echo OK: %~1
  exit /b 0
)
echo Descargando %~1 ...
where curl >nul 2>&1
if %errorlevel%==0 (
  curl -fL -o "%~1" "%~2"
  exit /b %errorlevel%
)
where powershell >nul 2>&1
if %errorlevel%==0 (
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Invoke-WebRequest -Uri '%~2' -OutFile '%~1'"
  exit /b %errorlevel%
)
echo ERROR: no se encontro curl ni PowerShell.
exit /b 1
