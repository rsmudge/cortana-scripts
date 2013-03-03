#
# Compile Raven using MinGW
#

# compile raven.exe
i586-mingw32msvc-gcc src/raven.c src/inject.c src/raven_main.c -o raven.exe -mwindows -lws2_32 -lwininet

# compile raven_svc.exe
i586-mingw32msvc-gcc src/raven.c src/inject.c src/raven_svc.c -o raven_svc.exe -mwindows -lws2_32 -lwininet

# compile raven.dll
i586-mingw32msvc-gcc -c src/raven.c src/inject.c src/raven_dll.c -shared
i586-mingw32msvc-dllwrap --def src/raven.def raven.o inject.o raven_dll.o -o raven.dll -lws2_32 -lwininet

# cleanup
rm -f *.o
