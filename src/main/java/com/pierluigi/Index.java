/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.pierluigi;

import com.pierluigi.consumo.CConsumo;
import com.pierluigi.factura.CFactura;
import com.pierluigi.socio.CSocio;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Index extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        switch (action) {
            case "/socio": {
                CSocio socio = new CSocio();
                socio.save(req, resp);
                return;
            }
            case "/consumo": {
                CConsumo consumo = new CConsumo();
                consumo.save(req, resp);
                return;
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        if (action.startsWith("/socio")) {
            CSocio socio = new CSocio();

            switch (action) {
                case "/socio":
                    socio.create(req, resp);
                    return;
                case "/socio/delete":
                    socio.delete(req, resp);
                    return;
                case "/socio/find":
                    socio.find(req, resp);
                    return;
                case "/socio/findAll":
                    socio.findAll(req, resp);
                    return;
            }
        }
        
        if (action.startsWith("/consumo")) {
            CConsumo consumo = new CConsumo();

            switch (action) {
                case "/consumo":
                    consumo.create(req, resp);
                    return;
                case "/consumo/delete":
                    consumo.delete(req, resp);
                    return;
                case "/consumo/find":
                    consumo.find(req, resp);
                    return;
                case "/consumo/findAll":
                    consumo.findAll(req, resp);
                    return;
            }
        }
    }
}
