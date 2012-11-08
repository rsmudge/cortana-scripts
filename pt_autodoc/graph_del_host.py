#!/usr/bin/python

import sys
import MySQLdb
import datetime as dt
import string as string
import ConfigParser

if len(sys.argv) != 3:
	print "Usage: graph_del_host.py <host ip> <graphDB config file>"
	sys.exit(0)

# open the config file
config = ConfigParser.RawConfigParser()
config.read(sys.argv[2])

# get the host IP to be added from args
host_ip = sys.argv[1]

# Try and open the database
try:
   conn = MySQLdb.connect (host = config.get("mysqld","host"),
			   user = config.get("mysqld","user"),
			   passwd = config.get("mysqld","passwd"),
			   db = config.get("mysqld","db"))
except MySQLdb.Error, e:
   print "Error %d: %s" % (e.args[0], e.args[1])
   sys.exit (1)
cursor = conn.cursor()

# Get the Time
now = dt.datetime.now()
nowStr = now.strftime('%Y-%m-%d %H:%M:%S')


# Look for the host
cursor.execute("SELECT id FROM nodes WHERE Metadata LIKE '" + host_ip + "%' AND Class = 'Attribute'")
meta_id = cursor.fetchone()
if meta_id:
   cursor.execute("SELECT Source FROM edges WHERE Target = %d" % meta_id[0])
   host_id = cursor.fetchone()
   # if source is a host, keep going
   if host_id:
      cursor.execute("SELECT Label FROM nodes WHERE id = '%d'" % host_id[0])
      host_label = cursor.fetchone()
      if host_label[0] != "Host":
         print("Host Not Found")
         sys.exit(0)
   else:
      sys.exit(0)
else:
   sys.exit(0)

# Update node end time.
cursor.execute("UPDATE nodes SET End = '" + nowStr + "' WHERE id = '%d'" % host_id[0])

print("Host " + host_ip + " (node %d) end time updated to now" % host_id[0])

# Close the database
cursor.close()
conn.commit()
conn.close()

