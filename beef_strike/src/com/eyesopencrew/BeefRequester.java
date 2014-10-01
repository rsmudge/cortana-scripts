package com.eyesopencrew;


import java.io.IOException;
import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * @author Beny Green - gacksecurity.blogspot.com
 *
 * EyesOpenCrew
 */
public class BeefRequester {
    private static String infos;
    
    /**
     *
     * @param beefUrl
     * @return
     */
    public static String BeefGetRequest(String beefUrl)  {

        BeefRequestThread beefthrd = new BeefRequestThread(beefUrl);
        beefthrd.start();
        try {
            beefthrd.join(10000); // limit to 10 seconds
            //System.out.println("After that: " + beefthrd.getHooks());
            return beefthrd.getHooks();
        } catch (InterruptedException ex) {
            Logger.getLogger(BeefRequester.class.getName()).log(Level.SEVERE, null, ex);
        }
       if ( beefthrd.getHooks().equals("NULL")){
            System.out.println(" Empty ");
            }
   
        return beefthrd.getHooks();
    }

    /**
     *
     * @param beefUrl
     * @return
     */
    public static String BeefGetRequestNoThread(String beefUrl) {

            try {

		     URL url = new URL(beefUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
                        //String t = br.readLine();
			while ((output = br.readLine()) != null) {
				//System.out.println(output);
                                BeefRequester.setInfos(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
          return infos;
    }

    /**
     *
     * @param beefUrl
     * @return
     */
    public static String BeefPostRequest(String beefUrl){
     String result = null;
     return result ;}

    /**
     *
     * @param jsonstr
     * @return
     */
    public String getResultID(String jsonstr) {
         JSONObject jsrs = new JSONObject();
	 jsrs = (JSONObject) JSONSerializer.toJSON(jsonstr);
	String id_result = jsrs.getString("command_id");
        return id_result;
    }
    /**
     *
     * @return
     */
    public static String getInfos() {
        return infos;
    }

    /**
     *
     * @param infos
     */
    public static void setInfos(String infos) {
        BeefRequester.infos = infos;
    }

    /**
     * @param jsontxt
     * @param key
     * Special addition for geolocation feature.
     */

    public static String extractMapData(String jsontxt, String key) {
       JSONObject jso = (JSONObject) JSONSerializer.toJSON(jsontxt);
       String a = null;
       if( jso.getJSONObject("geobytes").containsKey(key)) {
       a = jso.getJSONObject("geobytes").get(key).toString();
        }
       return a ;
       }
}
