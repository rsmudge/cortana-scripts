@echo off
set PATH=%PATH%;c:\mingw\bin

REM
REM Build raven.exe
REM
gcc -L c:\mingw\lib src/raven.c src/raven_main.c -o raven.exe -l wininet -mwindows
strip raven.exe -o raven_clean.exe

REM
REM Build raven_svc.exe
REM 
gcc -L c:\mingw\lib src/raven.c src/raven_svc.c -o raven_svc.exe -l wininet -mwindows
strip raven_svc.exe -o raven_svc_clean.exe

REM
REM Build raven.dll
REM
gcc -c -L c:\mingw\lib src/raven.c src/raven_dll.c -l wininet
dllwrap -L c:\mingw\lib --def raven.def raven.o raven_dll.o -o raven.dll -l wininet
strip raven.dll -o raven_clean.dll

REM 
REM Clean raven
REM 
del raven.o
del raven_dll.o