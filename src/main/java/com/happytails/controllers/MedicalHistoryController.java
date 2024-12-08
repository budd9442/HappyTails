package com.happytails.controllers;

import com.happytails.HappyTails;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MedicalHistoryController {
    public StackPane parent;
    public ImageView noRecordsImg;

    public void setParent(StackPane sp) {
        parent = sp;
    }

    public void onSelect(ActionEvent actionEvent) {
        noRecordsImg.setVisible(false);
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

    public void addRecordBtnClicked(MouseEvent mouseEvent) {
    }
}
