package com.catalize.backend.utils;

import com.catalize.backend.model.Introduction;
import com.catalize.backend.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.IncomingPhoneNumberFactory;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.AvailablePhoneNumber;
import com.twilio.sdk.resource.instance.IncomingPhoneNumber;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.list.AvailablePhoneNumberList;
import com.twilio.sdk.resource.list.IncomingPhoneNumberList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;

/**
 * Created by Marcus on 4/24/16.
 */
public class Util {
    private static final Logger logger = Logger.getLogger(Util.class.getName());
    final static FirebaseDatabase database = FirebaseDatabase.getInstance();
    final static TwilioRestClient client = new TwilioRestClient(Config.TWILIO_ACCOUNT_SID, Config.TWILIO_AUTH_TOKEN);
    final static Account account = client.getAccount();


    final static DatabaseReference introRef = database.getReference("introduction");
    final static DatabaseReference userRef = database.getReference("user");

    public static String formatNumber(String phone) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber swissNumberProto = phoneUtil.parse(phone, "US");
            return phoneUtil.format(swissNumberProto, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        return phone;
    }

    public static void sendEmail(String aContact, String aBody,  String subject, String email) {
        Sendgrid mail = new Sendgrid(Config.SEND_GRID_USER,Config.SEND_GRID_PASSWORD);

// set email data
        mail.setTo(aContact).setFrom(email).setSubject(subject).setText(aBody).setFromName("Catalize App");

// send your message
        mail.send();
//        Properties props = new Properties();
//        Session session = Session.getDefaultInstance(props, null);
//
//        try {
//            MimeMessage msg = new MimeMessage(session);
//            msg.setSubject(subject);
//            msg.setFrom(new InternetAddress(email, "Catalize Admin"));
//
//            msg.addRecipient(javax.mail.Message.RecipientType.TO,
//                    new InternetAddress(aContact, aName));
//            Multipart mp = new MimeMultipart();
//
//            MimeBodyPart htmlPart = new MimeBodyPart();
//            htmlPart.setContent(aBody, "text/plain");
//            mp.addBodyPart(htmlPart);
//            msg.setContent(mp);
//            Transport.send(msg);
//            logger.info("Email sent");
//        } catch (AddressException e) {
//            logger.info("Error : " + e.getLocalizedMessage());
//            // ...
//        } catch (MessagingException e) {
//            logger.info("Error : " + e.getLocalizedMessage());
//
//            // ...
//        } catch (UnsupportedEncodingException e) {
//            // ...
//            logger.info("Error : " + e.getLocalizedMessage());
//
//        }
    }

    public static void sendSMS(String aContact, String aBody, String phone) throws ServletException {
        MessageFactory messageFactory = account.getMessageFactory();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", aContact));
        params.add(new BasicNameValuePair("From", phone));
        params.add(new BasicNameValuePair("Body", aBody));
        try {
            Message sms = messageFactory.create(params);
            logger.info("Sms Sent: " + sms.getBody());
        } catch (TwilioRestException e) {
            throw new ServletException("Twilio error", e);
        }
    }

