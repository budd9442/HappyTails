package com.happytails.controllers;

import com.happytails.HappyTails;
import com.happytails.models.Clinic;
import com.happytails.utils.DBConnector;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    public Button confirmBtn;
    public Text summaryText;
    public MFXProgressSpinner progressSpinner;
    public VBox summaryList;
    public MFXTextField searchBar;
    private String owner;
    private String phone;
    private String pet;
    private String reason;
    public  String selectedClinic;
    public  String  startTime;
    public String endTime;
    public StackPane root;
    List<Clinic> clinics;
    public NewAppointmentController(){

    }

    private HBox selectedTimeSlot;

    public void setRoot(StackPane sp){
        root = sp;
    }


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


        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            onSearch();
        });

        selectSlotPane.setVisible(false);

            String query = "SELECT * FROM clinics";
            clinics = DBConnector.query(query, new String[]{}, resultSet -> {
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

            updateClinicList(clinics);

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


    private HBox createTimeSlot(String startTime, String endTime, boolean booked) {
        // Create an HBox for the panel
        HBox panel = new HBox();
        panel.setPrefSize(140, 30); // Adjusted size for GridPane layout
        panel.setPadding(new Insets(5));
        panel.setSpacing(5);

        if(booked) {
            panel.setStyle("-fx-background-color: white; -fx-border-color: gray; -fx-border-radius: 10; -fx-background-radius: 10;");
            panel.setCursor(Cursor.DEFAULT);
        }
        else {
            panel.setStyle("-fx-background-color: #D4BEE4; -fx-border-color: transparent; -fx-border-radius: 10; -fx-background-radius: 10;");
            panel.setCursor(Cursor.HAND);
            panel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> selectTimeSlot(panel));
        }


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


        return panel;
    }

    private void selectTimeSlot(HBox panel) {
        confirmBtn.setDisable(false);
        startTime = ((Label) panel.getChildren().get(0)).getText();
        endTime = ((Label) panel.getChildren().get(2)).getText();
        summarySlot.setText("Time slot : " +startTime + " - " + endTime);
        // Remove highlight from the previously selected time slot
        if (selectedTimeSlot != null) {
            selectedTimeSlot.setStyle("-fx-background-color: #D4BEE4; -fx-border-color: transparent; -fx-border-radius: 10; -fx-background-radius: 10;");
        }

        // Highlight the new selected time slot
        selectedTimeSlot = panel;
        panel.setStyle("-fx-background-color: #D4BEE4; -fx-border-color: purple; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");
    }

    public static String[] fetchAvailableHours(String clinicID) {
        String query = "SELECT availableHours FROM clinics WHERE clinicID = ?";
        String[] availableHours = new String[2]; // To store startTime and endTime

        List<String> results = DBConnector.query(query, new String[]{clinicID}, resultSet -> {
            try {
                return resultSet.getString("availableHours");
            } catch (SQLException e) {
                return null;
            }
        });

        if (!results.isEmpty()) {
            String hours = results.get(0); // Get the first result (there should only be one)
            String[] times = hours.split(" - ");
            if (times.length == 2) {
                availableHours[0] = times[0].trim(); // Start time
                availableHours[1] = times[1].trim(); // End time
            }
        }

        return availableHours;
    }




    public void onSelectDate(ActionEvent actionEvent) {
        if(dateField.getValue() == null) return;

        if (dateField.getValue().isBefore(LocalDate.now())){
            dateField.deselect();
        }

        confirmBtn.setDisable(true);
        summaryDate.setText("Date : " + dateField.getValue().toString());
        summarySlot.setText("");
        selectSlotPane.setVisible(true);
        noSelectionImg.setVisible(false);
        errorMsg.setVisible(false);
        timeSlots.setVisible(true);
        timeSlots.getChildren().clear();
        String[] availableHours = fetchAvailableHours(selectedClinic);
        String[][] timeRanges = generateTimeRanges(availableHours[0], availableHours[1]);
        int row = 0;
        int col = 0;

        List<String> bookedSlots = getBookedSlots(selectedClinic, dateField.getValue().toString());
        for (String[] range : timeRanges) {

            HBox timeSlot = createTimeSlot(range[0], range[1],bookedSlots.contains(range[0]));
            timeSlots.add(timeSlot, col, row); // Add time slot to GridPane

            col++;
            if (col == 2) { // Move to the next row after 2 columns
                col = 0;
                row++;
            }
        }
    }
    public List<String> getBookedSlots(String clinicID, String date) {
        String query = "SELECT startTime FROM appointments WHERE clinicID = ? AND date = ?";
        return DBConnector.query(query, new String[]{clinicID, date}, resultSet -> {
            try {
                return resultSet.getString("startTime");
            } catch (SQLException e) {
                e.printStackTrace();
                return null;  // Return null in case of an error
            }
        });
    }
    public void onContinueClick(MouseEvent mouseEvent) {
        summaryList.setVisible(false);
        progressSpinner.setVisible(true);
        summaryText.setText("Booking...");

        if(selectedTimeSlot == null || dateField.getValue()==null) {
            errorMsg.setText("Please select a time slot");
            errorMsg.setVisible(true);
            return;
        }

        String query = "INSERT INTO appointments (ownerID, contactNo, petName, reason, clinicID, date, startTime, endTime) " +
                "VALUES (?, ?, ?, ?, ?, ?,  ? ,?)";

        // Prepare data for the query
        String[] params = { DBConnector.currentUserID, this.phone , this.pet, this.reason, selectedClinic, dateField.getValue().toString(),startTime, endTime };

        // Execute the update and return the result
        DBConnector.executeUpdate(query, params);


        new Thread(() -> {
            try {
                // Sleep for 5 seconds (5000 milliseconds)
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Hide the loading spinner after 5 seconds
            Platform.runLater(() -> {
                summaryText.setText("Success");
                new Thread(() -> {
                    try {
                        // Sleep for 5 seconds (5000 milliseconds)
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Hide the loading spinner after 5 seconds
                    Platform.runLater(() -> {
                        onCancelClick(mouseEvent);
                    });
                }).start();
            });
        }).start();





    }

    public void onCancelClick(MouseEvent mouseEvent) {

        FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("appointments-main-view.fxml"));
        Parent newAppointment = null;
        try {
            newAppointment = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AppointmentsMainViewController controller = loader.getController();
        controller.setRoot(root);
        root.getChildren().removeAll();
        root.getChildren().setAll(newAppointment);
    }

    public void onSearch() {
        String searchText = searchBar.getText().trim();

        // Validate search text
        if (searchText.isEmpty()) {
            // If the search bar is empty, show all clinics
            updateClinicList(clinics);
            return;
        }

        // Filter clinics based on name or address using Java Streams
        List<Clinic> filteredClinics = clinics.stream()
                .filter(clinic -> clinic.getName().toLowerCase().contains(searchText.toLowerCase()) ||
                        clinic.getAddress().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());

        // Update the list view with filtered clinics
        updateClinicList(filteredClinics);
    }

    private void updateClinicList(List<Clinic> itemList) {
        // Clear the existing list view
        clinicList.getChildren().clear();

        // Populate the list view with the filtered clinics
        // Step 2: Loop through the clinics and load them into the clinicList view
        for (int i = 0; i < itemList.size(); i++) {
            //System.out.println(clinics.get(i));

            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("components/clinic-item.fxml"));

            try {
                Parent clinicItem = loader.load();
                ClinicItemController controller = loader.getController();

                controller.setData(itemList.get(i));

                // Add mouse click event to add border on click, preserving existing styles
                int finalI = i;
                clinicItem.setOnMouseClicked(event -> {
                    selectedClinic = clinics.get(finalI).getClinicID();
                    summaryClinic.setText(String.valueOf(clinics.get(finalI).getName()));
                    summarySlot.setText("");
                    summaryDate.setText("");
                    selectedTimeSlot = null;
                    dateField.setValue(null);
                    confirmBtn.setDisable(true);
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

}

