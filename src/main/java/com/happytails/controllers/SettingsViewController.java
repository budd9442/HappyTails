package com.happytails.controllers;

import com.happytails.utils.DBConnector;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.Initializable;
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

            if (newPassword.isEmpty() || confirmPassword.isEmpty() || currentPassword.isEmpty()) {
                System.out.println("Please fill in all password fields.");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                System.out.println("New password and confirmation password do not match.");
                return;
            }

            // Validate the current password from the database
            String query = "SELECT password FROM user WHERE user_id = ?";
            DBConnector.query(query, new String[]{DBConnector.currentUserID}, resultSet -> {
                try {
                    String storedPassword = resultSet.getString("password");

                    if (!storedPassword.equals(currentPassword)) {
                        System.out.println("Current password is incorrect.");
                    } else {
                        // Update the password
                        String updateQuery = "UPDATE user SET password = ? WHERE user_id = ?";
                        int rowsAffected = DBConnector.executeUpdate(updateQuery, new String[]{newPassword, DBConnector.currentUserID});

                        if (rowsAffected > 0) {
                            System.out.println("Password updated successfully.");
                        } else {
                            System.out.println("Failed to update password.");
                        }
                    }
                } catch (SQLException e) {
                    DBConnector.printSQLException(e);
                }
                return null;
            });
        } else {
            System.out.println("User ID is not set. Please log in.");
        }
    }

    public boolean validateChanges(){
        if(nameField.getText().isBlank()|| mailField.getText().isBlank() || addressField.getText().isBlank() || phoneFIeld.getText().isBlank()  ) return false;

        return true;

    }


    public void saveChanges(MouseEvent mouseEvent) {
        if (DBConnector.currentUserID != null) {
            String query = "UPDATE user SET name = ?, email = ?, phone = ?, address = ? WHERE user_id = ?";

            String[] updatedValues = new String[]{
                    nameField.getText().trim(),
                    mailField.getText().trim(),
                    phoneFIeld.getText().trim(),
                    addressField.getText().trim(),
                    DBConnector.currentUserID
            };

            int rowsAffected = DBConnector.executeUpdate(query, updatedValues);

            if (rowsAffected > 0) {
                System.out.println("User details updated successfully.");
            } else {
                System.out.println("Failed to update user details. Please check your input.");
            }
        }
    }


}
