package com.happytails.controllers;

import com.happytails.HappyTails;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {


    public HBox appointmentsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Add petItem to the GridPane at the current column and row
        for (int i = 0; i < 5 ; i++) {
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("components/appointment-item.fxml"));
            try {
                appointmentsList.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //FXMLLoader loader2 = new FXMLLoader(HappyTails.class.getResource("components/time-slot-item.fxml"));
            //timeSlots.getChildren().add(loader2.load());
        }

    }
}
