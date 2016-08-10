package com.catalize.backend.utils;

import com.catalize.backend.model.Introduction;
import com.catalize.backend.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
        String regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        //System.out.println(email +" : "+ matcher.matches());
        //If phone number is correct then format it to (123)-456-7890
        if (matcher.matches()) {
            return matcher.replaceFirst("+1$1$2$3");
        }

        return phone;
    }

    public static void sendEmail(String aContact, String aBody, String aName, String subject) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setSubject(subject);
            msg.setFrom(new InternetAddress(Config.EMAIL, "Catalize Admin"));

            msg.addRecipient(javax.mail.Message.RecipientType.TO,
                    new InternetAddress(aContact, aName));
            Multipart mp = new MimeMultipart();

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(aBody, "text/plain");
            mp.addBodyPart(htmlPart);
            msg.setContent(mp);
            Transport.send(msg);
            logger.info("Email sent");
        } catch (AddressException e) {
            logger.info("Error : " + e.getLocalizedMessage());
            // ...
        } catch (MessagingException e) {
            logger.info("Error : " + e.getLocalizedMessage());

            // ...
        } catch (UnsupportedEncodingException e) {
            // ...
            logger.info("Error : " + e.getLocalizedMessage());

        }
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
        String subject = String.format(Config.CHAT_SUBJECT, introduction.acceptCode);

        if (isA) {
            if (introduction.bText) {
                try {
                    sendSMS(introduction.bContact, body, introduction.phone);
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            } else {
                sendEmail(introduction.bContact, body, introduction.bName, subject);
            }
        } else {
            if (introduction.aText) {
                try {
                    sendSMS(introduction.aContact, body, introduction.phone);
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            } else {
                sendEmail(introduction.aContact, body, introduction.aName, subject);
            }
        }
    }

    public static void completeIntro(final Introduction intro) throws ServletException {

        userRef.child(intro.introducerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    String subject = String.format(Config.EMAIL_SUBJECT, user.firstName + " " + user.lastName, intro.acceptCode);

                    if (user.phone != null && user.phone.length() > 0) {
                        try {
                            sendSMS(user.phone, intro.aName + " has been introduced to " + intro.bName, intro.phone);
                        } catch (ServletException e) {
                            e.printStackTrace();
                        }
                    } else if (user.email != null && user.email.length() > 0) {
                        sendEmail(user.email, intro.aName + " has been introduced to " + intro.bName, user.firstName, subject);
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
                    logger.info("Aviable Number query" + dataSnapshot.getKey());

                    Introduction introduction = snap.getValue(Introduction.class);

                    if (introduction.aContact.contains(intro.aContact)) {
                        map.put(introduction.uid, introduction.phone);
                    }
                    if (introduction.aContact.contains(intro.bContact)) {
                        map.put(introduction.uid, introduction.phone);
                    }
                    if (introduction.bContact.contains(intro.aContact)) {
                        map.put(introduction.uid, introduction.phone);
                    }
                    if (introduction.bContact.contains(intro.bContact)) {
                        map.put(introduction.uid, introduction.phone);
                    }
                }
                IncomingPhoneNumberList numbers = client.getAccount().getIncomingPhoneNumbers();
                List<IncomingPhoneNumber> list = numbers.getPageData();
                boolean foundNumber = false;
                for (IncomingPhoneNumber number : list) {
                    if (map.get(number.getPhoneNumber()) == null) {
                        intro.phone = number.getPhoneNumber();
                        Map<String, Object> introUpdate = new HashMap<String, Object>();
                        introUpdate.put("phone", intro.phone);
                        introRef.child(intro.uid).updateChildren(introUpdate);
                        foundNumber = true;
                        break;
                    }
                }
                if (!foundNumber) {
                    if (client.getAccount().getIncomingPhoneNumbers().getPageData().size() < Config.MAX_NUMBERS) {
                        intro.phone = buyNumber("678");
                        Map<String, Object> introUpdate = new HashMap<String, Object>();
                        introUpdate.put("phone", intro.phone);
                        introRef.child(intro.uid).updateChildren(introUpdate);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void expiredIntro(Introduction intro) throws ServletException {
        String subject = String.format(Config.EXPIRED_SUBJECT, intro.acceptCode);
        if (intro.aText) {
            sendSMS(intro.aContact, Config.EXPIRED_MESSAGE, intro.phone);
        } else {
            sendEmail(intro.aContact, Config.EXPIRED_MESSAGE, intro.aName, subject);
        }
        if (intro.bText) {
            sendSMS(intro.bContact, Config.EXPIRED_MESSAGE, intro.phone);
        } else {
            sendEmail(intro.bContact, Config.EXPIRED_MESSAGE, intro.aName, subject);

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
        try {
            Map<String, String> buyParams = new HashMap<>();
            buyParams.put("PhoneNumber", phoneNumber);
            account.getIncomingPhoneNumberFactory().create(buyParams);

            IncomingPhoneNumber number = phoneNumberFactory.create(buyParams);

            return number.getPhoneNumber();

        } catch (TwilioRestException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void sendIntroduction(final Introduction introduction) throws ServletException {
        userRef.child(introduction.introducerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String subject = String.format(Config.EMAIL_SUBJECT, user.firstName + " " + user.lastName, introduction.acceptCode);
                if (introduction.aText) {
                    try {
                        String text = String.format(Config.TEXT_MESSAGE1, user.firstName + " " + user.lastName, introduction.bName, introduction.bName);
                        sendSMS(introduction.aContact, text, introduction.phone);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    }
                } else {
                    String email = String.format(Config.EMAIL_MESSAGE1, user.firstName + " " + user.lastName, introduction.bName, introduction.bName);
                    sendEmail(introduction.aContact, email, introduction.aName, subject);
                }
                if (introduction.bText) {
                    try {
                        String text = String.format(Config.TEXT_MESSAGE1, user.firstName + " " + user.lastName, introduction.aName, introduction.aName);

                        sendSMS(introduction.bContact, text, introduction.phone);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    }

                } else {
                    String email = String.format(Config.EMAIL_MESSAGE1, user.firstName + " " + user.lastName, introduction.aName, introduction.aName);

                    sendEmail(introduction.bContact, email, introduction.bName, subject);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static  void processEmailResponse(String acceptCode, final  String reply , final String email) {
        introRef.orderByChild("acceptCode").equalTo(acceptCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Introduction itroduction = null;
                String name = "Introduction";
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    itroduction = messageSnapshot.getValue(Introduction.class);
                }

                final Introduction intro = itroduction;
                String subject = String.format(Config.ACCEPT_SUBJECT, intro.acceptCode);
                if(intro.isComplete()){
                    if(intro.aContact.equals(email)){
                        chat(intro, reply, true);

                    }
                    else  if(intro.bContact.equals(email)){
                        chat(intro, reply, false);

                    }
                }
                else if(reply.toLowerCase().contains("yes")){
                    try {
                        acceptIntro(intro,email);
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

    private static void acceptIntro(Introduction intro, String contact) throws ServletException {
        String subject = String.format(Config.CHAT_SUBJECT, intro.acceptCode);

        if(intro.aContact.equals(contact)){
            if(intro.aReplied){
                if(intro.aText){
                    //send already acceptance text

                }
                else {
                    //send  already acceptance email
                }

            }
            else {
                intro.aReplied = true;
                if(intro.aText){
                    //send acceptance text
                    sendSMS(intro.aContact,Config.ACCEPTED_MESSAGE,intro.phone);

                }
                else {
                    //send acceptance email
                    sendEmail(intro.aContact, Config.EMAIL_MESSAGE2, intro.aName, subject);

                }
                if (intro.isComplete()) {
                    try {
                        completeIntro(intro);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        else if(intro.bContact.equals(contact)){
            if(intro.bReplied){
                if(intro.bText){
                    //send already acceptance text
                    sendSMS(intro.bContact,Config.ACCEPTED_MESSAGE,intro.phone);
                }
                else {
                    //send  already acceptance email
                    sendEmail(intro.bContact,Config.ACCEPTED_MESSAGE,intro.bName,subject);
                }

            }
            else {
                intro.bReplied = true;
                if(intro.bText){
                    //send acceptance text
                    sendSMS(contact,Config.ACCEPTED_MESSAGE,intro.phone);

                }
                else {
                    //send acceptance email
                    sendEmail(intro.bContact, Config.EMAIL_MESSAGE2, intro.bName, subject);

                }
                if (intro.isComplete()) {
                    try {
                        completeIntro(intro);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Map<String, Object> introUpdate = new HashMap<String, Object>();
        introUpdate.put("aReplied", intro.aReplied);
        introUpdate.put("bReplied", intro.aReplied);

        introRef.child(intro.uid).updateChildren(introUpdate);
    }

    public static void findIntroudctionbyPhone(String accountPhone,final  String reply ,final String contactPhone) {
        introRef.orderByChild("phone").equalTo(accountPhone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Introduction introduction = null;
                String name = "Introduction";
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    if(introduction.aContact.equals(contactPhone)
                            || introduction.bContact.equals(contactPhone)) {
                        introduction = messageSnapshot.getValue(Introduction.class);
                    }
                }

                final Introduction intro = introduction;
                if(intro.isComplete()){
                    if(intro.aContact.equals(contactPhone)){
                        chat(intro, reply, true);

                    }
                    else  if(intro.bContact.equals(contactPhone)){
                        chat(intro, reply, false);

                    }
                }
                else if(reply.toLowerCase().equals("yes")){
                    try {
                        acceptIntro(intro,contactPhone);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    }
                }

//                if (contactPhone.contains(intro.aContact)) {
//                    if(intro.aReplied==false ){
//                        intro.aReplied = true;
//                        try {
//                            sendSMS(contactPhone, Config.Text_MESSAGE2,intro.phone);
//                        } catch (ServletException e) {
//                            e.printStackTrace();
//                        }
//                        if(intro.isComplete()){
//                            try {
//                                completeIntro(intro);
//                            } catch (ServletException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                    else {
//                        if(intro.isComplete()){
//                            chat(intro, reply , true);
//                        }
//                    }
//
//                } else if (contactPhone.contains(intro.bContact)) {
//                    if(intro.bReplied==false ){
//                        intro.bReplied = true;
//                        try {
//                            sendSMS(contactPhone, Config.Text_MESSAGE2,intro.phone);
//                        } catch (ServletException e) {
//                            e.printStackTrace();
//                        }
//                        if(intro.isComplete()){
//                            try {
//                                completeIntro(intro);
//                            } catch (ServletException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                    else {
//                        if(intro.isComplete()){
//                            chat(intro, reply , false);
//                        }
//                    }
//                }
//                Map<String, Object> introUpdate = new HashMap<String, Object>();
//                introUpdate.put("aReplied",intro.aReplied);
//                introUpdate.put("bReplied",intro.aReplied);
//
//                introRef.child(intro.uid).updateChildren(introUpdate);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                logger.warning("Db error" + databaseError.getDetails());

            }
        });

    }




}

