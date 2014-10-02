package com.eyesopencrew;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * @author Beny Green - gacksecurity.blogspot.com
 * EyesOpenCrew -
 *
 * Object representation of Log list.
 */
public class LogsList {
    
public static Logs[] extractLogs(String jsontxt){
      JSONObject jsonLoglist = (JSONObject) JSONSerializer.toJSON( jsontxt );
      Logs Logslist[] = new Logs[jsonLoglist.size()];
    if (jsonLoglist.isEmpty()){
    } else{
       for (int i = 0; i < jsonLoglist.size(); i++){
             try {
      Logs log = new Logs();
      JSONObject logid = jsonLoglist.getJSONObject(""+i);
      log.setLogscount(logid.getString("logs_count"));
      log.setLogs(logid.getString("logs"));
      Logslist[i] = log;
                 }
    catch (JSONException e){System.out.println( "ERROR: " + e );}
        }
        }
          return Logslist ;
      }

public static int Logs_size(String cmd_loglink){
   String jsonTxt_eventlog = BeefRequester.BeefGetRequest(cmd_loglink);
   JSONObject jso = new JSONObject();
   jso = (JSONObject) JSONSerializer.toJSON(jsonTxt_eventlog);
    String cmd_logs_json = jso.get("logs").toString();
    JSONArray jsonarray = new JSONArray();
    jsonarray = (JSONArray) JSONSerializer.toJSON( cmd_logs_json );
     return jsonarray.size();
   }
   
public static LogEvents[] extractLogsData(String cmd_loglink){

        String jsonTxt_eventlog = BeefRequester.BeefGetRequest(cmd_loglink);
        JSONObject jso = new JSONObject();
        jso = (JSONObject) JSONSerializer.toJSON(jsonTxt_eventlog);

	String cmd_logs_json = jso.get("logs").toString();
        JSONArray jsonarray = new JSONArray();
        jsonarray = (JSONArray) JSONSerializer.toJSON( cmd_logs_json );
            LogEvents Logeventlist[] = new LogEvents[jsonarray.size()];
            if (jsonarray.isEmpty()){   } else{
        for (int i = 0; i < jsonarray.size(); i++){
        //System.out.println(jsonarray.getJSONObject(i).getString("name"));
         try {
             LogEvents logevnt = new LogEvents();
             logevnt.setDate(jsonarray.getJSONObject(i).getString("date"));
             logevnt.setEvent(jsonarray.getJSONObject(i).getString("event"));
             logevnt.setType(jsonarray.getJSONObject(i).getString("type"));
             Logeventlist[i] = logevnt;
                 }
        catch (JSONException e){System.out.println( "ERROR: " + e );}
        }
     } 
     return Logeventlist ;
   }
    
    public static String extractlogEventData(String jsontxt , int rang, String value) {
    
       LogEvents Logeventlist[] = LogsList.extractLogsData(jsontxt); 
       String a = null;
        if(value.equals("date")) {a = Logeventlist[rang].getDate();}
        else if(value.equals("event")) {a = Logeventlist[rang].getEvent();}
        else if(value.equals("type")) {a = Logeventlist[rang].getType();}
        return a ;
       }
}
