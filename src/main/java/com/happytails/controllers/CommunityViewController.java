package com.happytails.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommunityViewController implements Initializable {
    @FXML
    public GridPane feedGrid;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            for (int i = 0; i < 3; i++) { // Replace 6 with the number of items to load dynamically
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/happytails/components/community-photo.fxml"));

                Node communityPhoto = loader.load();

                // Calculate column (0 or 1) and row dynamically
                int column = i % 2; // Alternates between 0 and 1 for two columns
                int row = i / 2;    // Increments after every two items

                // Add it to the GridPane at the calculated position
                feedGrid.add(communityPhoto, column, row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
