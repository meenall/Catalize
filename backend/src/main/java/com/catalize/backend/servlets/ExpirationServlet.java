package com.catalize.backend.servlets;

import com.catalize.backend.model.Introduction;
import com.catalize.backend.utils.Config;
import com.catalize.backend.utils.Util;
import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.google.appengine.repackaged.org.joda.time.Period;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExpirationServlet extends HttpServlet {

  final FirebaseDatabase database = FirebaseDatabase.getInstance();
  DatabaseReference introRef = database.getReference("introduction");
  private static final Logger log = Logger.getLogger(ExpirationServlet.class.getName());

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    introRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for(DataSnapshot snap : dataSnapshot.getChildren()){
          log.info("Available "  + dataSnapshot.getKey());

          Introduction intro = snap.getValue(Introduction.class);
          DateTime create = DateTime.parse(intro.date);
          DateTime now = DateTime.now();
          Period p = new Period(create, now);
          int hours = p.getHours();
          if(hours>= 24 && hours<48){
            if(!intro.aReplied){
              try {
                Util.alertContact(intro,intro.aContact, Config.DAY1_MESSAGE);
              } catch (ServletException e) {
                e.printStackTrace();
              }
            }
            if(!intro.bReplied){
              try {
                Util.alertContact(intro,intro.bContact, Config.DAY1_MESSAGE);
              } catch (ServletException e) {
                e.printStackTrace();
              }
            }
          }
          else if(hours>= 48 && hours<72){
            if(!intro.aReplied){
              try {
                Util.alertContact(intro,intro.aContact, Config.DAY2_MESSAGE);
              } catch (ServletException e) {
                e.printStackTrace();
              }

            }
            if(!intro.bReplied){
              try {
                Util.alertContact(intro,intro.bContact, Config.DAY2_MESSAGE);
              } catch (ServletException e) {
                e.printStackTrace();
              }

            }
          }
          else if(hours>= 72 && hours<24*7){
            // expire incomplete
            if(!intro.isComplete()){
              intro.expired = true;
              intro.active = false;
              updateIntro(intro);
              try {
                Util.alertContact(intro,intro.aContact, Config.EXPIRED_MESSAGE);
              } catch (ServletException e) {
                e.printStackTrace();
              } try {
                Util.alertContact(intro,intro.bContact, Config.EXPIRED_MESSAGE);
              } catch (ServletException e) {
                e.printStackTrace();
              }
            }

          }
          if(hours>= 24*7){
            // expire all messages at this point
            intro.expired = true;
            intro.active = false;
            updateIntro(intro);
            try {
              Util.alertContact(intro,intro.aContact, Config.EXPIRED_MESSAGE);
            } catch (ServletException e) {
              e.printStackTrace();
            } try {
              Util.alertContact(intro,intro.bContact, Config.EXPIRED_MESSAGE);
            } catch (ServletException e) {
              e.printStackTrace();
            }
          }



        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });

  }

private  void updateIntro(Introduction introduction){
  Map<String, Object> introUpdate = new HashMap<String, Object>();
  introUpdate.put("expired",introduction.expired);
  introUpdate.put("active",introduction.active);
  introRef.child(introduction.uid).updateChildren(introUpdate);
}

}