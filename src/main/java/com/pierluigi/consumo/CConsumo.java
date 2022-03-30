package com.pierluigi.consumo;

import com.pierluigi.socio.MSocio;
import com.pierluigi.utils.IControlador;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CConsumo implements IControlador {

    private final MConsumo modelo;
    private final MSocio modeloS;
    private final VConsumo vista;

    public CConsumo() {
        this.modelo = new MConsumo();
        this.vista = new VConsumo();
        this.modeloS = new MSocio();
    }

    @Override
    public void create(HttpServletRequest req, HttpServletResponse res) {
        req.setAttribute("table", modelo.findAll());
        req.setAttribute("comboSocio", modeloS.comboBox());
        vista.show(req, res, "/template/consumo/consumo.jsp");
    }

    @Override
    public void save(HttpServletRequest req, HttpServletResponse res) {
        Map<String, String> request = new LinkedHashMap<>();
        request.put("lectura", req.getParameter("lectura"));
        request.put("socios", req.getParameter("socios"));
        request.put("id", req.getParameter("id"));

        modelo.setData(request);

        req.setAttribute("modelo", modelo.save());
        req.setAttribute("table", modelo.findAll());
        req.setAttribute("comboSocio", modeloS.comboBox());
        vista.show(req, res, "/template/consumo/consumo.jsp");
    }

    @Override
    public void delete(HttpServletRequest req, HttpServletResponse res) {
        boolean state = modelo.delete(req.getParameter("codigo"));

        req.setAttribute("state", state);
        req.setAttribute("table", modelo.findAll());
        req.setAttribute("comboSocio", modeloS.comboBox());
        vista.show(req, res, "/template/consumo/consumo.jsp");
    }

    @Override
    public void find(HttpServletRequest req, HttpServletResponse res) {

        req.setAttribute("modelo", modelo.find1(req.getParameter(
                "codigo")));
        req.setAttribute("table", modelo.findAll());
        req.setAttribute("comboSocio", modeloS.comboBox());
        vista.show(req, res, "/template/consumo/consumo.jsp");
    }

    @Override
    public void findAll(HttpServletRequest req, HttpServletResponse res) {
        req.setAttribute("table", modelo.findAll());
        req.setAttribute("comboSocio", modeloS.comboBox());
        vista.show(req, res, "/template/consumo/consumo.jsp");
    }
}


//select (s.nombre, s.apellido) as nombre from socio s, consumo c where s.codigo=c.cod_socio and c.id=9;
