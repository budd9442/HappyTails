package com.happytails.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class CommunityPhotoController {
    public Pane detailsPane;

    private boolean isMouseInsideDetailsPane = false;
    private final PauseTransition hideDelay = new PauseTransition(Duration.millis(200)); // 200ms delay

    @FXML
    public void initialize() {
        // Track mouse entering the details pane
        detailsPane.setOnMouseEntered(event -> {
            isMouseInsideDetailsPane = true;
            hideDelay.stop(); // Stop hiding if the mouse re-enters quickly
        });

        // Track mouse exiting the details pane
        detailsPane.setOnMouseExited(event -> {
            isMouseInsideDetailsPane = false;
            hideWithDelay(); // Start delay before hiding
        });
    }

    public void showDetails(MouseEvent mouseEvent) {
        detailsPane.setVisible(true);
    }

    public void hideDetails(MouseEvent mouseEvent) {
        hideWithDelay(); // Trigger delay before hiding
    }

    private void hideWithDelay() {
        hideDelay.setOnFinished(event -> {
            if (!isMouseInsideDetailsPane) { // Ensure the mouse is still outside
                detailsPane.setVisible(false);
            }
        });
        hideDelay.playFromStart();
    }
}
