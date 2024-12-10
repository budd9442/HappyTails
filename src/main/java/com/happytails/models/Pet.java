package com.happytails.models;

import com.happytails.utils.DBConnector;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Pet {
    private int petID;
    private String petName;
    private String species;
    private String breed;
    private int age;
    private String gender;
    private String dob;
    private String picURL;

    // Constructor
    public Pet(int petID, String petName, String species, String breed, String gender, String dob, String url) {
        this.petID = petID;
        this.petName = petName;
        this.species = species;
        this.breed = breed;
        this.gender = gender;
        this.dob = dob;
        this.picURL = url;
    }

    public String getPicURL(){return this.picURL;};

    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        if (dob == null || dob.isEmpty()) {
            return 0; // Return 0 if dob is not set
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(dob, formatter);
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String  getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(String dob){ this.dob = dob;}

    public  String getDob(){return  this.dob;}
    @Override
    public String toString() {
        return "Pet{" +
                "petID=" + petID +
                ", petName='" + petName + '\'' +
                ", species='" + species + '\'' +
                ", breed='" + breed + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", dob=" + dob +
                '}';
    }

    public double getHeight() {
        String query = """
        SELECT m.Value
        FROM measurements m
        JOIN pet p ON m.PetID = p.PetID
        WHERE p.Owner = ? AND m.PetID = ? AND m.MeasurementType = 'height'
        ORDER BY m.RecordDate DESC
        LIMIT 1;
    """;

        // Execute the query using DBConnector
        List<Double> results = DBConnector.query(
                query,
                new String[]{DBConnector.currentUserID, String.valueOf(petID)},
                resultSet -> {
                    try {
                        return resultSet.getDouble("Value");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        );

        // Return the height value or null if no result is found
        return results.isEmpty() ? -1 : results.get(0);
    }
    public double getWeight() {
        String query = """
        SELECT m.Value
        FROM measurements m
        JOIN pet p ON m.PetID = p.PetID
        WHERE p.Owner = ? AND m.PetID = ? AND m.MeasurementType = 'weight'
        ORDER BY m.RecordDate DESC
        LIMIT 1;
    """;

        // Execute the query using DBConnector
        List<Double> results = DBConnector.query(
                query,
                new String[]{DBConnector.currentUserID, String.valueOf(petID)},
                resultSet -> {
                    try {
                        return resultSet.getDouble("Value");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        );

        // Return the height value or null if no result is found
        return results.isEmpty() ? -1 : results.get(0);
    }
}
