package com.eyesopencrew;

/**
 * @author Beny Green - gacksecurity.blogspot.com
 * EyesOpenCrew -
 *
 * Object representation of one autorun entry from beefstrike.
 */
public class Autorun {
  private String cc;
  private String id;
  private String name;
  private String browser;
  private String category;
  private String Param;

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
  
    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

     public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getParam() {
        return Param;
    }

    public void setParam(String Param) {
        this.Param = Param;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }


}
