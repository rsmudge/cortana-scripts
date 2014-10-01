package com.eyesopencrew;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * @author Beny Green - gacksecurity.blogspot.com
 *
 * EyesOpenCrew
 */
public class OfflineClass {
//List of offline zombies
    /**
     *
     * @param jsontxt
     * @return Zombie[]
     *   - Extract offline zombis from Json representation.
     * 
     */
    public static Zombies[] extractOffline(String jsontxt){

        JSONObject json = new JSONObject();
        json = (JSONObject) JSONSerializer.toJSON( jsontxt );
     // JSONObject json = (JSONObject)

      JSONObject Offline = json.getJSONObject("hooked-browsers").getJSONObject("offline");
        Zombies zombieGroup[] = new Zombies[Offline.size()];
if (Offline.isEmpty()){
} else{
       for (int i = 0; i < Offline.size(); i++){

             try {
      Zombies zombie = new Zombies();
      JSONObject zombieid = Offline.getJSONObject(""+i);
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
                 }
    catch (JSONException e){System.out.println( "ERROR: " + e );}
}
        }
          return zombieGroup ;
      }
    /**
     *
     * @param jsontxt
     * @param rang
     * @return Zombies
     *
     * Extract one offline zombi obtained from Json representation.
     */
    public static Zombies extractOneZombie(String jsontxt , int rang) {
             Zombies zombieGroup[] = OfflineClass.extractOffline(jsontxt);
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
     * Extract a particular data about a particular offline zombi obtained from a Json representation.
     */
    public static String extractZombieData(String jsontxt , int rang, String value) {
    
        Zombies zombieGroup[] = OfflineClass.extractOffline(jsontxt);
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



