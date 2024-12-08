package com.happytails.controllers;

import com.happytails.utils.DBConnector;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsViewController implements Initializable {
    public MFXToggleButton debugToggle;

    public void onDebugToggle(MouseEvent mouseEvent) {
        DBConnector.debugMode = debugToggle.isSelected();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        debugToggle.setSelected(DBConnector.debugMode);
    }
}
