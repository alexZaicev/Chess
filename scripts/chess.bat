@echo off

rem Setup the JVM
if "x%JAVA%" == "x" (
  if "x%JAVA_HOME%" == "x" (
    set JAVA=java
  ) else (
    set "JAVA=%JAVA_HOME%\bin\java"
  )
)

rem Specify the class path and the main class name
set CLASSPATH=.;./lib/*
set MAINCLASS=com.alexz.chess.Chess

rem Execute the JVM
"%JAVA%" -cp "%CLASSPATH%" %MAINCLASS% %*
