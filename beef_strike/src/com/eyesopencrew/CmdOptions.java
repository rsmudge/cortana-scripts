package com.eyesopencrew;

/**
 * @author Beny Green - gacksecurity.blogspot.com
 * EyesOpenCrew -
 *
 * Object representation of one command from command list.
 */
public class CmdOptions {
  private String info;
  private String name;
  private String value;
  private String ui_label;
     /**
   *
   * @return info
   */
  public String getInfo() {
        return info;
    }
    /**
     *
     * @param info
     */
    public void setInfo(String id) {
        this.info = id;
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

     public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUi_label() {
        return ui_label;
    }

    public void setUi_label(String ui_label) {
        this.ui_label = ui_label;
    }

}
