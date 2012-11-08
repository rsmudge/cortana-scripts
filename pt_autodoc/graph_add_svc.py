#!/usr/bin/python

import sys
import MySQLdb
import datetime as dt
import string as string
import ConfigParser


if len(sys.argv) != 4:
	print "Usage: graph_add_svc.py <host ip> <port number> <graphDB config file>"
	sys.exit(0)

# open the config file
config = ConfigParser.RawConfigParser()
config.read(sys.argv[3])

# get the host IP to be added from args
host_ip = sys.argv[1]
host_port = sys.argv[2]

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

# Check if already exists
# Select id(service) of Attribute Node with Label Port and Metadata host_port
cursor.execute("SELECT id FROM nodes WHERE Class = 'Attribute' AND Label = 'Port' \
  AND Metadata = '" + host_port + "'")
port_node_ids = cursor.fetchall()
# select id(ip) of attribute node with label IP and metadata host_ip
cursor.execute("SELECT id FROM nodes WHERE Class = 'Attribute' AND Label = 'IP' \
  AND Metadata LIKE '" + host_ip + "%'")
ip_node_ids = cursor.fetchall()
# select source of edge with target = id(ip) (may be only 1)
port_edges = []
for port_node_id in port_node_ids:
   cursor.execute("SELECT Source, Target, id FROM edges WHERE Target = %d" % port_node_id[0])
   tmp_port_sources = cursor.fetchall()
   port_edges = port_edges + list(tmp_port_sources)
   del tmp_port_sources
# Select source of edge with target = id(service) (probably a list of edges)
ip_edges = []
for ip_node_id in ip_node_ids:
   cursor.execute("SELECT Source, Target, id FROM edges WHERE Target = %d" % ip_node_id[0])
   tmp_ip_sources = cursor.fetchall()
   ip_edges = ip_edges + list(tmp_ip_sources)
   del tmp_ip_sources
# Filter for same source in both lists
#svc_id = id where source is in both ip_edges and node_edgess
svc_edge_id = []
for ip_edge in ip_edges:
   for port_edge in port_edges:
# Debug
#      print "Comparing port edge: "
#      print port_edge
#      print "and ip edge: "
#      print ip_edge
      if port_edge[0] == ip_edge[0]:
         svc_edge_id.append(port_edge[2])

# if service exists, update the edge time and exit
if len(svc_edge_id) > 0:
   for svc_id_ctr in svc_edge_id:
      cursor.execute("UPDATE edges SET End = '" + pwbEndStr + "' WHERE id = '%d'" \
        % svc_id_ctr)
   print("Service already exists.  Edge time updated.")
   sys.exit(0)

# If port doesn't exist, Add port
if not port_node_ids:
#   print("Port node doesn't exist.") # Debug
   cursor.execute("""
      INSERT INTO nodes (Class, Label, Start, End, Metadata)
      VALUES
        ('Attribute', 'Port', %s, %s, %s) 
      """, \
        (nowStr, pwbEndStr, host_port)
   )

#  Get the ID of the Port node we just created
cursor.execute("SELECT id FROM nodes WHERE Metadata = '" + host_port + "' AND Class = 'Attribute' \
  AND Label = 'Port'")
svc_edge_id = cursor.fetchone()

# Get the host
cursor.execute("SELECT id FROM nodes WHERE Metadata LIKE '" + host_ip + "%' AND Class = 'Attribute'")
host_meta_id = cursor.fetchone()
if not host_meta_id:
   sys.exit("This shouldn't happen. We already checked for the IP attribute")
cursor.execute("SELECT Source FROM edges WHERE Target = %d" % host_meta_id[0])
host_id = cursor.fetchone()
if not host_id:
   sys.exit("Again, this shouldn't happen.  We already checked for a host based on the IP attribute node")

# Link Port to Host
cursor.execute("""
   INSERT INTO edges (Source, Target, Start, End) 
   VALUES
      (%s, %s, %s, %s)
   """, \
      (host_id[0], svc_edge_id[0], nowStr, pwbEndStr)
)

#print("Service " + host_port + " on host " + host_ip + " (node %d) added." \
#   % svc_node_id[0])
print("Service " + host_port + " (node %d) connected to host " + host_ip + "." \
   % svc_edge_id[0])

# Close the database
cursor.close()
conn.commit()
conn.close()

