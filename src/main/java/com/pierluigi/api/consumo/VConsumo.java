package com.pierluigi.api.consumo;

import com.google.gson.Gson;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VConsumo {

    public void apiShowModelo(HttpServletRequest req, HttpServletResponse res) {
        String json = "";
        res.setContentType("application/json;charset=UTF-8");
        res.setCharacterEncoding("UTF-8");

        Map<String, String> modelo = (Map<String, String>) req.getAttribute(
                "modelo");
        json = modelo != null ? new Gson().toJson(modelo) : "{\"message\": \"ERROR\"}";

        try {
            ServletOutputStream out = res.getOutputStream();
            out.print(json);
            out.flush();
        } catch (Exception e) {
        }
    }

    public void apiShowMessage(HttpServletRequest req, HttpServletResponse res) {
        String json = "";
        res.setContentType("application/json;charset=UTF-8");
        res.setCharacterEncoding("UTF-8");

        boolean state = (boolean) req.getAttribute("state");
        String message = !state ? "ERROR" : "OK";
        json = "{\"message\": \"" + message + "\"}";

        try {
            ServletOutputStream out = res.getOutputStream();
            out.print(json);
            out.flush();
        } catch (Exception e) {
        }
    }

    public void apiShowList(HttpServletRequest req, HttpServletResponse res) {
        res.setContentType("application/json;charset=UTF-8");
        res.setCharacterEncoding("UTF-8");

        List<Map<String, String>> table = (List<Map<String, String>>) req.
                getAttribute("table");
        String json = table != null ? new Gson().toJson(table) : "{\"message\": \"ERROR\"}";

        try {
            ServletOutputStream out = res.getOutputStream();
            out.print(json);
            out.flush();
        } catch (Exception e) {
        }
    }

    public void view(HttpServletRequest req, HttpServletResponse res,
            String template) {
        try {
            String h = "CONSUMO";
            req.setAttribute("titulo", h);
//res.sendRedirect(req.getContextPath()+"/template/apiSocio/socio.jsp");
            req.getRequestDispatcher(template).forward(req, res);
        } catch (Exception e) {
        }

    }

    public void apiShowCombo(HttpServletRequest req, HttpServletResponse res) {
        res.setContentType("application/json;charset=UTF-8");
        res.setCharacterEncoding("UTF-8");

        List<Map<String, String>> combo = (List<Map<String, String>>) req.getAttribute("combo");
        String json = combo != null ? new Gson().toJson(combo) : "{\"message\": \"ERROR\"}";

        try {
            ServletOutputStream out = res.getOutputStream();
            out.print(json);
            out.flush();
        } catch (Exception e) {
        }
    }
}
