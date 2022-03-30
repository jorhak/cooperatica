package com.pierluigi.api.socio;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pierluigi.utils.Middleware;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CSocio {

    private final VSocio vista;
    private final MSocio modelo;

    public CSocio() {
        this.vista = new VSocio();
        this.modelo = new MSocio();
    }
    
    public void create(HttpServletRequest req, HttpServletResponse res) {
        //req.setAttribute("table", modelo.findAll());
        vista.view(req, res, "/template/apiSocio/socio.jsp");
    }

    public void apiSave(HttpServletRequest req, HttpServletResponse res) {
        String json = Middleware.getJson(req);
        JsonElement elementObject = JsonParser.parseString(json);
        JsonObject requestJson = elementObject.getAsJsonObject();

        Map<String, String> request = new LinkedHashMap<>();
        request.put("codigo", requestJson.get("codigo").getAsString());
        request.put("nombre", requestJson.get("nombre").getAsString());
        request.put("apellido", requestJson.get("apellido").getAsString());
        request.put("direccion", requestJson.get("direccion").getAsString());

        modelo.setData(request);

        req.setAttribute("modelo", modelo.save());
        vista.apiShowModelo(req, res);
    }

    public void apiFind(HttpServletRequest req, HttpServletResponse res) {
        String json = Middleware.getJson(req);
        JsonElement elementObject = JsonParser.parseString(json);
        JsonObject requestJson = elementObject.getAsJsonObject();

        String id;
        id = requestJson.get("codigo").getAsString();
        req.setAttribute("modelo", modelo.find("codigo", id));
        vista.apiShowModelo(req, res);
    }

    public void apiDelete(HttpServletRequest req, HttpServletResponse res) {
        String json = Middleware.getJson(req);
        JsonElement elementObject = JsonParser.parseString(json);
        JsonObject requestJson = elementObject.getAsJsonObject();

        String id;
        id = requestJson.get("codigo").getAsString();
        req.setAttribute("state", modelo.delete(id));
        vista.apiShowMessage(req, res);
    }

    public void apiFindAll(HttpServletRequest req, HttpServletResponse res) {
        req.setAttribute("table", modelo.findAll());
        vista.apiShowList(req, res);
    }
}
