#!/usr/bin/python
#Open a file and create nodes and edges for the IPs in that file

import sys
import MySQLdb
import datetime as dt
import string as string
import ConfigParser


if len(sys.argv) != 3:
	print "Usage: graph_add_host.py <host ip> <graphDB config file>"
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
pwbEnd = dt.datetime(2012, 11, 22)
pwbEndStr = pwbEnd.strftime('%Y-%m-%d %H:%M:%S')


# Get Node IDs (we'll need them to link to.  specifically unique ones
cursor.execute ("SELECT Label, id FROM nodes")
rows = cursor.fetchall ()
node_ids = {}
for row in rows:
   node_ids[row[0]] = int(row[1])

# Do a sanity check here to make sure the host isn't in the graph
# get id by metadata and class.  Look up ID in edges table by target
cursor.execute("SELECT id FROM nodes WHERE Metadata LIKE '" + host_ip + "%' AND Class = 'Attribute'")
dup_meta_id = cursor.fetchone()
if dup_meta_id:
   cursor.execute("SELECT Source FROM edges WHERE Target = %d" % dup_meta_id[0])
   dup_id = cursor.fetchone()
   # if source is a host, end with "already seen"
   if dup_id:
      cursor.execute("SELECT Label FROM nodes WHERE id = '%d'" % dup_id[0])
      dup_label = cursor.fetchone()
      if dup_label[0] == "Host":
         cursor.execute("UPDATE nodes SET End = '" + pwbEndStr + "' WHERE id = '%d'" \
           % dup_id[0])
         print("Already Seen.  End time extended.")
         sys.exit(0)

# Add the host_ip to the graph
cursor.execute("""
   INSERT INTO nodes (Class, Label, Start, End, Metadata)
   VALUES
      ('Condition', 'Host', %s, %s, %s),
      ('Attribute', 'IP', %s, %s, %s)
   """, \
   (nowStr, pwbEndStr, host_ip, \
    nowStr, pwbEndStr, host_ip \
   )
)
cursor.execute("SELECT id FROM nodes WHERE Metadata = %s AND Class = 'Condition'", host_ip)
host = cursor.fetchone()
cursor.execute("SELECT id FROM nodes WHERE Metadata = %s AND Class = 'Attribute'", host_ip)
IP = cursor.fetchone()
cursor.execute("""
   INSERT INTO edges (Source, Target, start, end)
   VALUES 
      (%s, %s, %s, %s) 
   """, \
      (host[0], IP[0], nowStr, pwbEndStr
      )
)

# Remove extra metadata from hosts now that edges are created
cursor.execute("UPDATE nodes SET Metadata = '' WHERE Label = 'Host'")

# Check if netblock already exists
# Split the IP so we can check for all size networks
s_ip = string.split(host_ip,".",4)


cursor.execute ("SELECT id FROM nodes WHERE Metadata LIKE '" \
   + s_ip[0] + "." + s_ip[1] + "." + s_ip[2] + ".%' AND Label = 'Subnet'")
nets = cursor.fetchall()
if len(nets) < 1:
   cursor.execute ("SELECT id FROM nodes WHERE Metadata LIKE '" \
     + s_ip[0] + "." + s_ip[1] + ".%' AND Label = 'Subnet'")
   nets2 = cursor.fetchall()
   if len(nets) < 1:
      cursor.execute ("SELECT id FROM nodes WHERE Metadata LIKE '" \
        + s_ip[0] + ".%' AND Label = 'Subnet'")
      nets3 = cursor.fetchall()
      if len(nets) < 1:
      #  Well, there weren't any subnets, lets make one
         cursor.execute("INSERT INTO nodes (Class, Label, Metadata, Start, End) \
          VALUES ('Attribute', 'Subnet', '" + s_ip[0] + "." + s_ip[1] + "." + s_ip[2] + ".0', '" + \
           nowStr + "', '" + pwbEndStr + "')"
         )
	 # Now get the new subnet
         cursor.execute ("SELECT id FROM nodes WHERE Metadata LIKE '" \
           + s_ip[0] + "." + s_ip[1] + "." + s_ip[2] + ".%' AND Label = 'Subnet'")
         nets = cursor.fetchall()
	 # And link it to the engagement
         cursor.execute("""
         INSERT INTO edges (Source, Target, start, end)
         VALUES
           (%s, %s, %s, %s)
         """, \
           (node_ids['Engagement'], host[0], nowStr, pwbEndStr
           )
         )


# link the host to the netblock(s)
for net in nets:
   cursor.execute("""
      INSERT INTO edges (Source, Target, Start, End)
      VALUES
         (%s, %s, %s, %s)
      """, \
         (net[0], host[0], nowStr, pwbEndStr)
   )

# Complete
print("Host %s added and linked to graph" % host_ip)

# Close the database
cursor.close()
conn.commit()
conn.close()
