package com.example.prescriptionapp;

//USer class that will store user data to be entered into the firebase.
public class User {
    public String name;
    public String username;
    public String phoneNumber;
    public String password;

    public User(){

    }

    public User(String name, String username, String password, String phoneNumber){
        this.name = name;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

}
