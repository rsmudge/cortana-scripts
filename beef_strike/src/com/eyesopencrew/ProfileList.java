package com.eyesopencrew;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import java.io.FileWriter;
import java.io.File;
/**
 * @author Beny Green - gacksecurity.blogspot.com
 *
 * EyesOpenCrew
 */
public class ProfileList {
//List of Profiles entries of beefstrike
    /**
     *
     * @param jsontxt
     * @return Profil[]
     *   - Extract Profile entries from Json representation.
     * 
     */
    public static Profile[] extractProfile(String jsontxt){
      
      JSONObject jsonProfilelist = (JSONObject) JSONSerializer.toJSON( jsontxt );
      //JSONObject Offline = json.getJSONObject("hooked-browsers").getJSONObject("offline");
        Profile Profilelist[] = new Profile[jsonProfilelist.size()];
if (jsonProfilelist.isEmpty()){
    
} else{
       for (int i = 0; i < jsonProfilelist.size(); i++){

             try {
      Profile Profil = new Profile();
      JSONObject Profilid = jsonProfilelist.getJSONObject(""+i);
      Profil.setId(Profilid.getString("id"));
      Profil.setBrowser(Profilid.getString("Browser"));
      Profil.setUserAgent(Profilid.getString("UserAgent"));
      Profil.setVersion(Profilid.getString("Version"));
      Profil.setOS(Profilid.getString("OS"));
      Profil.setPlatform(Profilid.getString("Platform"));
      Profil.setActivex(Profilid.getString("ActiveX"));
      Profil.setFlash(Profilid.getString("Flash"));
      Profil.setJava(Profilid.getString("Java"));
      Profil.setVBScript(Profilid.getString("VBScript"));
      Profil.setPlugins(Profilid.getString("Plugins"));
      Profil.setAttack_URL(Profilid.getString("Attack_URL"));
      Profil.setLink_cc(Profilid.getString("Link_cc"));
      Profilelist[i] = Profil;
                 }
    catch (JSONException e){System.out.println( "ERROR: " + e );}
}
        }
          return Profilelist ;
      }

    public static Profile extractOneProfil(String jsontxt , int rang) {
             Profile ProfilGroup[] = ProfileList.extractProfile(jsontxt);
        try {
             return ProfilGroup[rang];
             }
        catch (ArrayIndexOutOfBoundsException e){return null;}
            }
  
    public static String extractProfilData(String jsontxt , int rang, String value) {
    
       Profile ProfilGroup[] = ProfileList.extractProfile(jsontxt);
       String a = null;

       if (value.equals("id")){ a = ProfilGroup[rang].getId(); }
       else if(value.equals("Browser")) {a = ProfilGroup[rang].getBrowser();}
       else if(value.equals("UserAgent")) {a = ProfilGroup[rang].getUserAgent();}
       else if(value.equals("Version")) {a = ProfilGroup[rang].getVersion();}
       else if(value.equals("OS")) {a = ProfilGroup[rang].getOS();}
       else if(value.equals("Platform")) {a = ProfilGroup[rang].getPlatform();}
       else if(value.equals("ActiveX")) {a = ProfilGroup[rang].getActivex();}
       else if(value.equals("Flash")) {a = ProfilGroup[rang].getFlash();}
       else if(value.equals("Java")) {a = ProfilGroup[rang].getJava();}
       else if(value.equals("VBScript")) {a = ProfilGroup[rang].getVBScript();}
       else if(value.equals("Plugins")) {a = ProfilGroup[rang].getPlugins();}
       else if(value.equals("Attack_URL")) {a = ProfilGroup[rang].getAttack_URL();}
       else if(value.equals("Link_cc")) {a = ProfilGroup[rang].getLink_cc();}
       return a ;
       }

    public static void saveProfileList (String pathoffiletosave , String texttosave){
        try{
           File ff=new File(pathoffiletosave);
           ff.createNewFile();
           FileWriter ffw=new FileWriter(ff);
           ffw.write(texttosave);
           ffw.close();
        } catch (Exception e) {}
    }
}



