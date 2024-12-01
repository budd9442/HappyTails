package com.happytails.controllers;

import com.happytails.HappyTails;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPetController implements Initializable {

    public StackPane parent;

    public void setParent(StackPane sp){
        parent = sp;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void backBtnClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("my-pets.fxml"));
            Parent myPetsView = loader.load();
            MyPetsController controller = loader.getController();
            controller.setParent(parent);
            parent.getChildren().clear();
            parent.getChildren().add(myPetsView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
