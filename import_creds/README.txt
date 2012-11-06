import_creds.cna is a simple cortana script to import credentials into the metasploit database through cortana.  It is configured to read the appropriate fields from cloudcracker.com results, however updating it to read other credential files should be as easy as changing the fields to be imported from @data.

To use import_creds, call it from the cortana console with two varials, the file to be imported and the host to assign the credentials to:
cortana>import_creds /home/rmudge/cloudcracker_results.txt 192.168.1.1
