package com.pierluigi.utils;

import java.io.BufferedReader;
import javax.servlet.http.HttpServletRequest;

public class Middleware {

    public static String getJson(HttpServletRequest req) {
        StringBuffer jb = new StringBuffer();
        String line = null;

        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            return "{\"state\": \"ERROR READ JSON\"}";
        }

        return jb.toString();
    }
}
