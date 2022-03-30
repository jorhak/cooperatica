package com.pierluigi.socio;


import com.pierluigi.utils.Html;
import com.pierluigi.utils.IVista;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VSocio implements IVista {

  @Override
  public String getTitle() {
    return "Registrar Socio";
  }

  @Override
  public String linkToFindAndDelete(String id) {
    return Html.linkToFindAndDelete(
            "/cooperativa/socio/find",
            "/cooperativa/socio/delete",
            id
    );
  }

  @Override
  public String getTable(List<Map<String, String>> table) {
    String tr = "";
    for (int i = 0; i < table.size(); i++) {
      Map<String, String> row = table.get(i);
      tr += "<tr>";
      tr += "<td>" + row.getOrDefault("codigo", "") + "</td>";      
      tr += "<td>" + row.getOrDefault("nombre", "") + "</td>";
      tr += "<td>" + row.getOrDefault("apellido", "") + "</td>";
      tr += "<td>" + row.getOrDefault("direccion", "") + "</td>";
      tr += "<td>" + linkToFindAndDelete(row.getOrDefault("codigo", "0")) + "</td>";
      tr += "</tr>";
    }
    return tr;
  }

  @Override
  public void show(HttpServletRequest req, HttpServletResponse res, String template) {
    List<Map<String, String>> table = (List<Map<String, String>>) req.getAttribute("table");
    Map<String, String> modelo = (Map<String, String>) req.getAttribute("modelo");

    if (modelo != null) {
      req.setAttribute("codigo", modelo.getOrDefault("codigo", "0"));      
      req.setAttribute("nombre", modelo.getOrDefault("nombre", ""));
      req.setAttribute("apellido", modelo.getOrDefault("apellido", ""));
      req.setAttribute("direccion", modelo.getOrDefault("direccion", ""));
    } else {
      req.setAttribute("codigo", "0");
    }


    req.setAttribute("table", getTable(table));
    req.setAttribute("title", getTitle());

    try {
      req.getRequestDispatcher(template).forward(req, res);
    } catch (Exception e) {
    }
  }
}
