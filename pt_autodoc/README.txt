pt_autodoc.cna is a cortana script which watches for hosts and services to be added and deleted.  When they are added and deleted, it adds them to a mysql database which may be imported into graph software such as gephi.  Prior to importing any hosts, please run "initialize_graph_db".  This will wipe the current database and recreate all tables necessary to store the graph.

The cortana script is basically only calling python scripts which do the brunt of the work.  This is not elegant code, but it's been working for me.  Hopefully you can use it as well.

When pt_autodoc.cna sees a host or service, it checks the graph database.  If the host/service doesn't exist, it creates the node as well as any parent nodes (subnets, etc).  It then creates the appropriate edges.  If the node already exists, it updates the end time of the node.

When pt_autodoc.cna sees a host/service removed, rather than remove it from the graph, it simply sets the end time of the associated node to the current time.

(Note, since the node only stores a single start/end time, if a service is added/removed multiple times, only it's initial 'seen' time and the latest end time are kept.  Also, end times are currently statically set to "11/22/2012".  This can easily be changed by updating the pwbEnd variable to pull values from the config file rather than the the current static set.)
