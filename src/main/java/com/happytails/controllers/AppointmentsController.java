package com.happytails.controllers;

import com.happytails.HappyTails;
import com.happytails.utils.DBConnector;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {


    public HBox appointmentsList;
    public StackPane mainStackPane;
    public MFXTextField phoneField;
    public MFXComboBox petList;
    public MFXComboBox reasonsList;
    public MFXTextField nameField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        reasonsList.getItems().addAll(new String[] {"Routine Checkup",
                "Vaccinations",
                "Booster Shots",
                "Parasite Prevention",
                "Dental Cleaning",
                "Skin Issues",
                "Illness or Injury",
                "Weight Management",
                "Spaying/Neutering",
                "Pregnancy Care",
                "Grooming (Nail Trim, Coat Maintenance)",
                "Behavioral Consultation",
                "Allergies Management",
                "Chronic Condition Management",
                "Diagnostic Tests",
                "Senior Pet Care",
                "End-of-Life Care"});

        // Add petItem to the GridPane at the current column and row
        for (int i = 0; i < 5 ; i++) {
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("components/appointment-item.fxml"));
            try {
                appointmentsList.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        populatePetList();

    }

    public void onContinueClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("new-appointment.fxml"));
        Parent newAppointment =  loader.load();
        NewAppointmentController controller = loader.getController();
        System.out.println(nameField.getText()+ " " + phoneField.getText() + " " + petList.getText() + " " + reasonsList.getText());
        controller.setData(nameField.getText(), phoneField.getText(), petList.getText(), reasonsList.getText());
        mainStackPane.getChildren().add(newAppointment);
    }

    private void populatePetList() {
        String query = "SELECT PetName, Species, PetID FROM pet";

        // Fetching pet names, species, and PetID using DBConnector
        List<String> petDetails = DBConnector.query(query, new String[]{}, resultSet -> {
            try {
                String petName = resultSet.getString("PetName");
                String species = resultSet.getString("Species");
                String petID = resultSet.getString("PetID");
                return petName + " (" + species + ")";  // Display in format PetName (Species)
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });

        petList.getItems().addAll(petDetails);
        if (!petDetails.isEmpty()) {
            petList.setValue(petDetails.get(0)); // Default to the first pet
            petList.setText(petDetails.get(0));
        }
    }
}
