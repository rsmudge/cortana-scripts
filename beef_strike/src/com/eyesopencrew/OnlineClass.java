package com.eyesopencrew;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * @author Beny Green - gacksecurity.blogspot.com
 *
 * EyesOpenCrew
 */

public class OnlineClass {

    /**
     *
     * @param jsontxt
     * @return Zombies[]
     *
     *   - Extract zombis obtained from Json representation.
     */
    public static Zombies[] extractOnline(String jsontxt){
      
      JSONObject json = (JSONObject) JSONSerializer.toJSON( jsontxt );
      JSONObject online = json.getJSONObject("hooked-browsers").getJSONObject("online");
      
      Zombies[] zombieGroup = new Zombies[online.size()];
if (online.isEmpty()){
    return zombieGroup ;
} else{
        for (int i = 0; i < online.size(); i++){
            try {
      Zombies zombie = new Zombies();
      JSONObject zombieid = online.getJSONObject(""+i);
      zombie.setSession(zombieid.getString("session"));
      zombie.setName(zombieid.getString("name"));
      zombie.setVersion(zombieid.getString("version"));
      zombie.setOs(zombieid.getString("os"));
      zombie.setPlatform(zombieid.getString("platform"));
      zombie.setIp(zombieid.getString("ip"));
      zombie.setPort(zombieid.getString("port"));
      zombie.setDomain(zombieid.getString("domain"));
      zombie.setPage_uri(zombieid.getString("page_uri"));
      zombieGroup[i] = zombie;
     //System.out.println(zombieGroup[i].getOs());
             }
   catch (JSONException e){System.out.println( "ERROR: " + e );}
     }
          return zombieGroup ;
         }
      }

    /**
     * 
     * @param jsontxt
     * @param rang
     * @return Zombies
     *
     * Extract one online zombi obtained from a Json representation.
     */
    public static Zombies extractOneZombie(String jsontxt , int rang) {
        Zombies zombieGroup[] = OnlineClass.extractOnline(jsontxt);
        try {
             return zombieGroup[rang];
           }
        catch (ArrayIndexOutOfBoundsException e){return null;}
       
            }
    /**
     *
     * @param jsontxt
     * @param rang
     * @param value
     * @return String
     *
     * * extract a particular data about a particular online zombi obtained from a Json representation.
     */
    public static String extractZombieData(String jsontxt , int rang, String value) {

       Zombies zombieGroup[] = OnlineClass.extractOnline(jsontxt);
       String a = null;

        if (value.equals("session")){ a = zombieGroup[rang].getSession(); }
        else if(value.equals("name")) {a = zombieGroup[rang].getName();}
        else if(value.equals("version")) {a = zombieGroup[rang].getVersion();}
        else if(value.equals("os")) {a = zombieGroup[rang].getOs();}
        else if(value.equals("platform")) {a = zombieGroup[rang].getPlatform();}
        else if(value.equals("ip")) {a = zombieGroup[rang].getIp();}
        else if(value.equals("port")) {a = zombieGroup[rang].getPort();}
        else if(value.equals("domain")) {a = zombieGroup[rang].getDomain();}
        else if(value.equals("page_uri")) {a = zombieGroup[rang].getPage_uri();}
        return a ;
       }
 }

