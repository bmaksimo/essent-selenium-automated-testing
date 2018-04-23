package com.essent.testing.dwp.menu.model;

import java.util.List;
import java.util.Map;

public interface TopMenu {
  public class Item {
    private String label;
    private String link;
    public Item(String label, String link) {
      super();
      this.label = label;
      this.link = link;
    }
    public String getLabel() {
      return label;
    }
    public String getLink() {
      return link;
    }

      @Override
      public String toString() {
          return "Item{" +
              "label='" + label + '\'' +
              ", link='" + link + '\'' +
              '}';
      }
  }

  TopMenu    item(String itemName, Item item);

  TopMenu    merge(Map<String, Item> items);

  List<Item> items();

  Item       getItem(String key);


}
