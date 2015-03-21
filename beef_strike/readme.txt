+--------------------------------------------+
+         BeEF and Armitage integration      +
+               by @thebenygreen             +
+--------------------------------------------+

The purpose of this script is to automate the sending of some  commands of BeEF_xss 
and allow to control the zombies directly through the intuitive armitage's interface.
The ultimate goal would be to work on armitage as if you was in  the web panel of beef
Beef_Strike can help you for automatic and targeted browser's exploitation.


- WHAT IT DO -
* auto load metasploit's beef plugin (not a requierment)
* connect armitage to the beef's server 
* perform MiTM with the help of ettercap and inject beef hooks all over the LAN 
* give access to the web cloning and mass mailer of BeEF via Armitage
* Auto import all the new zombies inside metasploit database
* auto perform MiTB attack to ensure persistence on the victim's browser.
* Autorun beef's commands again victim browser based on the browser type
* geolocate every zombies on a google map
* have a feature for automatic and targeted client-side exploitation with the ability for the 
  pentester to specify which exploit to use against which specify profile 
* generate PDF report of BeEF zombies


- HOW TO USE -

For use this script you need to 
1. Download and install (That step is really not necessary )
   BeEF Metasploit Plugin from Christian Frichot github repository.
   https://github.com/xntrik/beefmetasploitplugin
   
2. Download and install all libraries listed on the read_this file located on the lib folder : http://ow.ly/JMx09  
   And modify the  "import" lines on beef_strike script to point to the right path.

3. To use MiTM attack, Ettercap must be available on system path. [That is the case in Backtrack & Kali].

4. Launch Beef xss server and Armitage

5. Load beef_strike.cna cortana script. A sub new menu will appear on the attack menu

6. Connect to your Beef server instance.

7. Begin the zombies recruitment. You can use xss exploitation or ettercap method. In this folder you will find the infect.filter file. 
   This is the default file for html injection. You must change it and point to your beef server. 
   The default value is the localhost address and port 3000 the default port of beef server
   Have fun with ettercap filter: http://www.irongeek.com/i.php?page=security/ettercapfilter
      You can also use the web cloner or mass mailer for recruitment purpose.

8. if you have choose MiTM recruitment method, choose the interface to poison and let beef_strike do the job. You can also use hooker to play with beef hook inside cobalt strike workflow.

8. Once a zombie appear, many beef's commands (and you can add) are automatically launch again victim. 
   You have profiling the victim browser (you can see result on zombies menu item). 
   Now feel free to use client-side exploits and send to your zombies through beef invisible iframe module. 

        See ? easy !
----------------------------------------------------------------------------------------------------------------+
Tested on Backtrack and Kali
video demo: http://www.youtube.com/watch?v=YhKhkYzq2s8&feature=share&list=UU7_xeQ_4d8jAMtxdJgikjlA
- 			http://youtube.com/mrbenygreen	

- LIMITATION -
  The result of some command can only be see on Beef web ui. 

