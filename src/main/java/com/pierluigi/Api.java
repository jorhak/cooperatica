package com.pierluigi;

import com.pierluigi.api.consumo.CConsumo;
import com.pierluigi.api.socio.CSocio;
import com.pierluigi.api.factura.CFactura;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Api extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        switch (action) {
            case "/api/socio/delete": {
                CSocio socio = new CSocio();
                socio.apiDelete(req, resp);
                return;
            }
            case "/api/consumo/delete": {
                CConsumo consumo = new CConsumo();
                consumo.apiDelete(req, resp);
                return;
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        switch (action) {
            case "/api/socio/update": {
                CSocio socio = new CSocio();
                socio.apiSave(req, resp);
                return;
            }
            case "/api/consumo/update": {
                CConsumo consumo = new CConsumo();
                consumo.apiSave(req, resp);
                return;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        switch (action) {
            case "/api/socio": {
                CSocio socio = new CSocio();
                socio.apiSave(req, resp);
                return;
            }
            case "/api/consumo": {
                CConsumo consumo = new CConsumo();
                consumo.apiSave(req, resp);
                return;
            }
            case "/api/consumo/findCodSocio": {
                CConsumo consumo = new CConsumo();
                consumo.apiFindCodSocio(req, resp);
                return;
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //resp.sendRedirect(req.getContextPath()+"/template/apiSocio/socio.jsp");
        String action = req.getServletPath();

//        if (action.startsWith("/api")) {
//            CSocio socio = new CSocio();
//            switch (action) {
//                case "/api/socio":
//                    socio.create(req, resp);
//                    return;
//            }
//        }
        if (action.startsWith("/api/socio")) {
            CSocio socio = new CSocio();
            switch (action) {
                case "/api/socio":
                    socio.create(req, resp);
                    return;
                case "/api/socio/find":
                    socio.apiFind(req, resp);
                    return;
                case "/api/socio/findAll":
                    socio.apiFindAll(req, resp);
                    return;

            }
        }

        if (action.startsWith("/api/consumo")) {
            CConsumo consumo = new CConsumo();
            switch (action) {
                case "/api/consumo":
                    consumo.create(req, resp);
                    return;
                case "/api/consumo/find":
                    consumo.apiFind(req, resp);
                    return;
                case "/api/consumo/findAll":
                    consumo.apiFindAll(req, resp);
                    return;

                case "/api/consumo/comboSocio":
                    consumo.apiComboSocio(req, resp);
                    return;
            }
        }

        if (action.startsWith("/api/factura")) {
            CFactura factura = new CFactura();
            switch (action) {
                case "/api/factura":
                    factura.create(req, resp);
                    return;
//                case "/api/factura/find":
//                    factura.apiFind(req, resp);
//                    return;
//                case "/api/factura/findAll":
//                    factura.apiFindAll(req, resp);
//                    return;
//                case "/api/factura/comboSocio":
//                    factura.apiComboSocio(req, resp);
//                    return;
            }
        }
    }

}
