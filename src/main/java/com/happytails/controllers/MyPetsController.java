package com.happytails.controllers;

import com.happytails.HappyTails;
import com.happytails.models.Pet;
import com.happytails.utils.DBConnector;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MyPetsController implements Initializable {


    public GridPane petsList;
    public StackPane parent;

    public void setParent(StackPane sp){
        parent = sp;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            String query = "SELECT * FROM Pet";
            String[] argsArray = {}; // No arguments needed for this query

            // Fetch all pets
            List<Pet> pets =DBConnector.query(query, argsArray, resultSet -> {
                try {
                    return new Pet(
                            resultSet.getInt("PetID"),
                            resultSet.getString("PetName"),
                            resultSet.getString("Species"),
                            resultSet.getString("Breed"),
                            resultSet.getString("Gender"),
                            resultSet.getString("DateOfBirth")

                    );
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            });

            // Print all pets
            pets.forEach(System.out::println);

            int i =0;
            for (Pet pet:pets) {
                FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("components/pet-item.fxml"));
                Parent petItem = loader.load();
                PetItemController controller = loader.getController();
                controller.setData(pet.getPetName(),pet.getSpecies(),pet.getBreed(),pet.getDob());


                // Add petItem to the GridPane at the current column and row
                petsList.add(petItem, 0, i);

                i++;
                // Update column and row indices

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backBtnClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("home-view.fxml"));
            Parent view = loader.load();
            parent.getChildren().setAll(view); // Replace current content
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPetBtnClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("add-pet.fxml"));
            Parent myPetsView = loader.load();
            AddPetController controller = loader.getController();
            controller.setParent(parent);
            parent.getChildren().clear();
            parent.getChildren().add(myPetsView);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
