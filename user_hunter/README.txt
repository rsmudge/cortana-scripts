
The user_hunter.cna cortana script continually polls the metasploit 
database for notes output by the user_hunter.rb module.

The included user_hunter.rb module needs to be loaded into the teamserver's 
metasploit installation under
./metasploit-framework/modules/post/windows/gather/ for proper use.

Right clicking a Meterpreter session exposes a contextual menu
to launch the module.

The user_hunter.rb module will query the domain for available computers, as well
as users of a particular group (default "domain admins") and then will issue a 
command to find the users logged into every machine found. The logged in users
are compared against the queried user group, and matches result in a 
user.hunter note being thrown into the database for that host.

End result- point -> click -> be told where domain admins are logged in :)


-- harmj0y
