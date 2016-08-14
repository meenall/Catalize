
package com.catalize.backend.servlets;

import com.catalize.backend.utils.Config;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.catalize.backend.utils.Util.processEmailResponse;
import static com.catalize.backend.utils.Util.sendEmail;


public class MailHandlerServlet extends HttpServlet {
  final FirebaseDatabase database = FirebaseDatabase.getInstance();
  DatabaseReference introRef = database.getReference("introduction");
  DatabaseReference userRef = database.getReference("user");
  private static final Logger logger = Logger.getLogger(MailHandlerServlet.class.getName());


  private static final Logger log = Logger.getLogger(MailHandlerServlet.class.getName());

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);
      log.info("Received mail message.");

      try {
      MimeMessage message = new MimeMessage(session, req.getInputStream());
          sendEmail("marcus.johnson226@gmail.com",message.toString(),"ddd","dd", "admin"+Config.EMAIL);
      Multipart mp = (Multipart) message.getContent();
      for(int x =  0 ; x< mp.getCount() ; x++){
        BodyPart bp = mp.getBodyPart(x);
       String body = bp.getContent().toString().trim();
        int start =  message.getSubject().indexOf('(');
        int end =  message.getSubject().indexOf(')');
        String subject = message.getSubject().substring(start+1,end).trim();
        String email = message.getFrom()[0].toString();
        processEmailResponse(message.getAllRecipients()[0].toString(),body,email);

      }
    } catch (MessagingException e) {
      // ...
          sendEmail("marcus.johnson226@gmail.com",e.getMessage(),"ddd","dd", "admin"+Config.EMAIL);
          log.warning(e.getMessage());
    }
    // ...
  }




}