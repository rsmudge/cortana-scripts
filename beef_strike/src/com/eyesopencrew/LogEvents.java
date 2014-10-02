package com.eyesopencrew;

/**
 * @author Beny Green - gacksecurity.blogspot.com
 * EyesOpenCrew -
 *
 * Object representation of one log entry.
 */
public class LogEvents {
     private String date;
  private String event;
  private String type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
