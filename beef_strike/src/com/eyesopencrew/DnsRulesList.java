package com.eyesopencrew;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * @author Beny Green - gacksecurity.bDnspot.com
 * EyesOpenCrew -
 *
 * Object representation of DNS rules .
 */
public class DnsRulesList {
public static Dns[] extractDns(String jsontxt){
      JSONObject jsonDnslist = (JSONObject) JSONSerializer.toJSON( jsontxt );
      Dns Dnslist[] = new Dns[jsonDnslist.size()];
    if (jsonDnslist.isEmpty()){
    } else{
       for (int i = 0; i < jsonDnslist.size(); i++){
             try {
      Dns Dns = new Dns();
      JSONObject Dnsid = jsonDnslist.getJSONObject(""+i);
      Dns.setCount(Dnsid.getString("count"));
      Dns.setRuleset(Dnsid.getString("ruleset"));
      Dnslist[i] = Dns;
                 }
    catch (JSONException e){System.out.println( "ERROR: " + e );}
        }
        }
          return Dnslist ;
      }

public static int Dns_size(String cmd_Dnslink){
   String jsonTxt_eventDns = BeefRequester.BeefGetRequest(cmd_Dnslink);
   JSONObject jso = new JSONObject();
   jso = (JSONObject) JSONSerializer.toJSON(jsonTxt_eventDns);
    String cmd_Dns_json = jso.get("Dns").toString();
    JSONArray jsonarray = new JSONArray();
    jsonarray = (JSONArray) JSONSerializer.toJSON( cmd_Dns_json );
     return jsonarray.size();
   }

public static DnsRules[] extractDnsData(String cmd_Dnslink){

        String jsonTxt_eventDns = BeefRequester.BeefGetRequest(cmd_Dnslink);
        JSONObject jso = new JSONObject();
        jso = (JSONObject) JSONSerializer.toJSON(jsonTxt_eventDns);

	String cmd_Dns_json = jso.get("Dns").toString();
        JSONArray jsonarray = new JSONArray();
        jsonarray = (JSONArray) JSONSerializer.toJSON( cmd_Dns_json );
            DnsRules Dnseventlist[] = new DnsRules[jsonarray.size()];
            if (jsonarray.isEmpty()){   } else{
        for (int i = 0; i < jsonarray.size(); i++){

         try {
             DnsRules Dnsevnt = new DnsRules();
             Dnsevnt.setId(jsonarray.getJSONObject(i).getString("id"));
             Dnsevnt.setPattern(jsonarray.getJSONObject(i).getString("pattern"));
             Dnsevnt.setResponse(jsonarray.getJSONObject(i).getString("response"));
             Dnsevnt.setType(jsonarray.getJSONObject(i).getString("type"));
             Dnseventlist[i] = Dnsevnt;
                 }
        catch (JSONException e){System.out.println( "ERROR: " + e );}
        }
     }
     return Dnseventlist ;
   }

public static String extractDnsEventData(String jsontxt , int rang, String value) {
        DnsRules Dnseventlist[] = DnsRulesList.extractDnsData(jsontxt);
        String a = null;
        if(value.equals("id")) {a = Dnseventlist[rang].getId();}
        else if(value.equals("pattern")) {a = Dnseventlist[rang].getPattern();}
        else if(value.equals("response")) {a = Dnseventlist[rang].getResponse();}
        else if(value.equals("type")) {a = Dnseventlist[rang].getType();}
        return a ;
       }
}
