package com.eyesopencrew;

/**
 * @author Beny Green - gacksecurity.blogspot.com
 * EyesOpenCrew -
 *
 * Object representation of one DNS rule.
 */
public class DnsRules {
  private String id;
  private String pattern;
  private String type;
  private String response;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
  
}
