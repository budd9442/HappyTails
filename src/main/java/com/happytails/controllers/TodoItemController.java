package com.happytails.controllers;

import com.happytails.utils.DBConnector;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class TodoItemController {
    public ImageView tickIcon;
    public ImageView deleteBtn;
    public Label textLabel;
    public Pane colorLabel;

    public void setData(String text, String color, boolean done){
        textLabel.setText(text);
        colorLabel.setStyle("-fx-background-color: " + color + ";" + "-fx-background-radius : 20;");
        //deleteBtn.setTextFill(Color.web(color));
        //deleteBtn.setStyle("-fx-foreground-color: " + color + ";");
        tickIcon.setVisible(done);

    }

    public void onClick(MouseEvent mouseEvent) {
        tickIcon.setVisible(!tickIcon.isVisible());

        String updateQuery = "UPDATE todo SET done = ?  WHERE user_id = ? AND text = ?";
        String doneStatus = tickIcon.isVisible() ? "1" : "0";

        // Execute the update query
        DBConnector.executeUpdate(updateQuery, new String[]{doneStatus, DBConnector.currentUserID, textLabel.getText()});

    }
}