    public static void chat(Introduction introduction, String body, boolean isA) {
        String  subject = introduction.acceptCode;

        if (isA) {
            if (introduction.bText) {
                try {
                    sendSMS(introduction.bContact, body, introduction.phone);
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            } else {
                sendEmail(introduction.bContact, body, subject,introduction.email);
            }
        } else {
            if (introduction.aText) {
                try {
                    sendSMS(introduction.aContact, body, introduction.phone);
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            } else {
                sendEmail(introduction.aContact, body, subject,introduction.email);
            }
        }
    }

    public static void completeIntro(final Introduction intro) throws ServletException {
        String subject = intro.subject+ " ( "+intro.acceptCode+" )";

        if(intro.aText){
            sendSMS(intro.aContact,"You can now begin chatting", intro.phone);
        }
        else {
            sendEmail(intro.aContact,"You can now begin chatting",subject,intro.email);
        }
        if(intro.bText){
            sendSMS(intro.bContact,"You can now begin chatting", intro.phone);

        }
        else {
            sendEmail(intro.bContact,"You can now begin chatting",subject,intro.email);

        }

        userRef.child(intro.introducerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    String subject = intro.subject+ " ( "+intro.acceptCode+" )";

                    if (user.phone != null && user.phone.length() > 0) {
                        try {
                            sendSMS(user.phone, intro.aName + " has been introduced to " + intro.bName, intro.phone);
                        } catch (ServletException e) {
                            e.printStackTrace();
                        }
                    } else if (user.email != null && user.email.length() > 0) {
                        sendEmail(user.email, intro.aName + " has been introduced to " + intro.bName, subject,intro.email);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                logger.warning("Db error" + databaseError.getDetails());

            }
        });


    }


    public static void findNumber(final Introduction intro) {

        introRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap map = new HashMap();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                    Introduction introduction = snap.getValue(Introduction.class);
                    logger.info("Find Number Loop : Intro: " + introduction.toString());


                    if (introduction.aContact.contains(intro.aContact)) {
                        logger.info("Added number to no list " + intro.phone);

                        map.put(introduction.phone, introduction.phone);
                    }
                    if (introduction.aContact.contains(intro.bContact)) {
                        logger.info("Added number to no list " + intro.phone);

                        map.put(introduction.phone, introduction.phone);
                    }
                    if (introduction.bContact.contains(intro.aContact)) {
                        logger.info("Added number to no list " + intro.phone);

                        map.put(introduction.phone, introduction.phone);
                    }
                    if (introduction.bContact.contains(intro.bContact)) {
                        logger.info("Added number to no list " + intro.phone);

                        map.put(introduction.phone, introduction.phone);
                    }
                }
                IncomingPhoneNumberList numbers = client.getAccount().getIncomingPhoneNumbers();
                List<IncomingPhoneNumber> list = numbers.getPageData();
                boolean buyNumber = true;
                for (IncomingPhoneNumber number : list) {
                    logger.info("Looking for owned   number");

                    if (map.get(number.getPhoneNumber()) == null) {
                        logger.info("Found  number " + number.getPhoneNumber());

                        intro.phone = number.getPhoneNumber();
                        Map<String, Object> introUpdate = new HashMap<String, Object>();
                        introUpdate.put("phone", intro.phone);
//                        introRef.child(intro.uid).updateChildren(introUpdate);
                        buyNumber = false;
                        break;
                    }
                }
                if (buyNumber) {
                    logger.info("Buying new number");
                    if (client.getAccount().getIncomingPhoneNumbers().getPageData().size() < Config.MAX_NUMBERS) {
                        intro.phone = buyNumber("678");
                    }
                }
                try {
                    sendIntroduction(intro);
                } catch (ServletException e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void expiredIntro(Introduction intro) throws ServletException {
        String subject = intro.subject+ " ( "+intro.acceptCode+" )";
        if (intro.aText) {
            sendSMS(intro.aContact, Config.EXPIRED_MESSAGE, intro.phone);
        } else {
            sendEmail(intro.aContact, Config.EXPIRED_MESSAGE,  subject,intro.email);
        }
        if (intro.bText) {
            sendSMS(intro.bContact, Config.EXPIRED_MESSAGE, intro.phone);
        } else {
            sendEmail(intro.bContact, Config.EXPIRED_MESSAGE,  subject,intro.email);

        }
    }

    public static String buyNumber(String areaCode) {

        IncomingPhoneNumberFactory phoneNumberFactory = account.getIncomingPhoneNumberFactory();

        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("AreaCode", areaCode);
        searchParams.put("SmsEnabled", String.valueOf(true));
        searchParams.put("VoiceEnabled", String.valueOf(false));

        List<AvailablePhoneNumber> availableNumbersForGivenArea = getAvailablePhoneNumbers(account, searchParams);

        if (availableNumbersForGivenArea.size() > 0) {
            String number = buyNumber(account, phoneNumberFactory, availableNumbersForGivenArea.get(0).getPhoneNumber());
            if (number != null)
                return number;
        } else {
            searchParams.remove("AreaCode");
            List<AvailablePhoneNumber> generalAvailableNumbers = getAvailablePhoneNumbers(account, searchParams);

            if (generalAvailableNumbers.size() > 0) {
                String number = buyNumber(account, phoneNumberFactory, generalAvailableNumbers.get(0).getPhoneNumber());
                if (number != null)
                    return number;
            }
        }
        return null;
    }

    private static List<AvailablePhoneNumber> getAvailablePhoneNumbers(Account account, Map<String, String> searchParams) {
        AvailablePhoneNumberList phoneNumbers = account.getAvailablePhoneNumbers(searchParams, "US", "Local");
        return phoneNumbers.getPageData();
    }

    private static String buyNumber(Account account, IncomingPhoneNumberFactory phoneNumberFactory, String phoneNumber) {
       String brought = "";
        try {
            Map<String, String> buyParams = new HashMap<>();
            buyParams.put("PhoneNumber", phoneNumber);
            buyParams.put("SmsUrl", "http://catalize-1470601187382.appspot.com/sms");
            account.getIncomingPhoneNumberFactory().create(buyParams);

            IncomingPhoneNumber number = phoneNumberFactory.create(buyParams);

            brought=  number.getPhoneNumber();


        } catch (TwilioRestException e) {
            e.printStackTrace();
        }
        return brought;
    }

    public static void sendIntroduction(final Introduction introduction) throws ServletException {
        userRef.child(introduction.introducerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final User user = dataSnapshot.getValue(User.class);

                sendMessages(user, introduction);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private static void sendMessages(User user, Introduction introduction) {
        introduction.active = true;
        introRef.child(introduction.uid).setValue(introduction);
        String subject = introduction.subject+ " ( "+introduction.acceptCode+" )";
        if (introduction.aText) {
            try {
                String text = String.format(Config.TEXT_MESSAGE1, user.displayName,introduction.aName, introduction.body, introduction.bName);
                sendSMS(introduction.aContact, text, introduction.phone);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        } else {
            String email = String.format(Config.EMAIL_MESSAGE1, user.displayName,   introduction.aName, introduction.body, introduction.aName);
            sendEmail(introduction.aContact, email, subject,introduction.email);
        }
        if (introduction.bText) {
            try {
                String text = String.format(Config.TEXT_MESSAGE1, user.displayName,introduction.aName, introduction.body, introduction.aName);

                sendSMS(introduction.bContact, text, introduction.phone);
            } catch (ServletException e) {
                e.printStackTrace();
            }

        } else {
            String email = String.format(Config.EMAIL_MESSAGE1, user.displayName, introduction.aName, introduction.body,introduction.aName);

            sendEmail(introduction.bContact, email, subject,introduction.email);
        }
    }



    public static  void processEmailResponse(final String acceptCode, final  String reply , final String email) {
        ValueEventListener listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Introduction intro = null;
                String name = "Introduction";
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    intro = messageSnapshot.getValue(Introduction.class);
                }
                if (intro == null) {
                    return;
                }

                if (intro.isComplete()) {
                    if (intro.aContact.equals(email)) {
                        chat(intro, intro.aName + " said: " + reply, true);

                    } else if (intro.bContact.equals(email)) {
                        chat(intro, intro.bName + " said: " + reply, false);

                    }
                } else {

                    try {
                        alertContact(intro, email, reply);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    }
                    String subject = intro.subject+ " ( "+intro.acceptCode+" )";

                    if(intro.aContact.contains(email)){
                       sendEmail(intro.aContact,Config.EMAIL_MESSAGE2,subject,intro.email);
                    }
                    else if(intro.bContact.contains(email)){
                        sendEmail(intro.bContact,Config.EMAIL_MESSAGE2,subject,intro.email);

                    }

                }



            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                logger.warning("Db error" + databaseError.getDetails());

            }
        };

        introRef.orderByChild("email").equalTo(acceptCode).limitToFirst(1).addListenerForSingleValueEvent(listener);

    }

    private static void alertContact( Introduction intro,String contact,String reply) throws ServletException {
        String subject = intro.subject+ " ( "+intro.acceptCode+" )";

        if(intro.aContact.contains(contact)){
             if(intro.aReplied){

                 //intro not complete, send wait message
                 if(intro.aText){
                     sendSMS(intro.aContact,Config.WAITING_MESSAGE,intro.phone);
                 }
                 else {
                     sendEmail(intro.aContact,Config.WAITING_MESSAGE,subject,intro.email);
                 }
             }
             else if(reply.toLowerCase().contains("yes")){
                 try {
                     acceptIntro(intro,intro.aContact);
                 } catch (ServletException e) {
                     e.printStackTrace();
                 }
                 //see if he wants to accept

             }
        }
        else if(intro.bContact.contains(contact)){
            if(intro.bReplied){

                //intro not complete, send wait message
                if(intro.bText){
                    sendSMS(intro.bContact,Config.WAITING_MESSAGE,intro.phone);
                }
                else {
                    sendEmail(intro.bContact,Config.WAITING_MESSAGE,subject,intro.email);
                }
            }
            else if(reply.toLowerCase().contains("yes")){
                try {
                    acceptIntro(intro,intro.bContact);
                } catch (ServletException e) {
                    e.printStackTrace();
                }
                //see if he wants to accept

            }

        }


    }

    private static void acceptIntro(final Introduction intro, String contact) throws ServletException {
        final String subject = intro.subject+ " ( "+intro.acceptCode+" )";


        if(contact.contains(intro.aContact)){
            if(intro.aReplied){
                if(intro.aText){
                    //send already acceptance text
                    sendSMS(contact,Config.ACCEPTED_MESSAGE,intro.phone);
                }
                else {
                    //send  already acceptance email
                    sendEmail(contact,Config.ACCEPTED_MESSAGE,subject,intro.email);

                }

            }
            else {
                /// haven't accepted
                intro.aReplied = true;
                if(intro.aText){
                    //send acceptance text
                    sendSMS(intro.aContact,Config.Text_MESSAGE2,intro.phone);


                }
                else {
                    //send acceptance email
                    sendEmail(contact, Config.EMAIL_MESSAGE2, subject,intro.email);

                }

            }

        }
        else if(contact.contains(intro.bContact)){
            if(intro.bReplied){
                if(intro.bText){
                    //send already acceptance text
                    sendSMS(contact,Config.ACCEPTED_MESSAGE,intro.phone);
                }
                else {
                    //send  already acceptance email
                    sendEmail(contact,Config.ACCEPTED_MESSAGE,subject,intro.email);
                }

            }
            else {
                intro.bReplied = true;
                if(intro.bText){
                    //send acceptance text
                    sendSMS(contact,Config.Text_MESSAGE2,intro.phone);


                }
                else {
                    //send acceptance email
                    sendEmail(intro.bContact, Config.EMAIL_MESSAGE2, subject,intro.email);


                }

            }
        }

        Map<String, Object> introUpdate = new HashMap<String, Object>();
        introUpdate.put("aReplied", intro.aReplied);
        introUpdate.put("bReplied", intro.bReplied);
        final Introduction updated = intro;

        introRef.child(intro.uid).updateChildren(introUpdate, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(updated.aReplied && updated.bReplied){
                    try {
                        completeIntro(updated);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    }
                }
                else{
    /// b didnt reply
                   if(!updated.bReplied) {
                       if(updated.bText){
                           try {
                               sendSMS(updated.bContact,"Waiting for your reply",updated.phone);
                           } catch (ServletException e) {
                               e.printStackTrace();
                           }
                       }
                       else {
                            sendEmail(updated.bContact,"Waiting for your reply",subject, intro.email);
                        }
                       if(updated.aText){
                           try {
                               sendSMS(updated.aContact,"Waiting  for "+intro.bName+" to reply",updated.phone);
                           } catch (ServletException e) {
                               e.printStackTrace();
                           }
                       }
                       else {
                           sendEmail(updated.aContact,"Waiting  for "+intro.bName+" to reply",subject,intro.email);
                       }
                   }
                   /// a didnt reply
                    else if(!updated.aReplied) {
                       if(updated.aText){
                           try {
                               sendSMS(updated.aContact,"Waiting for your reply",updated.phone);
                           } catch (ServletException e) {
                               e.printStackTrace();
                           }
                       }
                       else {
                           sendEmail(updated.aContact,"Waiting for your reply",subject, intro.email);
                       }
                       if(updated.bText){
                           try {
                               sendSMS(updated.bContact,"Waiting  for "+intro.aName+" to reply",updated.phone);
                           } catch (ServletException e) {
                               e.printStackTrace();
                           }
                       }
                       else {
                           sendEmail(updated.bContact,"Waiting  for "+intro.aName+" to reply",subject,intro.email);
                       }

                   }
                }
            }
        });
    }

