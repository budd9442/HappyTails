package com.happytails.controllers;

import com.happytails.HappyTails;
import com.happytails.models.Pet;
import com.happytails.models.Vaccination;
import com.happytails.utils.DBConnector;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class VaccinationsController implements Initializable {


    public VBox vaccinesList;
    public ImageView selectedPetImage;
    public MFXComboBox petSelector;
    public ImageView noVacsImg;
    @FXML
    private Label nameLabel;

    public void backBtnClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("home-view.fxml"));
            Parent view = loader.load();
            parent.getChildren().setAll(view); // Replace current content
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StackPane parent;

    public void setParent(StackPane sp) {
        parent = sp;
    }

    public void addRecordBtnClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("components/vaccine-item.fxml"));
        Parent vacItem = loader.load();
        VaccineItemController controller = loader.getController();
        //controller.setData();


        // Add petItem to the GridPane at the current column and row
        vaccinesList.getChildren().add(vacItem);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedPetImage.setClip(new Circle(100, 100, 100));
        //petSelector.setItems(FXCollections.observableArrayList("Dog 1", "Dog 2", "Dog 3"));
        try {
            populatePetSelector();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        petSelector.setOnAction(event -> {
            String selectedPet = (String) petSelector.getValue();
            if (selectedPet != null) {
                try {
                    populateList(selectedPet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }
    private void populateList(String selectedPet) throws IOException {
        noVacsImg.setVisible(false);

        vaccinesList.getChildren().clear();
        String species = selectedPet.substring(selectedPet.indexOf('(') + 1, selectedPet.indexOf(')'));
        String name = selectedPet.substring(0, selectedPet.indexOf('(')).trim();

        String query = "SELECT PetID FROM pet WHERE PetName = ? AND Species = ?";
        List<Integer> petIDs = DBConnector.query(query, new String[]{name, species}, resultSet -> {
            try {
                return resultSet.getInt("PetID"); // Retrieve the PetID
            } catch (SQLException e) {
                return null; // Return null if there is an issue
            }
        });



        List<Vaccination> vacs = getVaccinationsBySpecies(species);
        for(Vaccination vac :vacs){
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("components/vaccine-item.fxml"));
            Parent vacItem = loader.load();
            VaccineItemController controller = loader.getController();
            controller.setData(vac.getId(),petIDs.getFirst(), vac.getName(),vac.getDescription(),vac.getDueAgeDescription(),getVaccinationStatus(petIDs.getFirst(),vac.getId()));


            // Add petItem to the GridPane at the current column and row
            vaccinesList.getChildren().add(vacItem);

        }


    }

    public String getVaccinationStatus(int petId, int vacId) {
        String query = "SELECT * FROM vaccination_records WHERE pet_id = ? AND vac_id = ?";
        List<String> results = DBConnector.query(query, new String[]{String.valueOf(petId), String.valueOf(vacId)}, resultSet -> {
            try {
                return resultSet.getString("status");
            } catch (SQLException e) {
                return null;
            }
        });

        if (results.isEmpty()) {
            return "Not Vaccinated";
        } else {
            return results.get(0);
        }
    }


    public static List<Vaccination> getVaccinationsBySpecies(String species) {
        // SQL query to fetch vaccinations by species
        String query = """
            SELECT 
                id, 
                vaccination_name, 
                description, 
                due_age_description 
            FROM 
                vaccinations 
            WHERE 
                species = ?;
        """;

        // Use DBConnector.query to fetch results and map them to Vaccination objects
        return DBConnector.query(query, new String[]{species}, resultSet -> {
            try {
                return new Vaccination(
                        resultSet.getInt("id"),
                        resultSet.getString("vaccination_name"),
                        resultSet.getString("description"),
                        resultSet.getString("due_age_description")
                );
            } catch (Exception e) {
                e.printStackTrace();
                return null; // Handle or log error as needed
            }
        });
    }

    private void populatePetSelector() throws IOException {
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

        // Adding fetched details to the ComboBox

        if (!petDetails.isEmpty()) {
            petSelector.setText(petDetails.get(0)); // Default to the first pet
            populateList(petDetails.get(0));
        }

        if(DBConnector.debugMode) System.out.println(petDetails);
        petSelector.getItems().addAll(petDetails);
    }

    public void onSelect(ActionEvent actionEvent) {
        noVacsImg.setVisible(false);
    }


}
