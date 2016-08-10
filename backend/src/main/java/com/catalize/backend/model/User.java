package com.catalize.backend.model;

import java.util.UUID;

/**
 * Created by Marcus on 8/2/16.
 */
public class User {
    public String uid;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    public int introductions;

    public User() {
        uid = UUID.randomUUID().toString();
        introductions = 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", introductions=" + introductions +
                '}';
    }
}
