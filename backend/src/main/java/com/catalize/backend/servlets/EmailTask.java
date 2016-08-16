package com.catalize.backend.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.catalize.backend.utils.Util.findByEmail2;

/**
 * Created by Marcus on 8/2/16.
 */
public class EmailTask extends HttpServlet {

    private static final Logger log = Logger.getLogger(EmailTask.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String body = request.getParameter("body");

         findByEmail2(to,body,from);

    }
}
