package com.pierluigi.consumo;

import com.pierluigi.utils.Html;
import com.pierluigi.utils.IVista;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VConsumo implements IVista {

    @Override
    public String getTitle() {
        return "Registrar Consumo";
    }

    @Override
    public String linkToFindAndDelete(String id) {
        return Html.linkToFindAndDelete(
                "/cooperativa/consumo/find",
                "/cooperativa/consumo/delete",
                id
        );
    }

    @Override
    public String getTable(List<Map<String, String>> table) {
        String tr = "";
        for (int i = 0; i < table.size(); i++) {
            Map<String, String> row = table.get(i);
            tr += "<tr>";
            tr += "<td>" + row.getOrDefault("id", "0") + "</td>";
            tr += "<td>" + row.getOrDefault("cod_socio", "") + "</td>";
            tr += "<td>" + row.getOrDefault("estado", "") + "</td>";
            tr += "<td>" + linkToFindAndDelete(row.getOrDefault("id", "0")) +
                    "</td>";
            tr += "</tr>";
        }
        return tr;
    }

    @Override
    public void show(HttpServletRequest req, HttpServletResponse res,
            String template) {
        Map<String, String> modelo = (Map<String, String>) req.getAttribute(
                "modelo");
        List<Map<String, String>> table = (List<Map<String, String>>) req.
                getAttribute("table");
        Map<String, String> comboSocio = (Map<String, String>) req.getAttribute(
                "comboSocio");

        if (modelo != null) {
//      Save
            req.setAttribute("lectura", modelo.getOrDefault("lectura", "0"));
            req.setAttribute("socios", modelo.getOrDefault("socios", "0"));

//      Find
            req.setAttribute("lectura", modelo.getOrDefault("Mfin", "0"));
            req.setAttribute("socio", modelo.getOrDefault("codigo", "0"));
//            
            req.setAttribute("id", modelo.getOrDefault("id", "0"));

        } else {
            req.setAttribute("socio", "");
        }
        

        String socio = getCod_socio(comboSocio, req.getAttribute("socio").toString());
        req.setAttribute("socios", socio);

        req.setAttribute("table", getTable(table));
        req.setAttribute("title", getTitle());

        try {
            req.getRequestDispatcher(template).forward(req, res);
        } catch (Exception e) {
        }
    }

    private String getCod_socio(Map<String, String> data, String selected) {
        return Html.getComboBox(data, selected);
    }
}
