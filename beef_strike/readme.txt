+--------------------------------------------+
+         BeEF and Armitage integration      +
+--------------------------------------------+

The purpose of this script is to automate the sending of some  commands of BeEF_xss 
and allow to control the zombies directly through the intuitive armitage's interface.
The ultimate goal would be to work on armitage as if you was in  the web panel of beef

- WHAT IT DO -
* auto load metasploit's beef plugin
* connect armitage to the beef's server
* perform MiTM with the help of ettercap
* inject beef hooks all over the LAN (the LAN's users browse website and are automatically hooked)
* Auto import all the new zombies inside metasploit database
* auto perform MiTB attack to ensure persistence on the victim's browser.
* Autorun beef's commands again victim browser (reconnaissance scripts) 


- HOW TO USE -

For use this script you need to 
1. Download and install 
   BeEF Metasploit Plugin from Christian Frichot github repository.
   https://github.com/xntrik/beefmetasploitplugin

2. The ettercap command must be able to launch ettercap installation. [That is the case in Backtrack & Kali].

3. Launch Beef xss server and Armitage

4. Load beef_strike.cna cortana script. A sub new menu will appear on the attack menu

5. Connect to your Beef server instance and put your RESTful API key.

6. Begin the zombies recruitment with ettercap method. In this folder you will find the infect.filter file. 
   This is the default file for html injection. You must change it and point to your beef server. 
   The default value is the localhost address and port 3000 the default port of beef server
   Have fun with ettercap filter: http://www.irongeek.com/i.php?page=security/ettercapfilter

7. Choose the interface to poison and let beef_strike do the job

8. Once a zombie appear, many beef's commands (and you can add) are automatically launch again victim. 
   You have profiling the victim browser (you can see result on zombies menu item). 
   Now feel free to use client-side exploits and send to your zombies through beef invisible iframe module. 

9. To use drive-by option and send invisible iframe throught armitage you need to provide the beef's session ID of 
   the hooked browser. 
	Easy tips : --> Attack menu -> Beef_Strike -> Zombies -> right click on a zombie
		       -> Beef action -> information -> switch on Beef Strike Tab -> copy the value of BEEFHOOK cookie (session ID)
                       -> Switch to zombies tab -> right click the same zombie -> drive-by -> paste session ID -> put the URL of your iframe
		       -> Now you can refresh zombie tab, session ID have been recorded.
        See ? easy !
----------------------------------------------------------------------------------------------------------------+
Tested on Backtrack5r3
video demo: http://www.youtube.com/watch?v=YhKhkYzq2s8&feature=share&list=UU7_xeQ_4d8jAMtxdJgikjlA
- 

- LIMITATION -
* At this time, This script doesn't use the beef_online command of beef metasploit plugin
  to update zombies list. At result, but it try to reproduce it. 
  So if you try to add host while this script is running, you will falsify final result. 
  Do your recruitment and Let Beef_strike add host for you. 



- TO CAME -
* better beef_strike and beef communication
* mitmproxy html injection method

- credits -
Browser's icon : http://speckyboy.com/2010/08/09/30-fresh-and-free-icon-sets-for-designers-and-developers/

Work in progress...