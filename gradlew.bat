@echo off
:: Gradle startup script for Windows

set DIR=%~dp0
set APP_HOME=%DIR%

set CLASSPATH=%APP_HOME%gradle\wrapper\gradle-wrapper.jar

if not defined JAVA_HOME goto noJavaHome

set JAVA_EXE=%JAVA_HOME%\bin\java.exe
if exist "%JAVA_EXE%" goto execute

:noJavaHome
set JAVA_EXE=java.exe

:execute
"%JAVA_EXE%" -Xmx64m -Xms64m -cp "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
