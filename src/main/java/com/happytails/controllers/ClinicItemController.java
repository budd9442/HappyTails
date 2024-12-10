package com.happytails.controllers;

import com.happytails.models.Clinic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ClinicItemController implements Initializable {
    @FXML
    public Text nameLabel;
    @FXML
    public Text addressLabel;
    @FXML
    public Text ratingLabel;

    public Clinic clinic;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText("AAA");

    }

    void setData(Clinic clinic){
        this.clinic = clinic;
        nameLabel.setText(clinic.getName());

        String[] addressParts = clinic.getAddress().split(",");
        String shortenedAddress = clinic.getAddress(); // Default to full address in case it's short

        if (addressParts.length >= 2) {
            shortenedAddress = addressParts[addressParts.length - 2].trim() + ", "
                    + addressParts[addressParts.length - 1].trim();
        }

        addressLabel.setText(shortenedAddress);
        ratingLabel.setText(String.valueOf(clinic.getRating()));
    }
}
