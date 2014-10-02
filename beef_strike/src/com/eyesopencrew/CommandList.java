package com.eyesopencrew;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * @author Beny Green - gacksecurity.blogspot.com
 *
 * EyesOpenCrew
 */
public class CommandList {
//List of Commands modules of BeEF
    /**
     *
     * @param jsontxt
     * @return cmd[]
     *   - Extract commands from Json representation.
     * 
     */
public static Commands[] extractCommands(String jsontxt){
      
      JSONObject jsonCommandlist = (JSONObject) JSONSerializer.toJSON( jsontxt );
      //JSONObject Offline = json.getJSONObject("hooked-browsers").getJSONObject("offline");
        Commands Commandslist[] = new Commands[jsonCommandlist.size()];
    if (jsonCommandlist.isEmpty()){
   
} else{
       for (int i = 0; i < jsonCommandlist.size(); i++){

             try {
      Commands cmd = new Commands();
      JSONObject cmdid = jsonCommandlist.getJSONObject(""+i);
      cmd.setId(cmdid.getString("id"));
      cmd.setName(cmdid.getString("name"));
      cmd.setCategory(cmdid.getString("category"));
      Commandslist[i] = cmd;
                 }
    catch (JSONException e){System.out.println( "ERROR: " + e );}
}
        }
          return Commandslist ;
      }

    public static Commands extractOnecmd(String jsontxt , int rang) {
             Commands cmdGroup[] = CommandList.extractCommands(jsontxt);
        try {
             return cmdGroup[rang];
             }
        catch (ArrayIndexOutOfBoundsException e){return null;}
            }
  
    public static String extractcmdData(String jsontxt , int rang, String value) {
    
       Commands cmdGroup[] = CommandList.extractCommands(jsontxt);
       String a = null;

        if (value.equals("id")){ a = cmdGroup[rang].getId(); }
        else if(value.equals("name")) {a = cmdGroup[rang].getName();}
        else if(value.equals("category")) {a = cmdGroup[rang].getCategory();}
        return a ;
       }
    /////////

    public static CmdOptions[] extractCommandsOptions(String cmd_optionlink){

        String jsonTxt_cmdeoption = BeefRequester.BeefGetRequest(cmd_optionlink);
        JSONObject jso = new JSONObject();
        jso = (JSONObject) JSONSerializer.toJSON(jsonTxt_cmdeoption);

	String cmd_options_json = jso.get("options").toString();
        JSONArray jsonarray = new JSONArray();
        jsonarray = (JSONArray) JSONSerializer.toJSON( cmd_options_json );
            CmdOptions Cmdoptlist[] = new CmdOptions[jsonarray.size()];
            if (jsonarray.isEmpty()){   } else{
        for (int i = 0; i < jsonarray.size(); i++){
        //System.out.println(jsonarray.getJSONObject(i).getString("name"));
         try {
             CmdOptions cmdopt = new CmdOptions();
             cmdopt.setName(jsonarray.getJSONObject(i).getString("name"));
             cmdopt.setValue(jsonarray.getJSONObject(i).getString("value"));
             cmdopt.setUi_label(jsonarray.getJSONObject(i).getString("ui_label"));
             Cmdoptlist[i] = cmdopt;
                 }
        catch (JSONException e){System.out.println( "ERROR: " + e );}
        }
     }
     return Cmdoptlist ;
   }
    
    public static String extractcmdOptionData(String jsontxt , int rang, String value) {
    
       CmdOptions Cmdoptlist[] = CommandList.extractCommandsOptions(jsontxt);
       String a = null;
        if(value.equals("name")) {a = Cmdoptlist[rang].getName();}
        else if(value.equals("value")) {a = Cmdoptlist[rang].getValue();}
        else if(value.equals("ui_label")) {a = Cmdoptlist[rang].getUi_label();}
        return a ;
       }

 }




