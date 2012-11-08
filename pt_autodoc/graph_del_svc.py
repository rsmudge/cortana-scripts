#!/usr/bin/python

import sys
import MySQLdb
import datetime as dt
import string as string
import ConfigParser

if len(sys.argv) != 4:
	print "Usage: graph_del_svc.py <host ip> <port number> <graphDB config file>"
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
svc_id = []
for ip_edge in ip_edges:
   for port_edge in port_edges:
# Debug
#      print "Comparing port edge: "
#      print port_edge
#      print "and ip edge: "
#      print ip_edge
      if port_edge[0] == ip_edge[0]:
         svc_id.append(port_edge[2])

if len(svc_id) < 1:
   print("Service not found to remove.")
   sys.exit(0)

# Update node end time.
cursor.execute("UPDATE nodes SET End = '" + nowStr + "' WHERE id = '%d'" % svc_id[0])

print("Service " + host_port + " on host " + host_ip + " (node %d) end time updated to now" \
   % svc_id[0])

# Close the database
cursor.close()
conn.commit()
conn.close()

