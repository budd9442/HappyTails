package com.happytails.models;

import java.time.LocalDate;

public class ParasiteControlRecord extends MedicalRecord {
    private String treatmentType;
    private String frequency;

    public ParasiteControlRecord(String petName, LocalDate recordDate, String veterinarian, String treatmentType, String frequency) {
        super(petName, recordDate, veterinarian);
        this.treatmentType = treatmentType;
        this.frequency = frequency;
    }

    @Override
    public void displayRecord() {
        System.out.println("Parasite Control Record for: " + getPetName());
        System.out.println("Treatment: " + treatmentType + ", Date: " + getRecordDate());
        System.out.println("Frequency: " + frequency);
        System.out.println("Veterinarian: " + getVeterinarian());
    }
}
