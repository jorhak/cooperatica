package com.pierluigi.utils;

import java.util.List;
import java.util.Map;

public interface IModelo {

  public void setData(Map<String, String> request);

  public Map<String, String> save();

  public boolean delete(String id);

  public Map<String, String> find(String columnName, Object columnValue);

  public List<Map<String, String>> findAll();

  public Map<String, String> comboBox();
}
