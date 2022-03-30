package com.pierluigi.utils;

import java.util.Map;


public class Html {

  public static String linkToFindAndDelete(String urlFind, String urlDelete, String id) {
    String td = "<a href='" + urlDelete + "?codigo=" + id + "'> &#x274c;&nbsp;</a>";
    td += "<a href='" + urlFind + "?codigo=" + id + "'>&nbsp;&#x1f50e;</a>";
    return td;
  }

  public static String getComboBox(Map<String, String> data, String valueSelected) {
    String select = "";
    for (Map.Entry<String, String> option : data.entrySet()) {
      String key = option.getKey();
      String selected = (key.equals(valueSelected)) ? "' selected >" : "'>";
      select += "<option value='" + key + selected + option.getValue() + "</option>";
    }
    return select;
  }
}
