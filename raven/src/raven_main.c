#include "raven.h"
#include "windows.h"

int main(int argc, char * argv[]) {
	while (TRUE) {
		raven_go();
		Sleep(SLEEP_TIME);
	}
	return 0;
}