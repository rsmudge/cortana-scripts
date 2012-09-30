#include "raven.h"
#include "windows.h"

DWORD ThreadProc(LPVOID whatever) {
	while (TRUE) {
		raven_go();
		Sleep(SLEEP_TIME);
	}
}

BOOL WINAPI DllMain (HANDLE hDll, DWORD dwReason, LPVOID lpReserved) {
	switch (dwReason) {
		case DLL_PROCESS_ATTACH:
			CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)&ThreadProc, (LPVOID) NULL, 0, NULL);
		break;
	}
	return TRUE;
}

STDAPI DllRegisterServer(void) {
}

STDAPI DllUnregisterServer(void) {
}

STDAPI DllGetClassObject( REFCLSID rclsid, REFIID riid, LPVOID *ppv ) {
	return CLASS_E_CLASSNOTAVAILABLE;
}

STDAPI DllRegisterServerEx( LPCTSTR lpszModuleName ) {
}
