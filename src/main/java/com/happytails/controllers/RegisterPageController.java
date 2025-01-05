package com.happytails.controllers;

import com.happytails.HappyTails;
import com.happytails.utils.DBConnector;
import com.happytails.utils.Utils;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

public class RegisterPageController {
    public MFXButton registerBtn;
    public MFXTextField nameTextBox;
    public MFXTextField emailTextBox;
    public MFXPasswordField passwordTextBox;
    public MFXPasswordField confirmPasswordTextBox;



    public void registerBtnClicked(MouseEvent mouseEvent) throws IOException {
        if(nameTextBox.getText().isBlank() || emailTextBox.getText().isBlank() || emailTextBox.getText().isBlank() || confirmPasswordTextBox.getText().isBlank()){
            JOptionPane.showMessageDialog(
                    null,
                    "Please fill in all the required fields",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        if(!Utils.isValidEmail(emailTextBox.getText())){
            JOptionPane.showMessageDialog(
                    null,
                    "Please enter a valid email",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        if(passwordTextBox.getText().length()<8){
            JOptionPane.showMessageDialog(
                    null,
                    "Passwords must be minimum 8 characters long",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        if(!Objects.equals(passwordTextBox.getText(), confirmPasswordTextBox.getText())){
            JOptionPane.showMessageDialog(
                    null,
                    "Passwords do not match",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if(DBConnector.register(nameTextBox.getText(),emailTextBox.getText(),passwordTextBox.getText()) != -1){
            JOptionPane.showMessageDialog(
                    null,
                    "User registered",
                    "DEBUG",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HappyTails.class.getResource("login-view.fxml"));

        Stage stage = new Stage();
        stage.setTitle("HappyTails");
        stage.setScene( new Scene(fxmlLoader.load(), 900, 450));
        stage.show();
        LoginPageController controller = fxmlLoader.getController();
        controller.setData(emailTextBox.getText(),passwordTextBox.getText());
        ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();




    }

    public void loginClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HappyTails.class.getResource("login-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Happy Tails");
        stage.setScene( new Scene(fxmlLoader.load(), 900, 450));
        stage.show();
        ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();
    }
}
