package com.eyesopencrew;

/**
 * @author Beny Green - gacksecurity.blogspot.com
 * EyesOpenCrew -
 *
 * Object representation of one command from command list.
 */
public class Commands {
  private String id;
  private String name;
  private String category;

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


}
