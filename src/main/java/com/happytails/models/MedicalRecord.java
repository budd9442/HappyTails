package com.happytails.models;
import java.time.LocalDate;
import java.util.List;

public abstract class MedicalRecord {
    private String petName;
    private LocalDate recordDate;
    private String veterinarian;

    public MedicalRecord(String petName, LocalDate recordDate, String veterinarian) {
        this.petName = petName;
        this.recordDate = recordDate;
        this.veterinarian = veterinarian;
    }

    public String getPetName() {
        return petName;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public String getVeterinarian() {
        return veterinarian;
    }

    public abstract void displayRecord(); // Abstract method to be implemented by subclasses
}

