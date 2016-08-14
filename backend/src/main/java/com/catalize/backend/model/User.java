package com.catalize.backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Marcus on 8/2/16.
 */
public class User {
    public String uid;
    public String displayName;
    public String email;
    public String phone;
    public Integer introductions;
    public List<String> introList;
    public User() {
        uid = UUID.randomUUID().toString();
        introductions = 0;
        introList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", introductions=" + introductions +
                ", introList=" + introList +
                '}';
    }
}
