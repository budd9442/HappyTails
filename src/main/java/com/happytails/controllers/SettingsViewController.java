package com.happytails.controllers;

import com.happytails.utils.DBConnector;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingsViewController implements Initializable {
    public MFXToggleButton debugToggle;
    public MFXPasswordField currentPass;
    public MFXPasswordField newPass;
    public MFXPasswordField confirmPass;
    public MFXTextField nameField;
    public MFXTextField mailField;
    public MFXTextField phoneField;
    public MFXTextField addressField;
    public MFXTextField phoneFIeld;


    public void onDebugToggle(MouseEvent mouseEvent) {
        DBConnector.debugMode = debugToggle.isSelected();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        debugToggle.setSelected(DBConnector.debugMode);
        populateData();
    }

    public void populateData() {
        if (DBConnector.currentUserID != null) {
            String query = "SELECT name, email, phone, address FROM user WHERE user_id = ?";
            DBConnector.query(query, new String[]{DBConnector.currentUserID}, resultSet -> {
                try {
                    nameField.setText(resultSet.getString("name"));
                    mailField.setText(resultSet.getString("email"));
                    addressField.setText(resultSet.getString("address"));
                    phoneFIeld.setText(resultSet.getString("phone"));
                   System.out.println(resultSet.getString("phone"));
                   // if(resultSet.getString("address")!= null) addressField.setText(resultSet.getString("address"));
                } catch (SQLException e) {
                    DBConnector.printSQLException(e);
                }
                return null;
            });
        }
    }

    public void updatePassword(MouseEvent mouseEvent) {
        if (DBConnector.currentUserID != null) {
            String currentPassword = currentPass.getText().trim();
            String newPassword = newPass.getText().trim();
            String confirmPassword = confirmPass.getText().trim();

            if (newPassword.length() < 8) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "New password must be at least 8 characters long.");
                return;
            }

            if (newPassword.isEmpty() || confirmPassword.isEmpty() || currentPassword.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill in all the required fields.");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Passwords do not match");
                return;
            }

            // Validate the current password from the database
            String query = "SELECT password FROM user WHERE user_id = ?";
            DBConnector.query(query, new String[]{DBConnector.currentUserID}, resultSet -> {
                try {
                    String storedPassword = resultSet.getString("password");

                    if (!storedPassword.equals(currentPassword)) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Password is wrong");
                    } else {
                        // Update the password
                        String updateQuery = "UPDATE user SET password = ? WHERE user_id = ?";
                        int rowsAffected = DBConnector.executeUpdate(updateQuery, new String[]{newPassword, DBConnector.currentUserID});

                        if (rowsAffected > 0) {
                            showAlert(Alert.AlertType.CONFIRMATION, "Success", "Password Updated.");
                        } else {
                            showAlert(Alert.AlertType.ERROR, "Error", "Unknown Error");
                        }
                    }
                } catch (SQLException e) {
                    DBConnector.printSQLException(e);
                }
                return null;
            });
        } else {
            System.out.println("User ID is not set. Please log in."); // never happens
        }
    }

    public boolean validateChanges(){
        if(nameField.getText().isBlank()|| mailField.getText().isBlank() || addressField.getText().isBlank() || phoneFIeld.getText().isBlank()  ) return false;

        return true;

    }

    public void saveChanges(MouseEvent mouseEvent) {
        if (DBConnector.currentUserID != null) {
            // Validate input fields
            if (nameField.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Name cannot be empty.");
                return;
            }

            if (mailField.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Email cannot be empty.");
                return;
            }

            if (!isValidEmail(mailField.getText().trim())) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid email address.");
                return;
            }

            if (phoneFIeld.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Phone number cannot be empty.");
                return;
            }

            if (!isValidPhoneNumber(phoneFIeld.getText().trim())) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid phone number.");
                return;
            }

            if (addressField.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Address cannot be empty.");
                return;
            }

            // Prepare and execute the update query
            String query = "UPDATE user SET name = ?, email = ?, phone = ?, address = ? WHERE user_id = ?";
            String[] updatedValues = new String[]{
                    nameField.getText().trim(),
                    mailField.getText().trim(),
                    phoneFIeld.getText().trim(),
                    addressField.getText().trim(),
                    DBConnector.currentUserID
            };

            int rowsAffected = DBConnector.executeUpdate(query, updatedValues);

            // Show appropriate success or failure message
            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "User details updated successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update user details. Please try again.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "No user is currently logged in.");
        }
    }

    // Utility method to display alerts
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Utility method to validate email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    // Utility method to validate phone number
    private boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^[0-9]{10}$"; // Adjust the regex as per your requirement
        return phone.matches(phoneRegex);
    }



}
