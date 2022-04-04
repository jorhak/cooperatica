package com.pierluigi.api.consumo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pierluigi.api.socio.MSocio;
import com.pierluigi.utils.Middleware;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CConsumo {

    private final MConsumo modelo;
    private final VConsumo vista;
    private final MSocio modeloSocio;

    public CConsumo() {
        this.modelo = new MConsumo();
        this.vista = new VConsumo();
        this.modeloSocio = new MSocio();
    }
    
    public void create(HttpServletRequest req, HttpServletResponse res) {
        //req.setAttribute("table", modelo.findAll());
        vista.view(req, res, "/template/apiConsumo/consumo.jsp");
    }
    
    public void apiSave(HttpServletRequest req, HttpServletResponse res) {
        String json = Middleware.getJson(req);
        JsonElement elementObject = JsonParser.parseString(json);
        JsonObject requestJson = elementObject.getAsJsonObject();

        Map<String, String> request = new LinkedHashMap<>();
        request.put("id", requestJson.get("id").getAsString());
        request.put("lectura", requestJson.get("lectura").getAsString());
        request.put("socio", requestJson.get("socio").getAsString());
        request.put("estado", "Impaga");

        modelo.setData(request);

        req.setAttribute("modelo", modelo.save());
        vista.apiShowModelo(req, res);
    }

    public void apiFind(HttpServletRequest req, HttpServletResponse res) {
        String json = Middleware.getJson(req);
        JsonElement elementObject = JsonParser.parseString(json);
        JsonObject requestJson = elementObject.getAsJsonObject();
        
        String id;
        id = requestJson.get("id").getAsString();
        req.setAttribute("modelo", modelo.find("id", id));
        vista.apiShowModelo(req, res);
    }
    
    public void apiFindCodSocio(HttpServletRequest req, HttpServletResponse res) {
        String json = Middleware.getJson(req);
        JsonElement elementObject = JsonParser.parseString(json);
        JsonObject requestJson = elementObject.getAsJsonObject();
        
        String cod_socio;
        cod_socio = requestJson.get("cod_socio").getAsString();
        req.setAttribute("combo", modelo.findCodSocio("cod_socio", cod_socio));
        vista.apiShowCombo(req, res);
    }

    public void apiDelete(HttpServletRequest req, HttpServletResponse res) {
        String json = Middleware.getJson(req);
        JsonElement elementObject = JsonParser.parseString(json);
        JsonObject requestJson = elementObject.getAsJsonObject();
        
        String id;
        id = requestJson.get("id").getAsString();
        req.setAttribute("state", modelo.delete(id));
        vista.apiShowMessage(req, res);
    }

    public void apiFindAll(HttpServletRequest req, HttpServletResponse res) {
        req.setAttribute("table", modelo.findAll());
        vista.apiShowList(req, res);
    }
    
    public void apiComboSocio(HttpServletRequest req, HttpServletResponse res) {
        req.setAttribute("combo", modeloSocio.apiCombo());
        vista.apiShowCombo(req, res);
    }
}
