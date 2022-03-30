package com.pierluigi.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IControlador {

  public void create(HttpServletRequest req, HttpServletResponse res);

  public void save(HttpServletRequest req, HttpServletResponse res);

  public void delete(HttpServletRequest req, HttpServletResponse res);

  public void find(HttpServletRequest req, HttpServletResponse res);

  public void findAll(HttpServletRequest req, HttpServletResponse res);  
}
