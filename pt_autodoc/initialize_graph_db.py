#!/usr/bin/python

# Add initial information to graph 

import sys
import MySQLdb
import datetime as dt
import string as string
import ConfigParser

if len(sys.argv) != 1:
        print "Usage: intiialize_graph_db.py <graphDB config file>"
        sys.exit(0)

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


now = dt.datetime.now()
nowStr = now.strftime('%Y-%m-%d %H:%M:%S')
pwbEnd = dt.datetime(2012, 11, 22)
pwbEndStr = pwbEnd.strftime('%Y-%m-%d %H:%M:%S')

# Start the database from scratch
cursor.execute ("DROP TABLE IF EXISTS nodes")
cursor.execute ("DROP TABLE IF EXISTS edges")
cursor.execute ("""
	CREATE TABLE nodes
	(
	  id		INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	  Label		varchar(45),
	  Class		varchar(45),
	  Description	varchar(255),
	  Metadata	varchar(255),
	  Start		DATETIME,
	  End		DATETIME,
	  X		INT,
	  Y		INT,
	  Size		INT
	)
""")
cursor.execute ("""
	CREATE TABLE edges
	(
	  Source	INT NOT NULL,
	  Target	INT NOT NULL,
	  Id		INT NOT NULL AUTO_INCREMENT UNIQUE,
	  Type		varchar(45),
	  Label		varchar(45),
	  Weight	INT,
	  Start		DATETIME,
	  End		DATETIME,
	  PRIMARY KEY (Source, Target)
	)
""")

# Insert Top-Level Nodes into Database
cursor.execute ("""
        INSERT INTO nodes (Class, Label, start, end, Metadata)
        VALUES
           ('Actor', 'me', %s, %s, ''),
           ('Condition', 'Engagement', %s, %s, ''),
           ('Attribute', 'Domain', %s, %s, 'THINC.local'),
           ('Attribute', 'subnet', %s, %s, '192.168.17.0/24')
        """, \
           (nowStr, pwbEndStr, \
            nowStr, pwbEndStr, \
            nowStr, pwbEndStr, \
            nowStr, pwbEndStr \
           )
        )


# Get Node IDs (we'll need them to link to.  specifically unique ones
cursor.execute ("SELECT Label, id FROM nodes")
rows = cursor.fetchall ()
node_ids = {}
for row in rows:
   node_ids[row[0]] = int(row[1])


# Create Edges
cursor.execute ("""
        INSERT INTO edges (Source, Target, start, end)
        VALUES
           (%s, %s, %s, %s), # link me to engagement
           (%s, %s, %s, %s), # link engagement to Domain
           (%s, %s, %s, %s) # link me to subnet
        """, \
           (node_ids['me'], node_ids['Engagement'], nowStr, pwbEndStr, \
            node_ids['Engagement'], node_ids['Domain'], nowStr, pwbEndStr, \
            node_ids['Engagement'], node_ids['subnet'], nowStr, pwbEndStr \
           )
        )

print("Database wiped and initialized.")

# Close the database
cursor.close()
conn.commit()
conn.close()
