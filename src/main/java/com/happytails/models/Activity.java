package com.happytails.models;

public class Activity {
    String activityName;
    String duration;
    String icon;
    String petID;

    public Activity(String activityName, String duration, String icon, String petID){
        this.activityName = activityName;
        this.duration =  duration;
        this.icon =  icon;
        this.petID = petID;
    }
}
