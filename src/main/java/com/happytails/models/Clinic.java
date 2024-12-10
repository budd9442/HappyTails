package com.happytails.models;


public class Clinic {
    private String clinicID;
    private String name;
    private double rating;
    private String address;
    private String joinDate;
    private String locationURL;
    private String availableHours;

    public Clinic() {
    }

    // Parameterized Constructor
    public Clinic(String clinicID, String name, double rating, String address, String joinDate, String locationURL, String availableHours) {
        this.clinicID = clinicID;
        this.name = name;
        this.rating = rating;
        this.address = address;
        this.joinDate = joinDate;
        this.locationURL = locationURL;
        this.availableHours = availableHours;
    }

    // Getters and Setters
    public String getClinicID() {
        return clinicID;
    }

    public void setClinicID(String clinicID) {
        this.clinicID = clinicID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getLocationURL() {
        return locationURL;
    }

    public void setLocationURL(String locationURL) {
        this.locationURL = locationURL;
    }

    public String getAvailableHours() {
        return availableHours;
    }

    public void setAvailableHours(String availableHours) {
        this.availableHours = availableHours;
    }

    @Override
    public String toString() {
        return "Clinic{" +
                "clinicID='" + clinicID + '\'' +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", address='" + address + '\'' +
                ", joinDate='" + joinDate + '\'' +
                ", locationURL='" + locationURL + '\'' +
                ", availableHours='" + availableHours + '\'' +
                '}';
    }
}
