package com.happytails.controllers;

import com.happytails.HappyTails;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewAppointmentController implements Initializable {
    public VBox clinicList;
    public GridPane timeSlots; // Changed to GridPane

    // Variable to track the selected time slot
    private HBox selectedTimeSlot;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add example time slots
        String[][] timeRanges = {
                {"8 AM", "9 AM"},
                {"9 AM", "10 AM"},
                {"10 AM", "11 AM"},
                {"11 AM", "12 PM"},
                {"12 PM", "1 PM"},
                {"1 AM", "2 PM"},
                {"2 PM", "2 PM"},
                {"3 PM", "3 PM"},
                {"4 PM", "4 PM"}
        };

        int row = 0;
        int col = 0;

        for (String[] range : timeRanges) {
            HBox timeSlot = createTimeSlot(range[0], range[1]);
            timeSlots.add(timeSlot, col, row); // Add time slot to GridPane

            col++;
            if (col == 2) { // Move to the next row after 2 columns
                col = 0;
                row++;
            }
        }
        for (int i = 0; i < 5 ; i++) {
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("components/clinic-item.fxml"));
            try {
                clinicList.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        }
    }

    private HBox createTimeSlot(String startTime, String endTime) {
        // Create an HBox for the panel
        HBox panel = new HBox();
        panel.setPrefSize(140, 30); // Adjusted size for GridPane layout
        panel.setPadding(new Insets(5));
        panel.setSpacing(5);
        panel.setStyle("-fx-background-color: #D4BEE4; -fx-border-color: transparent; -fx-border-radius: 10; -fx-background-radius: 10;");
        panel.setCursor(Cursor.HAND);
        panel.setAlignment(Pos.CENTER);

        // Add rounded corners
        panel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(10), Insets.EMPTY)));

        // Create labels for start and end time
        Label startTimeLabel = new Label(startTime);
        Label endTimeLabel = new Label(endTime);
        startTimeLabel.setFont(new Font(14));
        endTimeLabel.setFont(new Font(14));

        Label dash = new Label(" - ");
        dash.setFont(new Font(14));

        // Add labels to the panel
        panel.getChildren().addAll(startTimeLabel, dash, endTimeLabel);

        // Add click event to highlight the selected time slot
        panel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> selectTimeSlot(panel));

        return panel;
    }

    private void selectTimeSlot(HBox panel) {
        // Remove highlight from the previously selected time slot
        if (selectedTimeSlot != null) {
            selectedTimeSlot.setStyle("-fx-background-color: #D4BEE4; -fx-border-color: transparent; -fx-border-radius: 10; -fx-background-radius: 10;");
        }

        // Highlight the new selected time slot
        selectedTimeSlot = panel;
        panel.setStyle("-fx-background-color: #D4BEE4; -fx-border-color: purple; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");
    }

    public void onContinueClick(MouseEvent mouseEvent) {
    }
}
