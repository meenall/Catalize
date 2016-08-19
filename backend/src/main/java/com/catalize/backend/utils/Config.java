package com.catalize.backend.utils;

public class Config {
    public final  static String TWILIO_ACCOUNT_SID =System.getProperty("TWILIO_ACCOUNT_SID");
    public final  static String TWILIO_AUTH_TOKEN =System.getProperty("TWILIO_AUTH_TOKEN");
    public final  static String SEND_GRID_USER =System.getProperty("SEND_GRID_USER");
    public final  static String SEND_GRID_PASSWORD =System.getProperty("SEND_GRID_PASSWORD");
    public final static  int MAX_NUMBERS = 10;
    public final  static String ERROR_EMAIL = "marcus.johnson226@gmail.com";
    public final  static String EMAIL = "@mail.catalizeapp.com";

    public final static  String EMAIL_SUBJECT  = "Catalize Introduction ( %s )";
    public final static  String CHAT_SUBJECT  = "Chat from Introduction ( %s )";
    public final static  String ACCEPT_SUBJECT  = "Introduction  Accepted ( %s )";
    public final static  String EXPIRED_SUBJECT  = "Introduction  Expired ( %s )";



    public  final  static  String EMAIL_MESSAGE1 = "Hi! You've received an introduction from %s to %s through Catalize, the networking and \n" +
            "connections app:\n" +
            "\n" +
            "%s\n" +
            "\n" +
            "To accept this introduction to your new connection, %s, simply reply to this message with YES . This introduction will\n" +
            "expire in 3 days. Learn more at catalizeapp.com. Happy connecting!";
    public final  static  String EMAIL_MESSAGE2 = "You have accepted this introduction.";

    public final  static  String TEXT_MESSAGE1 = "Hi! You've received an introduction from %s to %s through Catalize, the networking and \n" +
            "connections app:\n" +
            "\n" +
            "%s\n" +
            "\n" +
            "To accept this introduction to your new connection, %s, simply reply to this message with YES. This introduction will\n" +
            "expire in 3 days. Learn more at catalizeapp.com. Happy connecting!";
    public final static String Text_MESSAGE2 = "You have accepted this introduction.";

    public final static String EXPIRED_MESSAGE =" Your introduction has expired. Thank you for using Catalize";
    public final static String ACCEPTED_MESSAGE =" Your already accepted this introduction .";

    public static final String WAITING_MESSAGE ="Waiting on response from the other party." ;
    public static final String COMPLETE_MESSAGE ="Both parties have accepted this introduction. Reply to this message to continue the conversation. This chat will expire in 7 days. Happy connecting!" ;


    public static final String DAY1_MESSAGE ="You have 2 days to reply to this introduction. Reply YES to continue the conversatio" ;
    public static final String DAY2_MESSAGE ="You have 1 day to reply to this introduction. Reply YES to continue the conversatio";

}