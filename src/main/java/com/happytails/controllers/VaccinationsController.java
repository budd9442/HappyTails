package com.happytails.controllers;

import com.happytails.HappyTails;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class VaccinationsController {

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

    public void setParent(StackPane sp){
        parent = sp;
    }

    public void addRecordBtnClicked(MouseEvent mouseEvent) {
    }
}
