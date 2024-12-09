package com.happytails.models;

public class Vaccination {
    private int id;
    private String name;
    private String description;
    private String dueAgeDescription;

    public Vaccination(int id, String name, String description, String dueAgeDescription) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueAgeDescription = dueAgeDescription;
    }

    // Getters and toString() for debugging and display
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDueAgeDescription() {
        return dueAgeDescription;
    }

    @Override
    public String toString() {
        return "Vaccination{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dueAgeDescription='" + dueAgeDescription + '\'' +
                '}';
    }
}