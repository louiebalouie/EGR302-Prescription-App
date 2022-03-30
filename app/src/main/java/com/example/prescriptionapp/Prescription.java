package com.example.prescriptionapp;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.firestore.*;
import java.time.LocalTime;


//Prescription class that will store prescription data to be entered into the firebase.
@IgnoreExtraProperties
public class Prescription {
    public String prescriptName;
    public LocalTime lastTaken;
    public LocalTime nextDose;
    public double timeBetweenDoses;

    public Prescription(){

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Prescription(String name, LocalTime time, double dosageTime){
        this.prescriptName = name;
        this.lastTaken = time;
        this.timeBetweenDoses = dosageTime;
        long minsBetweenDoses = (long) (dosageTime * 60);
        this.nextDose = lastTaken.plusMinutes(minsBetweenDoses);
    }
}
