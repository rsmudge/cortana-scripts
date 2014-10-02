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
public class AutorunList {
//List of Autoruns entries of beefstrike
    /**
     *
     * @param jsontxt
     * @return Autor[]
     *   - Extract autorun entries from Json representation.
     * 
     */
    public static Autorun[] extractAutorun(String jsontxt){
      
      JSONObject jsonAutorunlist = (JSONObject) JSONSerializer.toJSON( jsontxt );
      //JSONObject Offline = json.getJSONObject("hooked-browsers").getJSONObject("offline");
        Autorun Autorunlist[] = new Autorun[jsonAutorunlist.size()];
if (jsonAutorunlist.isEmpty()){
    System.out.println("No Autorun, may be list is empty !");
} else{
       for (int i = 0; i < jsonAutorunlist.size(); i++){

             try {
      Autorun Autor = new Autorun();
      JSONObject Autorid = jsonAutorunlist.getJSONObject(""+i);
      Autor.setId(Autorid.getString("id"));
      Autor.setName(Autorid.getString("name"));
      Autor.setCc(Autorid.getString("cc"));
      Autor.setBrowser(Autorid.getString("browser"));
      Autor.setParam(Autorid.getString("Param"));
      Autor.setCategory(Autorid.getString("category"));
      Autorunlist[i] = Autor;
                 }
    catch (JSONException e){System.out.println( "ERROR: " + e );}
}
        }
          return Autorunlist ;
      }

    public static Autorun extractOneAutor(String jsontxt , int rang) {
             Autorun AutorGroup[] = AutorunList.extractAutorun(jsontxt);
        try {
             return AutorGroup[rang];
             }
        catch (ArrayIndexOutOfBoundsException e){return null;}
            }
  
    public static String extractAutorData(String jsontxt , int rang, String value) {
    
       Autorun AutorGroup[] = AutorunList.extractAutorun(jsontxt);
       String a = null;

        if (value.equals("id")){ a = AutorGroup[rang].getId(); }
        else if(value.equals("name")) {a = AutorGroup[rang].getName();}
        else if(value.equals("cc")) {a = AutorGroup[rang].getCc();}
        else if(value.equals("browser")) {a = AutorGroup[rang].getBrowser();}
        else if(value.equals("Param")) {a = AutorGroup[rang].getParam();}
        else if(value.equals("category")) {a = AutorGroup[rang].getCategory();}
        return a ;
       }

    public static void saveAutorunList (String pathoffiletosave , String texttosave){
        try{
           File ff=new File(pathoffiletosave);
           ff.createNewFile();
           FileWriter ffw=new FileWriter(ff);
           ffw.write(texttosave);
           ffw.close();
        } catch (Exception e) {
        System.out.println("Unable to save file");
        }
    }
}



