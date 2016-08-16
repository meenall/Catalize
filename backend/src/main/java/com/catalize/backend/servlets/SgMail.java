package com.catalize.backend.servlets;

import com.google.gson.JsonObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.catalize.backend.utils.Util.findByEmail2;

/**
 * Created by Marcus on 8/16/16.
 */

public class SgMail extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String envelope = req.getParameter("envelope");
//        JSONObject jsonObj = new JSONObject(envelope);

        String to = req.getParameter("to");
        String body = req.getParameter("body");
        String from = req.getParameter("from");
//        String from = jsonObj.getString("from");
        JsonObject rep = new JsonObject();
        rep.addProperty("from",from);
        rep.addProperty("body",body);
        rep.addProperty("to",to);

        findByEmail2(to,body,from);
        resp.getWriter().print(rep.toString());

    }
}
