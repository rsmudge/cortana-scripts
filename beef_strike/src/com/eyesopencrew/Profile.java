package com.eyesopencrew;

/**
 * @author Beny Green - gacksecurity.blogspot.com
 * EyesOpenCrew -
 *
 * Object representation of one profile entry from beefstrike.
 */
public class Profile {
  
  private String id;
  private String Browser;
  private String UserAgent;
  private String Version;
  private String OS;
  private String Platform;
  private String ActiveX;
  private String Flash;
  private String Java;
  private String VBScript;
  private String Plugins;
  private String Attack_URL;
  private String Link_cc;

     /**
   *
   * @return id
   */
  public String getId() {
        return id;
    }
    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getActivex() {
        return ActiveX;
    }

    public void setActivex(String Activex) {
        this.ActiveX = Activex;
    }

    public String getAttack_URL() {
        return Attack_URL;
    }

    public void setAttack_URL(String Attack_URL) {
        this.Attack_URL = Attack_URL;
    }

    public String getBrowser() {
        return Browser;
    }

    public void setBrowser(String Browser) {
        this.Browser = Browser;
    }

    public String getFlash() {
        return Flash;
    }

    public void setFlash(String Flash) {
        this.Flash = Flash;
    }

    public String getJava() {
        return Java;
    }

    public void setJava(String Java) {
        this.Java = Java;
    }

    public String getLink_cc() {
        return Link_cc;
    }

    public void setLink_cc(String Link_cc) {
        this.Link_cc = Link_cc;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getPlatform() {
        return Platform;
    }

    public void setPlatform(String Platform) {
        this.Platform = Platform;
    }

    public String getPlugins() {
        return Plugins;
    }

    public void setPlugins(String Plugins) {
        this.Plugins = Plugins;
    }

    public String getUserAgent() {
        return UserAgent;
    }

    public void setUserAgent(String UserAgent) {
        this.UserAgent = UserAgent;
    }

    public String getVBScript() {
        return VBScript;
    }

    public void setVBScript(String VBScript) {
        this.VBScript = VBScript;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }
  
}
