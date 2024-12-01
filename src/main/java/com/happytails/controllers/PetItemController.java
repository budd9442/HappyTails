package com.happytails.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class PetItemController implements Initializable {
    public ImageView petImage;
    public Label nameLabel;
    public Label ageLabel;
    public Label typeLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Rectangle roundedRectangle = new Rectangle(0, 0, petImage.getFitWidth(), petImage.getFitHeight());
        roundedRectangle.setArcWidth(40); // Set horizontal radius for rounded corners
        roundedRectangle.setArcHeight(40); // Set vertical radius for rounded corners

        petImage.setClip(roundedRectangle);


    }
    void setData(String name, String type, String breed, String dob) {
        nameLabel.setText(name);
        typeLabel.setText(type + " : " + breed);

        // Parse the date of birth
        LocalDate birthDate = LocalDate.parse(dob); // Assumes dob is in the format "YYYY-MM-DD"
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);

        String ageDisplay;
        if (period.getYears() > 0) {
            ageDisplay = period.getYears() + (period.getYears() == 1 ? " year old" : " years old");
        } else if (period.getMonths() > 0) {
            ageDisplay = period.getMonths() + (period.getMonths() == 1 ? " month old" : " months old");
        } else if (period.getDays() > 0) {
            int weeks = period.getDays() / 7;
            ageDisplay = weeks > 0 ? weeks + (weeks == 1 ? " week old" : " weeks old") : period.getDays() + (period.getDays() == 1 ? " day old" : " days old");
        } else {
            ageDisplay = "Newborn";
        }

        ageLabel.setText(ageDisplay);
    }

}
