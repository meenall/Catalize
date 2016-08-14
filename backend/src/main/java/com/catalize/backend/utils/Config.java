package com.catalize.backend.utils;

public class Config {
    public final  static String TWILIO_ACCOUNT_SID =System.getProperty("TWILIO_ACCOUNT_SID");
    public final  static String TWILIO_AUTH_TOKEN =System.getProperty("TWILIO_AUTH_TOKEN");
    public final static  int MAX_NUMBERS = 10;
    public final  static String ERROR_EMAIL = "marcus.johnson226@gmail.com";
    public final  static String EMAIL = "@catalize-1470601187382.appspotmail.com";

    public final static  String EMAIL_SUBJECT  = "Introduction from %s ( %s )";
    public final static  String CHAT_SUBJECT  = "Chat from Introduction ( %s )";
    public final static  String ACCEPT_SUBJECT  = "Introduction  Accepted ( %s )";
    public final static  String EXPIRED_SUBJECT  = "Introduction  Expired ( %s )";



    public  final  static  String EMAIL_MESSAGE1 = "Hi! You've received an introduction from %s to %s through Catalize, the networking and \n" +
            "connections app:\n" +
            "\n" +
            "\"Text that will be sent from the front-end\"\n" +
            "\n" +
            "To accept this introduction to your new connection, %s, simply reply to this message with YES . This introduction will\n" +
            "expire in 3 days. Learn more at catalizeapp.com. Happy connecting!";
    public final  static  String EMAIL_MESSAGE2 = "You have accepted this introduction.";

    public final  static  String TEXT_MESSAGE1 = "Hi! You've received an introduction from %s to %s through Catalize, the networking and \n" +
            "connections app:\n" +
            "\n" +
            "\"Text that will be sent from the front-end\"\n" +
            "\n" +
            "To accept this introduction to your new connection, %s, simply reply to this message with YES. This introduction will\n" +
            "expire in 3 days. Learn more at catalizeapp.com. Happy connecting!";
    public final static String Text_MESSAGE2 = "You have accepted this introduction.";

    public final static String EXPIRED_MESSAGE =" Your introduction has expired. Thank you for using Catalize";
    public final static String ACCEPTED_MESSAGE =" Your already accepted this introduction .";

    public static final String WAITING_MESSAGE ="Waiting on response from the other party" ;
}