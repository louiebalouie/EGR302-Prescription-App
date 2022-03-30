package com.example.prescriptionapp;
import com.google.firebase.firestore.*;

//USer class that will store user data to be entered into the firebase.
@IgnoreExtraProperties
public class User {
    public String name;
    public String username;
    public int phoneNumber;
    public int password;

    public User(){

    }

    public User(String name, String username, String password, int phoneNumber){
        this.name = name;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password.hashCode();
    }

}
