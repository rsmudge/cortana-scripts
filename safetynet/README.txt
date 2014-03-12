This Cortana script allows users to automatically inject 'safetynet'
payloads into new or existing meterpreter sessions.

The included smart_payload_inject.rb metasploit modules needs to be 
loaded into the teamserver's metasploit installation under
./metasploit-framework/modules/post/windows/manage/ for injection
to work properly.

The main menu is exposed to "Cobalt Strike" -> "Safetynet", as well as a 
contextual menu of "Meterprerer X -> Safetynet" on hosts w/ 
meterpreter sessions. Right clicking on individual safetynets
in the main display tab yields a contextual menu as well.

Manual commands are also exposed in the Cortana console and
descriptions are output on script load.

Features:
    -Safetynets can be specified from existing handlers on the 
       teamserver, or custom handlers can be specified
    -Safetynets are set to auto-inject on new sessions
        -this can be turned off, but is enabled by default
    -Safetynets can optionally be injected into explorer.exe as well
    -Safetynet configurations are stored in the database and persist across server restarts


-- harmj0y
