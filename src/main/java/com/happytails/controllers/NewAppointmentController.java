package com.happytails.controllers;

import com.happytails.HappyTails;
import com.happytails.models.Clinic;
import com.happytails.utils.DBConnector;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class NewAppointmentController implements Initializable {
    public VBox clinicList;
    public GridPane timeSlots; // Changed to GridPane
    public Text openHours;
    public Text selectedClinicLabel;
    public Text availableHoursLabel;
    public MFXDatePicker dateField;
    public Text errorMsg;
    public ScrollPane selectSlotPane;
    public ImageView noSelectionImg;
    public Text summarySlot;
    public Text summaryDate;
    public Text summaryClinic;
    public Text summaryReason;
    public Text summaryPet;
    public Text summaryPhone;
    public Text summaryName;
    private String owner;
    private String phone;
    private String pet;
    private String reason;

    public NewAppointmentController(){

    }

    private HBox selectedTimeSlot;


    public void setData(String owner, String phone,String pet, String reason ){
        this.owner = owner;
        this.phone = phone;
        this.pet = pet;
        this.reason = reason;

        summaryPet.setText("Pet : " + pet);
        summaryName.setText("Owner : " + owner);
        summaryReason.setText("Reason : "+ reason);
        summaryPhone.setText("Contact No. : " +phone);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorMsg.setText("Select a clinic first");

        selectSlotPane.setVisible(false);
        errorMsg.setVisible(true);
            String query = "SELECT * FROM clinics";
            List<Clinic> clinics = DBConnector.query(query, new String[]{}, resultSet -> {
                try {
                    // Map ResultSet to Clinic objects using the constructor
                    String clinicID = resultSet.getString("clinicID");
                    String name = resultSet.getString("name");
                    double rating = resultSet.getDouble("rating");
                    String address = resultSet.getString("address");
                    String joinDate = resultSet.getString("joinDate");
                    String locationURL = resultSet.getString("locationURL");
                    String availableHours = resultSet.getString("availableHours");
                    System.out.println("Clinic ID: " + clinicID);

                    // Create and return a new Clinic object
                    return new Clinic(clinicID, name, rating, address, joinDate, locationURL, availableHours);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            });

            // Step 2: Loop through the clinics and load them into the clinicList view
            for (int i = 0; i < clinics.size(); i++) {
                System.out.println(clinics.get(i));

                FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("components/clinic-item.fxml"));

                try {
                    Parent clinicItem = loader.load();
                    ClinicItemController controller = loader.getController();

                    controller.setData(clinics.get(i));

                    // Add mouse click event to add border on click, preserving existing styles
                    int finalI = i;
                    clinicItem.setOnMouseClicked(event -> {
                        summaryClinic.setText(clinics.get(finalI).getName());
                        summarySlot.setText("");
                        summaryDate.setText("");
                        selectSlotPane.setVisible(false);
                        noSelectionImg.setVisible(true);
                        dateField.setDisable(false);
                        timeSlots.setVisible(false);
                        // Remove the white border from all clinic items
                        for (Node child : clinicList.getChildren()) {
                            // Reset the style to remove the white border if it exists
                            child.setStyle(child.getStyle().replace("-fx-border-color: white;", ""));
                            child.setStyle(child.getStyle().replace("-fx-border-width: 2px;", ""));
                        }

                        // Add white border to the clicked item, preserving other styles
                        clinicItem.setStyle(clinicItem.getStyle() + "-fx-border-color: white; -fx-border-width: 2px; ");
                        selectedClinicLabel.setText(clinics.get(finalI).getName());
                        selectedClinicLabel.setVisible(true);
                        availableHoursLabel.setText("Available Hours : " + clinics.get(finalI).getAvailableHours());
                        availableHoursLabel.setVisible(true);

                    });

                    // Add the clinic item to the clinic list
                    clinicList.getChildren().add(clinicItem);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

    }

    public static String[][] generateTimeRanges(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h a");
        LocalTime start = LocalTime.parse(startTime, formatter);
        LocalTime end = LocalTime.parse(endTime, formatter);

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }

        int rangeCount = 0;
        LocalTime temp = start;
        while (temp.isBefore(end)) {
            temp = temp.plusHours(1);
            rangeCount++;
        }

        // Generate time ranges
        String[][] timeRanges = new String[rangeCount][2];
        temp = start;
        for (int i = 0; i < rangeCount; i++) {
            timeRanges[i][0] = temp.format(formatter); // Start of the range
            temp = temp.plusHours(1); // Increment by 1 hour
            timeRanges[i][1] = temp.format(formatter); // End of the range
        }

        return timeRanges;
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
        summarySlot.setText("Time slot : " +((Label) panel.getChildren().get(0)).getText() + " - " + ((Label) panel.getChildren().get(2)).getText());
        // Remove highlight from the previously selected time slot
        if (selectedTimeSlot != null) {
            selectedTimeSlot.setStyle("-fx-background-color: #D4BEE4; -fx-border-color: transparent; -fx-border-radius: 10; -fx-background-radius: 10;");
        }

        // Highlight the new selected time slot
        selectedTimeSlot = panel;
        panel.setStyle("-fx-background-color: #D4BEE4; -fx-border-color: purple; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");
    }



    public void onSelectDate(ActionEvent actionEvent) {
        summaryDate.setText("Date : " + dateField.getValue().toString());
        summarySlot.setText("");
        selectSlotPane.setVisible(true);
        noSelectionImg.setVisible(false);
        errorMsg.setVisible(false);
        timeSlots.setVisible(true);
        timeSlots.getChildren().clear();
        String[][] timeRanges = generateTimeRanges("8 AM", "5 PM");
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
    }
    public void onContinueClick(MouseEvent mouseEvent) {
    }
}

