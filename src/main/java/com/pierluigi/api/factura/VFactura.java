package com.pierluigi.api.factura;

import com.google.gson.Gson;
import com.pierluigi.utils.Html;
import com.pierluigi.utils.IVista;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VFactura  {

    public void show(HttpServletRequest req, HttpServletResponse res, String template) {
    req.setAttribute("titulo", getTitle());
    try {
      req.getRequestDispatcher(template).forward(req, res);
    } catch (Exception e) {
    }
  }

  public void showMasterDetail(HttpServletRequest req, HttpServletResponse res) {
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");

    Map<String, String> modelo = (Map<String, String>) req.getAttribute("modelo");
    List<Map<String, String>> items = (List<Map<String, String>>) req.getAttribute("items");

    Map<String, Object> data = new LinkedHashMap<>();
    
    if (modelo != null) {
      for (Map.Entry<String, String> entry : modelo.entrySet()) {
        data.put(entry.getKey(), entry.getValue());
      }
      data.put("items", items);
    }
    
    String json = modelo != null ? new Gson().toJson(data) : "{\"message\": \"ERROR\"}";
    try {
      ServletOutputStream out = res.getOutputStream();
      out.print(json);
      out.flush();
    } catch (Exception e) {
    }
  }

  public void showModel(HttpServletRequest req, HttpServletResponse res) {
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");

    Map<String, String> modelo = (Map<String, String>) req.getAttribute("modelo");
    String json = modelo != null
            ? new Gson().toJson(modelo)
            : "{\"message\": \"ERROR\"}";

    try {
      ServletOutputStream out = res.getOutputStream();
      out.print(json);
      out.flush();
    } catch (Exception e) {
    }
  }

  public void showMessage(HttpServletRequest req, HttpServletResponse res) {
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");

    boolean state = (boolean) req.getAttribute("state");
    String message = !state ? "ERROR" : "OK";
    String json = "{\"message\": \"" + message + "\"}";

    try {
      ServletOutputStream out = res.getOutputStream();
      out.print(json);
      out.flush();
    } catch (Exception e) {
    }
  }

  public void showList(HttpServletRequest req, HttpServletResponse res) {
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");

    List<Map<String, String>> table = (List<Map<String, String>>) req.getAttribute("table");
    String json = table != null
            ? new Gson().toJson(table)
            : "{\"message\": \"ERROR\"}";

    try {
      ServletOutputStream out = res.getOutputStream();
      out.print(json);
      out.flush();
    } catch (Exception e) {
    }
  }

  private String getTitle() {
    return "Buscar Consumo";
  }
}
