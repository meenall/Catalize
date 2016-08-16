package com.catalize.backend.servlets;


import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

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
        String to = null, from  = null, text = null;

        try {
            ServletFileUpload upload = new ServletFileUpload();

            FileItemIterator iterator = upload.getItemIterator(req);
            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();
                String name = item.getFieldName();
                InputStream stream = item.openStream();
                String result = new Scanner(stream,"UTF-8").useDelimiter("\\A").next();


                if(name.equals("to")){
                    to = result;
                }
                else if(name.equals("from")){
                    from = result;
                }
                else if(name.equals("text")){
                    text =result;
                }
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }


        findByEmail2(to,text,from);

    }
}
