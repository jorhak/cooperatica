package com.pierluigi.socio;


import com.pierluigi.utils.IControlador;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CSocio implements IControlador {

  private final MSocio modelo;
  private final VSocio vista;

  public CSocio() {
    this.modelo = new MSocio();
    this.vista = new VSocio();
  }

  @Override
  public void create(HttpServletRequest req, HttpServletResponse res) {
    req.setAttribute("table", modelo.findAll());
    vista.show(req, res, "/template/socio/socio.jsp");
  }

  @Override
  public void save(HttpServletRequest req, HttpServletResponse res) {
    Map<String, String> request = new LinkedHashMap<>();
    request.put("codigo", req.getParameter("codigo"));
    request.put("nombre", req.getParameter("nombre"));
    request.put("apellido", req.getParameter("apellido"));
    request.put("direccion", req.getParameter("direccion"));

    modelo.setData(request);
    
    req.setAttribute("modelo", modelo.save());
    req.setAttribute("table", modelo.findAll());
    vista.show(req, res, "/template/socio/socio.jsp");
  }

  @Override
  public void delete(HttpServletRequest req, HttpServletResponse res) {
    boolean state = modelo.delete(req.getParameter("codigo"));

    req.setAttribute("state", state);
    req.setAttribute("table", modelo.findAll());
    vista.show(req, res, "/template/socio/socio.jsp");
  }

  @Override
  public void find(HttpServletRequest req, HttpServletResponse res) {
    req.setAttribute("modelo", modelo.find("codigo", req.getParameter("codigo")));
    req.setAttribute("table", modelo.findAll());
    vista.show(req, res, "/template/socio/socio.jsp");
  }

  @Override
  public void findAll(HttpServletRequest req, HttpServletResponse res) {
    req.setAttribute("table", modelo.findAll());
    vista.show(req, res, "/template/socio/socio.jsp");
  }
}
