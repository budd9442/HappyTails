package com.happytails.models;

import java.time.LocalDate;
import java.util.List;

public class MedicalTreatmentRecord extends MedicalRecord {
    private String treatmentName;
    private List<String> medications;
    private LocalDate followUpDate;

    public MedicalTreatmentRecord(String petName, LocalDate recordDate, String veterinarian, String treatmentName, List<String> medications, LocalDate followUpDate) {
        super(petName, recordDate, veterinarian);
        this.treatmentName = treatmentName;
        this.medications = medications;
        this.followUpDate = followUpDate;
    }

    @Override
    public void displayRecord() {
        System.out.println("Medical Treatment Record for: " + getPetName());
        System.out.println("Treatment: " + treatmentName + ", Date: " + getRecordDate());
        System.out.println("Medications: " + String.join(", ", medications));
        System.out.println("Follow-up: " + followUpDate);
        System.out.println("Veterinarian: " + getVeterinarian());
    }
}
