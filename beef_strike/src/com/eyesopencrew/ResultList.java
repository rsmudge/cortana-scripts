package com.eyesopencrew;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * @author Beny Green - gacksecurity.blogspot.com
 * EyesOpenCrew - @thebenygreen
 *
 * Object representation of result for a module executed by BeEF.
 */
public class ResultList {
    //List of Results for a module executed by BeEF
    /**
     *
     * @param jsontxt
     * @return rslt[]
     *   - Extract results from Json representation.
     * 
     */
    public static Results[] extractResults(String jsontxt){
      
      JSONObject jsonCommandlist = (JSONObject) JSONSerializer.toJSON( jsontxt );
      //JSONObject Offline = json.getJSONObject("hooked-browsers").getJSONObject("offline");
        Results Resultslist[] = new Results[jsonCommandlist.size()];
if (jsonCommandlist.isEmpty()){
   
} else{
       for (int i = 0; i < jsonCommandlist.size(); i++){

             try {
      Results rslt = new Results();
      JSONObject rsltid = jsonCommandlist.getJSONObject(""+i);
      rslt.setDate(rsltid.getString("date"));
      rslt.setData(rsltid.getString("data"));
      Resultslist[i] = rslt;
                 }
    catch (JSONException e){System.out.println( "ERROR: " + e );}
}
        }
          return Resultslist ;
      }

    public static Results extractOnerslt(String jsontxt , int rang) {
             Results rsltGroup[] = ResultList.extractResults(jsontxt);
        try {
             return rsltGroup[rang];
             }
        catch (ArrayIndexOutOfBoundsException e){return null;}
            }
  
    public static String extractrsltData(String jsontxt , int rang, String value) {
    
       Results rsltGroup[] = ResultList.extractResults(jsontxt);
       String a = null;

        if (value.equals("date")){ a = rsltGroup[rang].getDate(); }
        else if(value.equals("data")) {a = rsltGroup[rang].getData();}
        return a ;
       }
}
