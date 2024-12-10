package com.happytails.models;

public class Appointment {
    private int appointmentID;
    private String ownerID;
    private String contactNo;
    private String petName;
    private String reason;
    private String clinicID;
    private String date;
    private String startTime;
    private String endTime;

    // Constructor
    public Appointment(int appointmentID, String ownerID, String contactNo, String petName, String reason,
                       String clinicID, String date, String startTime, String endTime) {
        this.appointmentID = appointmentID;
        this.ownerID = ownerID;
        this.contactNo = contactNo;
        this.petName = petName;
        this.reason = reason;
        this.clinicID = clinicID;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and setters
    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getClinicID() {
        return clinicID;
    }

    public void setClinicID(String clinicID) {
        this.clinicID = clinicID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}