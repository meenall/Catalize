package com.catalize.backend.servlets;

import com.catalize.backend.model.Introduction;
import com.catalize.backend.utils.Config;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.catalize.backend.utils.Util.chat;
import static com.catalize.backend.utils.Util.completeIntro;
import static com.catalize.backend.utils.Util.findIntroudctionbyPhone;
import static com.catalize.backend.utils.Util.sendSMS;

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

    logger.info("Received message from: " + fromNumber + " with body: " + body);
    findIntroudctionbyPhone(toNumber,body,fromNumber);
//    introRef.orderByChild("phone").equalTo(body).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
//      @Override
//      public void onDataChange(DataSnapshot dataSnapshot) {
//        Introduction introduction = null;
//        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
//          introduction = messageSnapshot.getValue(Introduction.class);
//        }
//
//        final Introduction intro = introduction;
//        sendResponse(intro, fromNumber,body);
//
//      }
//
//      @Override
//      public void onCancelled(DatabaseError databaseError) {
//        logger.warning("Db error" + databaseError.getDetails());
//
//      }
//    });
  }

  private void sendResponse(Introduction intro, String fromNumber,String body) {
    if (fromNumber.contains(intro.aContact)) {
      if(intro.aReplied==false ){
        intro.aReplied = true;
        try {
          sendSMS(fromNumber, Config.Text_MESSAGE2,intro.phone);
        } catch (ServletException e) {
          e.printStackTrace();
        }
        if(intro.isComplete()){
          try {
            completeIntro(intro);
          } catch (ServletException e) {
            e.printStackTrace();
          }
        }
      }
      else {
        if(intro.isComplete()){
          chat(intro, body , true);
        }
      }

    } else if (fromNumber.contains(intro.bContact)) {
      if(intro.bReplied==false ){
        intro.bReplied = true;
        try {
          sendSMS(fromNumber, Config.Text_MESSAGE2,intro.phone);
        } catch (ServletException e) {
          e.printStackTrace();
        }
        if(intro.isComplete()){
          try {
            completeIntro(intro);
          } catch (ServletException e) {
            e.printStackTrace();
          }
        }
      }
      else {
        if(intro.isComplete()){
          chat(intro, body , false);
        }
      }
    }
    Map<String, Object> introUpdate = new HashMap<String, Object>();
    introUpdate.put("aReplied",intro.aReplied);
    introUpdate.put("bReplied",intro.aReplied);

    introRef.child(intro.uid).updateChildren(introUpdate);

  }


}