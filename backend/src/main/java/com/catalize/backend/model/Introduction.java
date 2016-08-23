package com.catalize.backend.model;

import com.catalize.backend.utils.Config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Marcus on 8/2/16.
 */
public class Introduction {
    public String uid;
    public String phone;
    public String email;
    public  String body;
    public String subject;
    public String introducerId;
    public String aName;
    public String aContact;
    public boolean aText;
    public boolean aReplied;
    public String bName;
    public String bContact;
    public boolean bReplied;
    public boolean bText;
    public  boolean active;
    public String acceptCode;
    public String date;
    public String completeDate;

    public  boolean expired;


    public Introduction() {
        uid = UUID.randomUUID().toString();
        aReplied = false ;
        bReplied = false;
        aText = false;
        aText = false;
        active = false;
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        this.date = df.format(d);
        phone = "phone";
        int x =uid.lastIndexOf("-")+1;
        email = "intro-"+uid.substring(0,4)+Config.EMAIL;
        expired = false;
    }

    @Override
    public String toString() {
        return "Introduction{" +
                "uid='" + uid + '\'' +
                ", aName='" + aName + '\'' +
                ", aContact='" + aContact + '\'' +
                ", aText=" + aText +
                ", aReplied=" + aReplied +
                ", bName='" + bName + '\'' +
                ", bContact='" + bContact + '\'' +
                ", bReplied=" + bReplied +
                ", bText=" + bText +
                '}';
    }
    public boolean isComplete(){
        return (aReplied && bReplied) ? true : false;
    }
}
