package com.happytails.controllers;

import com.happytails.HappyTails;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuViewController implements Initializable {


    public Pane homeBtn;
    public Pane profileBtn;
    public Pane settingsBtn;
    public StackPane stackPane;
    public Pane appointmentsBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadView("home-view.fxml");
    }

    public void homeClicked(MouseEvent mouseEvent) {
        clearHighlightedBtn();
        homeBtn.setStyle("-fx-background-color: #FFC100; -fx-background-radius: 0 30 30 0;");
        loadView("home-view.fxml");
    }

//    public void petsClicked(MouseEvent mouseEvent) {
//        clearHighlightedBtn();
//        petsBtn.setStyle("-fx-background-color: #FFC100; -fx-background-radius: 0 30 30 0;");
//        loadView("pets-view.fxml");
//    }

    public void profileClicked(MouseEvent mouseEvent) {
        clearHighlightedBtn();
        profileBtn.setStyle("-fx-background-color: #FFC100; -fx-background-radius: 0 30 30 0;");
        loadView("community-view.fxml");
    }

    public void settingsClicked(MouseEvent mouseEvent) {
        clearHighlightedBtn();
        settingsBtn.setStyle("-fx-background-color: #FFC100; -fx-background-radius: 0 30 30 0;");
        loadView("settings-view.fxml");
    }

    public void clearHighlightedBtn(){
        homeBtn.setStyle("-fx-background-color: transparent;");
        profileBtn.setStyle("-fx-background-color: transparent;");
        settingsBtn.setStyle("-fx-background-color: transparent;");
        appointmentsBtn.setStyle("-fx-background-color: transparent;");
        //petsBtn.setStyle("-fx-background-color: transparent;");
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource(fxmlFile));
            Parent view = loader.load();

//            if(loader.getController().getClass()==HomeViewController.class){
//                HomeViewController controller = loader.getController();
//                controller.setMainStackPane(stackPane);
//            }



            //Parent view = FXMLLoader.load(Objects.requireNonNull(HappyTails.class.getResource(fxmlFile)));

            stackPane.getChildren().setAll(view); // Replace current content
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onLogoutClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HappyTails.class.getResource("login-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Happy Tails");
        stage.setScene( new Scene(fxmlLoader.load(), 900, 450));
        stage.show();
        ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();
    }

    public void appointmentsClicked(MouseEvent mouseEvent) {
        clearHighlightedBtn();
        appointmentsBtn.setStyle("-fx-background-color: #FFC100; -fx-background-radius: 0 30 30 0;");
        loadView("appointments-view.fxml");
    }
}
