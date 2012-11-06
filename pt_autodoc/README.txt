pt_autodoc.cna is a cortana script which watches for hosts and services to be added and deleted.  When they are added and deleted, it adds them to a mysql database which may be imported into graph software such as gephi.  Prior to importing any hosts, please run "initialize_graph_db".  This will wipe the current database and recreate all tables necessary to store the graph.

The cortana script is basically only calling python scripts which do the brunt of the work.  This is not elegant code, but it's been working for me.  Hopefully you can use it as well.
