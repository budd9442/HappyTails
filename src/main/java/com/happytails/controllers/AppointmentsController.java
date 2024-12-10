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

public class AppointmentsController implements Initializable {

    public StackPane mainStackPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("appointments-main-view.fxml"));
        Parent newAppointment = null;
        try {
            newAppointment = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AppointmentsMainViewController controller = loader.getController();
        controller.setRoot(mainStackPane);
        mainStackPane.getChildren().removeAll();
        mainStackPane.getChildren().setAll(newAppointment);
    }
}