+--------------------------------------------+
+         BeEF and Armitage integration      +
+--------------------------------------------+

The purpose of this script is to automate the sending of some  commands of BeEF_xss 
and allow to control the zombies directly through the intuitive armitage's interface.
The ultimate goal would be to work on armitage as if you was in  the web panel of beef
Beef_Strike can help you write script for automatic and targeted browser's exploitation.


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
   
2. Download and install all libraries listed on the read_this file located on the lib folder
   And modify the  "import" lines on beef_strike script to point to the right path.

3. The ettercap command must be able to launch ettercap installation. [That is the case in Backtrack & Kali].

4. Launch Beef xss server and Armitage

5. Load beef_strike.cna cortana script. A sub new menu will appear on the attack menu

6. Connect to your Beef server instance and put your RESTful API key.

6. Begin the zombies recruitment. You can use xss exploitation or ettercap method. In this folder you will find the infect.filter file. 
   This is the default file for html injection. You must change it and point to your beef server. 
   The default value is the localhost address and port 3000 the default port of beef server
   Have fun with ettercap filter: http://www.irongeek.com/i.php?page=security/ettercapfilter

7. Choose the interface to poison and let beef_strike do the job

8. Once a zombie appear, many beef's commands (and you can add) are automatically launch again victim. 
   You have profiling the victim browser (you can see result on zombies menu item). 
   Now feel free to use client-side exploits and send to your zombies through beef invisible iframe module. 

        See ? easy !
----------------------------------------------------------------------------------------------------------------+
Tested on Backtrack5r3
video demo: http://www.youtube.com/watch?v=YhKhkYzq2s8&feature=share&list=UU7_xeQ_4d8jAMtxdJgikjlA
- 

- LIMITATION -
  The result of some command can only be see on Beef web ui 



- TO CAME -
* mitmproxy html injection method
* DroNe script, a script for automatic and targeted browsser's exploitation based on their very specific profiles.



Work in progress...