    public static void findIntroudctionbyPhone(String accountPhone,final  String reply ,final String contactPhone) {
        introRef.orderByChild("phone").equalTo(accountPhone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Introduction introduction = null;
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Introduction temp = messageSnapshot.getValue(Introduction.class);
                    if(temp.aContact.equals(contactPhone)
                            || temp.bContact.equals(contactPhone)) {
                        introduction = temp;
                    }
                }
                if(introduction ==  null){
                    return;
                }

                final Introduction intro = introduction;
                if(intro.isComplete()){
                    if(intro.aContact.equals(contactPhone)){
                        chat(intro, intro.aName+" said: "+reply, true);

                    }
                    else  if(intro.bContact.equals(contactPhone)){
                        chat(intro, intro.bName+" said: "+reply, false);

                    }
                }
                else if(reply.toLowerCase().contains("yes")){
                    try {
                        acceptIntro(intro,contactPhone);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                logger.warning("Db error" + databaseError.getDetails());

            }
        });

    }

    public static void findByEmail2(final String introEmail, final  String reply , final String contactEmail) {
        introRef.orderByChild("email").equalTo(introEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Introduction introduction = null;
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Introduction temp = messageSnapshot.getValue(Introduction.class);
                    if(contactEmail.contains(temp.aContact)
                            || contactEmail.contains(temp.bContact)) {
                        introduction = temp;
                    }
                }
                if(introduction ==  null){
                    return;
                }

                final Introduction intro = introduction;
                if(intro.isComplete()){
                    if(contactEmail.contains(intro.aContact)){
                        chat(intro, intro.aName+" said: "+reply, true);

                    }
                    else  if(contactEmail.contains(intro.bContact)){
                        chat(intro, intro.bName+" said: "+reply, false);

                    }
                }
                else if(reply.toLowerCase().contains("yes")){
                    try {
                        acceptIntro(intro,contactEmail);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                logger.warning("Db error" + databaseError.getDetails());

            }
        });

    }


}

