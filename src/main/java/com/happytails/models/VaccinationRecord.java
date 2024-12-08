package com.happytails.models;

import java.time.LocalDate;

public class VaccinationRecord extends MedicalRecord {
    private String vaccineName;
    private LocalDate nextDueDate;

    public VaccinationRecord(String petName, LocalDate recordDate, String veterinarian, String vaccineName, LocalDate nextDueDate) {
        super(petName, recordDate, veterinarian);
        this.vaccineName = vaccineName;
        this.nextDueDate = nextDueDate;
    }

    @Override
    public void displayRecord() {
        System.out.println("Vaccination Record for: " + getPetName());
        System.out.println("Vaccine: " + vaccineName + ", Date: " + getRecordDate());
        System.out.println("Next Due: " + nextDueDate);
        System.out.println("Veterinarian: " + getVeterinarian());
    }
}

