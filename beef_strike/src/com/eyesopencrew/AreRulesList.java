package com.eyesopencrew;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * @author Beny Green - gacksecurity.blogspotpot.com
 * EyesOpenCrew -
 *
 * Object representation of AreRules rules .
 */
public class AreRulesList {
public static AreRules[] extractAreRules(String jsontxt){
      JSONObject jsonAreRuleslist = (JSONObject) JSONSerializer.toJSON( jsontxt );
      AreRules AreRuleslist[] = new AreRules[jsonAreRuleslist.size()];
    if (jsonAreRuleslist.isEmpty()){
    } else{
       for (int i = 0; i < jsonAreRuleslist.size(); i++){
             try {
      AreRules AreRules = new AreRules();
      JSONObject AreRulesid = jsonAreRuleslist.getJSONObject(""+i);
      AreRules.setId(AreRulesid.getString("id"));
      AreRules.setName(AreRulesid.getString("name"));
      AreRules.setName(AreRulesid.getString("author"));
      AreRuleslist[i] = AreRules;
                 }
    catch (JSONException e){System.out.println( "ERROR: " + e );}
        }
        }
          return AreRuleslist ;
      }

public static int AreRules_size(String cmd_AreRuleslink){
   String jsonTxt_eventAreRules = BeefRequester.BeefGetRequest(cmd_AreRuleslink);
   JSONObject jso = new JSONObject();
   jso = (JSONObject) JSONSerializer.toJSON(jsonTxt_eventAreRules);
   String cmd_AreRules_json = jso.get("AreRules").toString();
   JSONArray jsonarray = new JSONArray();
   jsonarray = (JSONArray) JSONSerializer.toJSON( cmd_AreRules_json );
   return jsonarray.size();
   }

public static AreRules[] extractAreRulesData(String cmd_AreRuleslink){

        String jsonTxt_eventAreRules = BeefRequester.BeefGetRequest(cmd_AreRuleslink);
        JSONObject jso = new JSONObject();
        jso = (JSONObject) JSONSerializer.toJSON(jsonTxt_eventAreRules);

	String cmd_AreRules_json = jso.get("AreRules").toString();
        JSONArray jsonarray = new JSONArray();
        jsonarray = (JSONArray) JSONSerializer.toJSON( cmd_AreRules_json );
            AreRules AreRuleseventlist[] = new AreRules[jsonarray.size()];
            if (jsonarray.isEmpty()){   } else{
        for (int i = 0; i < jsonarray.size(); i++){

         try {
             AreRules AreRulesevnt = new AreRules();
             AreRulesevnt.setId(jsonarray.getJSONObject(i).getString("id"));
             AreRulesevnt.setName(jsonarray.getJSONObject(i).getString("name"));
             AreRulesevnt.setName(jsonarray.getJSONObject(i).getString("author"));
             AreRuleseventlist[i] = AreRulesevnt;
                 }
        catch (JSONException e){System.out.println( "ERROR: " + e );}
        }
     }
     return AreRuleseventlist ;
   }

public static String extractAreRulesEventData(String jsontxt , int rang, String value) {
        AreRules AreRuleseventlist[] = AreRulesList.extractAreRulesData(jsontxt);
        String a = null;
        if(value.equals("id")) {a = AreRuleseventlist[rang].getId();}
        else if(value.equals("name")) {a = AreRuleseventlist[rang].getName();}
        else if(value.equals("author")) {a = AreRuleseventlist[rang].getAuthor();}
        return a ;
       }
}
