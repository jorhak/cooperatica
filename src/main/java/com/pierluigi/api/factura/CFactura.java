package com.pierluigi.api.factura;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pierluigi.utils.Middleware;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CFactura {

    private final VFactura vista;
    private final MFactura modelo;

    
    public CFactura() {
    this.vista = new VFactura();
    this.modelo = new MFactura();
  }

  public void create(HttpServletRequest req, HttpServletResponse res) {
    vista.show(req, res, "/template/apiFactura/buscar.jsp");
  }

  private Map<String, String> readModel(String json) {
    JsonElement elementObject = JsonParser.parseString(json);
    JsonObject request = elementObject.getAsJsonObject();

    Map<String, String> requestModel = new LinkedHashMap<>();
    requestModel.put("id", request.get("id").getAsString());
    requestModel.put("fecha", request.get("fecha").getAsString());
    requestModel.put("importe", request.get("importe").getAsString());

    return requestModel;
  }

  private List<Map<String, String>> readItems(String json) {
    JsonElement elementObject = JsonParser.parseString(json);
    JsonArray itemsJson = new JsonArray();
    try {
      itemsJson = elementObject.getAsJsonObject().get("items").getAsJsonArray();
    } catch (Exception e) {
    }

    List<Map<String, String>> requestItems = new LinkedList<>();

    for (int i = 0; i < itemsJson.size(); i++) {
      JsonObject itemJson = itemsJson.get(i).getAsJsonObject();

      Map<String, String> item = new HashMap<>();
      item.put("id", itemJson.get("id").getAsString());
      item.put("cantidad", itemJson.get("cantidad").getAsString());
      item.put("importe", itemJson.get("importe").getAsString());
      item.put("periodo", itemJson.get("periodo").getAsString());
      item.put("id_factura", itemJson.get("id_factura").getAsString());
      item.put("id_consumo", itemJson.get("id_consmo").getAsString());
      requestItems.add(item);
    }
    return requestItems;
  }

  public void save(HttpServletRequest req, HttpServletResponse res) {
    String json = Middleware.getJson(req);

    Map<String, String> requestModel = readModel(json);
    List<Map<String, String>> requestItems = readItems(json);

    modelo.setData(requestModel);
    modelo.setDataItems(requestItems);

    Map<String, String> cargaHoraria = modelo.save();
    if (cargaHoraria != null) {
      List<Map<String, String>> items = modelo.saveItems(cargaHoraria.get("id"));
      req.setAttribute("modelo", cargaHoraria);
      req.setAttribute("items", items);

    }
    vista.showMasterDetail(req, res);
  }

  public void saveItem(HttpServletRequest req, HttpServletResponse res) {
    String json = Middleware.getJson(req);
    JsonObject requestJson = JsonParser.parseString(json).getAsJsonObject();

    Map<String, String> request = new LinkedHashMap<>();
    request.put("id", requestJson.get("id").getAsString());
    request.put("dia", requestJson.get("dia").getAsString());
    request.put("hora_inicio", requestJson.get("hora_inicio").getAsString());
    request.put("hora_final", requestJson.get("hora_final").getAsString());
    request.put("carga_horaria_id", requestJson.get("carga_horaria_id").getAsString());

    modelo.setDataItem(request);
    req.setAttribute("modelo", modelo.saveItem());
    vista.showModel(req, res);
  }

  public void delete(HttpServletRequest req, HttpServletResponse res) {
    String id = req.getParameter("id");

    boolean state = false;

    if (modelo.deleteItems(id)) {
      state = modelo.delete(id);
    }

    req.setAttribute("state", state);
    vista.showMessage(req, res);
  }

  public void deleteItem(HttpServletRequest req, HttpServletResponse res) {
    String cargaHorariaId = req.getParameter("id");
    String claseId = req.getParameter("claseId");

    boolean state = modelo.deleteItem(cargaHorariaId, claseId);
    req.setAttribute("state", state);

    vista.showMessage(req, res);
  }

  public void find(HttpServletRequest req, HttpServletResponse res) {
    String id = req.getParameter("id");

    req.setAttribute("modelo", modelo.find("id", id));
    req.setAttribute("items", modelo.findItems(id));

    vista.showMasterDetail(req, res);
  }

  public void findItem(HttpServletRequest req, HttpServletResponse res) {
    String cargaHorariaId = req.getParameter("id");
    String claseId = req.getParameter("claseId");

    req.setAttribute("modelo", modelo.findItem(cargaHorariaId, claseId));
    vista.showModel(req, res);
  }

  public void findItems(HttpServletRequest req, HttpServletResponse res) {
    String cargaHorariaId = req.getParameter("id");

    req.setAttribute("table", modelo.findItems(cargaHorariaId));
    vista.showList(req, res);
  }

  public void findAll(HttpServletRequest req, HttpServletResponse res) {
    req.setAttribute("table", modelo.findAll());
    vista.showList(req, res);
  }
}


//select (s.nombre, s.apellido) as nombre from socio s, consumo c where s.codigo=c.cod_socio and c.id=9;
