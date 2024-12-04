package com.happytails.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class VaccineItemController implements Initializable {
    public Pane pane;
    public ImageView expandBtn;
    public  boolean expanded = false;
    public Text infoText1;
    public Text infoText2;
    public MFXButton cpmpleteBtn;
    public MFXButton incompleteBtn;
    public MFXButton missedBtn;
    public Pane colorBox;
    public Text statusText;

    public void onExpandClick(MouseEvent mouseEvent) {
        if(expanded) pane.setMaxHeight(60);
        else pane.setMaxHeight(210);
        expanded = !expanded;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.setMaxHeight(60);
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(pane.widthProperty());
        clip.heightProperty().bind(pane.heightProperty());
        pane.setClip(clip);
        

    }

    public void onHoverEnter(MouseEvent mouseEvent) {
        pane.setMaxHeight(210);
        expanded = true;
    }

    public void onHoverExit(MouseEvent mouseEvent) {
        pane.setMaxHeight(60);
        expanded = false;
    }

    public void markComplete(MouseEvent mouseEvent) {
        colorBox.setStyle("-fx-background-color: #87A2FF; -fx-background-radius: 15; -fx-border-radius: 10; -fx-border-color: white; ");
        statusText.setText("Completed");
    }

    public void markIncomplete(MouseEvent mouseEvent) {
        colorBox.setStyle("-fx-background-color: #CB9DF0; -fx-background-radius: 15; -fx-border-radius: 10; -fx-border-color: white; ");
        statusText.setText("Incomplete");

    }

    public void markMissed(MouseEvent mouseEvent) {
        colorBox.setStyle("-fx-background-color: #E195AB; -fx-background-radius: 15; -fx-border-radius: 10; -fx-border-color: white; ");
        statusText.setText("Missed");
    }
}
