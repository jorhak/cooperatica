package com.pierluigi.utils;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IVista {

  public String getTitle();

  public String linkToFindAndDelete(String id);

  public String getTable(List<Map<String, String>> table);

  public void show(HttpServletRequest req, HttpServletResponse res, String template);
}
