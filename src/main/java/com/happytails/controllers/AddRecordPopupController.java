package com.happytails.controllers;

import com.happytails.utils.DBConnector;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class AddRecordPopupController {
    @FXML
    public MFXComboBox<String> petSelector;
    @FXML
    public MFXComboBox<String> recordTypeSelector;
    @FXML
    public TextField valueField;

    private Runnable onSubmitCallback; // Callback to refresh chart after submission

    @FXML
    public void initialize() {
        // Populate pets
        String petQuery = "SELECT PetID, PetName FROM pet";
        List<String> pets = DBConnector.query(petQuery, new String[]{}, resultSet -> {
            try {
                return resultSet.getString("PetName") + " (ID: " + resultSet.getInt("PetID") + ")";
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
        petSelector.getItems().addAll(pets);
    }

    @FXML
    public void submit() {
        String selectedPet = petSelector.getValue();
        String recordType = recordTypeSelector.getValue();
        String value = valueField.getText();

        if (selectedPet == null || recordType == null || value.isEmpty()) {
            System.out.println("All fields must be filled!");
            return;
        }

        try {
            int petId = Integer.parseInt(selectedPet.split("ID: ")[1].replace(")", ""));
            double measurementValue = Double.parseDouble(value);

            // Insert into database
            String insertQuery = "INSERT INTO measurements (PetID, MeasurementType, Value, RecordDate) " +
                    "VALUES (?, ?, ?, CURRENT_DATE)";
            DBConnector.executeUpdate(insertQuery, new String[]{String.valueOf(petId), recordType.toLowerCase(), String.valueOf(measurementValue)});

            // Refresh chart
            if (onSubmitCallback != null) {
                onSubmitCallback.run();
            }

            // Close the popup
            Stage stage = (Stage) petSelector.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) petSelector.getScene().getWindow();
        stage.close();
    }

    public void setOnSubmitCallback(Runnable callback) {
        this.onSubmitCallback = callback;
    }
}
