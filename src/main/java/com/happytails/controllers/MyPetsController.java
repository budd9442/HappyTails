package com.happytails.controllers;

import com.happytails.HappyTails;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyPetsController implements Initializable {


    public GridPane petsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            for (int i = 0; i < 5; i++) {
                FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("components/pet-item.fxml"));
                Parent petItem = loader.load();

                // Add petItem to the GridPane at the current column and row
                petsList.add(petItem, 0, i);

                // Update column and row indices

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backBtnClicked(MouseEvent mouseEvent) {
    }
}
