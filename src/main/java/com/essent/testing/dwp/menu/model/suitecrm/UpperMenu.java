package com.essent.testing.dwp.menu.model.suitecrm;

import java.util.HashMap;
import java.util.Map;

public interface UpperMenu {
  public class Container {
    private Data[] data;

    public Data[] getData() {
      return data;
    }

    public void setData(Data[] data) {
      this.data = data;
    }
    
  }
  public class Data {
    private String label;
    private String link;
    private Map<String, String> params = new HashMap<>();
    public String getLabel() {
      return label;
    }
    public void setLabel(String label) {
      this.label = label;
    }
    public String getLink() {
      return link;
    }
    public void setLink(String link) {
      this.link = link;
    }
    public Map<String, String> getParams() {
      return params;
    }
    public void setParams(Map<String, String> params) {
      this.params = params;
    }
  }
}
