package com.happytails.controllers;

import com.happytails.HappyTails;
import com.happytails.utils.DBConnector;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Objects;

//comment

public class LoginPageController {

    public MFXTextField usernameTextBox;
    public MFXPasswordField passwordTextBox;
    @FXML
    private MFXButton loginBtn;

    public void setData(String username, String password){
        usernameTextBox.setText(username);
        passwordTextBox.setText(password);
    }

    public void loginBtnCicked(MouseEvent mouseEvent) {
        Parent root;
        try {

            if(usernameTextBox.getText().isBlank() || passwordTextBox.getText().isBlank()){
                JOptionPane.showMessageDialog(
                        null,
                        "Please fill in all the required fields",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            DBConnector.currentUserID = DBConnector.login(usernameTextBox.getText(),passwordTextBox.getText());
            if( DBConnector.currentUserID != null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Login Success!\nCurrent UID : " + DBConnector.currentUserID,
                        "DEBUG",
                        JOptionPane.INFORMATION_MESSAGE

                );
                FXMLLoader fxmlLoader = new FXMLLoader(HappyTails.class.getResource("menu-view.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Happy Tails");
                //stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(new Scene(fxmlLoader.load(), 1280, 720));
                stage.show();
                ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();
                //loginStage.close();

            }else{
                JOptionPane.showMessageDialog(
                        null,
                        "Incorrect password",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signUpClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HappyTails.class.getResource("register-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Happy Tails");
        stage.setScene(new Scene(fxmlLoader.load(), 900, 500));
        stage.show();
        ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();
    }
}