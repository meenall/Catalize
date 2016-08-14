package com.catalize.backend.servlets;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.catalize.backend.utils.Util.findIntroudctionbyPhone;

public class ReceiveSmsServlet extends HttpServlet {
  final FirebaseDatabase database = FirebaseDatabase.getInstance();
  DatabaseReference introRef = database.getReference("introduction");
  DatabaseReference userRef = database.getReference("user");

  private static final Logger logger = Logger.getLogger(ReceiveSmsServlet.class.getName());


  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws IOException,
          ServletException {
    final String fromNumber = request.getParameter("From");
    final String toNumber = request.getParameter("To");

    final String body = request.getParameter("Body").toLowerCase();

    logger.info("Received message from: " + fromNumber + " with body: " + body +" with req " + request.getParameterMap().toString());
    findIntroudctionbyPhone(toNumber,body,fromNumber);
  }

}