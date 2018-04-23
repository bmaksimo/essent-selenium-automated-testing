package com.essent.testing.dwp.menu.model.impl;

import com.essent.testing.dwp.menu.model.TopMenu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TopMenuImpl implements TopMenu{


  private Map<String, Item> items = new HashMap<>();

  public TopMenuImpl() {
  }
  @Override
  public TopMenu item(String itemName, Item item) {
    items.put(itemName, item);
    return this;
  }

  @Override
  public TopMenu merge(Map<String, Item> items) {
    items.putAll(items);
    return this;
  }

  @Override
  public List<Item> items() {
    List<Item> itemsList = items.entrySet().stream().map(entry -> {return entry.getValue();}).collect(Collectors.toList());
    return itemsList;
  }

    @Override
    public Item getItem(String key) {
        return items.get(key);
    }
}
