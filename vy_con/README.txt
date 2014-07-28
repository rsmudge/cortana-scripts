This Cortana script allows users to control and manage Vyatta routers. It is designed to 
particularly aid is mass command and control of Vyatta when you have root access.. 


The Vyatta Tab is exposed via a menu option Cobalt Strike -> Vyatta Tab. Upon recognition 
of a Vyatta Device, you must mark the host as Vyatta. The host will then appear in the 
Tab. You can mass select hosts in the tab and issue commands via the button. Currently
your shell must be as the root user or in the normal bash prompt... the vbash shell 
will not be useable for the control with the tab. 

Limited manual commands will be exposed via the Cortana Console.

Description: Cobalt Strike interface for Vyatta Routers you have gained root access to.
1. Get root ssh shell to the vyatta
2. Right click -> Mark As Vyatta

Next Features:
1. Upload and run vyatta script
2. Enumerate Router Function
3. Proper checking on text boxes to make sure they are not null before submitting comman ds

WARNINGS: When you use this tool, the local vyatta user will lose control over configuration. 
When you make config changes as root, it affects permissions on the config files. THIS IS FOR EXERCISE REDTEAMS!   
Also, this will not work as a non-priv vyatta user in its' current state. I can't promise it will behave if you try. 

Happy Hacking!
------ Dubhack ------
