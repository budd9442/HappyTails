package com.happytails.controllers;

import com.happytails.HappyTails;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

//comment

public class LoginPageController {

    @FXML
    private MFXButton loginBtn;


    public void loginBtnCicked(MouseEvent mouseEvent) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HappyTails.class.getResource("menu-view.fxml"));


            Stage stage = new Stage();
            stage.setTitle("Happy Tails");
            //stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(fxmlLoader.load(), 1280, 720));
            stage.show();
            ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();
            //loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}