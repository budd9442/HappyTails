package com.happytails.controllers;

import com.happytails.HappyTails;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyPetsController implements Initializable {


    public HBox petsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            for (int i = 0; i < 5; i++) {
                FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("pet-item.fxml"));
                Parent petItem = loader.load();


                // Replace the current child of the StackPane
                //petsList.getChildren().clear();
                petsList.getChildren().add(petItem);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backBtnClicked(MouseEvent mouseEvent) {
    }
}
