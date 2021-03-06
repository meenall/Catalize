
package com.catalize.backend.servlets;

import com.catalize.backend.utils.Config;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.catalize.backend.utils.Util.findByEmail2;
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
      Multipart mp = (Multipart) message.getContent();
      for(int x =  0 ; x< mp.getCount() ; x++){
        BodyPart bp = mp.getBodyPart(x);
       String body = getTextFromMimeMultipart(mp);

        int start =  message.getSubject().indexOf('(');
        int end =  message.getSubject().indexOf(')');
//        String subject = message.getSubject().substring(start+1,end).trim();
        String email = message.getFrom()[0].toString();
          String to = message.getAllRecipients()[0].toString();

         // processEmailResponse(message.getAllRecipients()[0].toString(),body,email);

          findByEmail2(message.getAllRecipients()[0].toString(),body,email);
//          Queue queue = QueueFactory.getDefaultQueue();
//          queue.add(TaskOptions.Builder.withUrl("/emailtask")
//                  .param("to",to )
//                  .param("from",email).param("body",body));

      }
    } catch (MessagingException e) {
      // ...
          sendEmail("marcus.johnson226@gmail.com",e.getMessage(),"error", "admin"+Config.EMAIL);
          log.warning(e.getMessage());
    } catch (Exception e) {
          e.printStackTrace();
          sendEmail("marcus.johnson226@gmail.com",e.getMessage(),"error", "admin"+Config.EMAIL);

      }
      // ...
  }


    private String getTextFromMimeMultipart(
            Multipart mimeMultipart) throws Exception{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }

}