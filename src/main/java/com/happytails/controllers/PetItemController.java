package com.happytails.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.net.URL;
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
}
