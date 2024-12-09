package com.happytails.controllers;

import com.happytails.utils.DBConnector;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class VaccineItemController implements Initializable {
    public Pane pane;
    public ImageView expandBtn;
    public  boolean expanded = false;
    public Text infoText1;
    public Text infoText2;
    public MFXButton completeBtn;
    public MFXButton incompleteBtn;
    public MFXButton missedBtn;
    public Pane colorBox;
    public Text statusText;
    public Text titleText;
    int vaccineId;
    int petId;

    public void setData(int id,int petId,String title, String info1, String info2, String status){
        titleText.setText(title);
        infoText1.setText(info1);
        infoText2.setText(info2);
        statusText.setText(status);
        setColor(status);
        this.vaccineId = id;
        this.petId = petId;


    }

    public void onExpandClick(MouseEvent mouseEvent) {
        if(expanded) pane.setMaxHeight(60);
        else pane.setMaxHeight(210);
        expanded = !expanded;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.setMaxHeight(60);
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(pane.widthProperty());
        clip.heightProperty().bind(pane.heightProperty());
        pane.setClip(clip);



        

    }

    public void onHoverEnter(MouseEvent mouseEvent) {
        pane.setMaxHeight(210);
        expanded = true;
    }

    public void onHoverExit(MouseEvent mouseEvent) {
        pane.setMaxHeight(60);
        expanded = false;
    }

    void setColor(String status){
       switch (status){
           case "Vaccinated" : colorBox.setStyle("-fx-background-color: #87A2FF; -fx-background-radius: 15; -fx-border-radius: 10; -fx-border-color: white; ");
            break;
           case "Not Vaccinated" : colorBox.setStyle("-fx-background-color: #CB9DF0; -fx-background-radius: 15; -fx-border-radius: 10; -fx-border-color: white; ");
            break;
           case "Missed" : colorBox.setStyle("-fx-background-color: #E195AB; -fx-background-radius: 15; -fx-border-radius: 10; -fx-border-color: white; ");
            break;
       }
    }

    public void markComplete(MouseEvent mouseEvent) {
        if(statusText.getText().equals("Vaccinated")) return;
        statusText.setText("Vaccinated");
        setColor("Vaccinated");

        String deleteQuery = "DELETE FROM vaccination_records WHERE pet_id = ? AND vac_id = ?";

        String[] deleteParams = new String[]{String.valueOf(petId), String.valueOf(vaccineId)}; // Assume petID and vacID are available
        DBConnector.executeUpdate(deleteQuery, deleteParams);


        String query = "INSERT INTO vaccination_records (pet_id, vac_id, vaccination_date,status) VALUES (?, ?,?,?)";

        String[] params = new String[]{String.valueOf(petId), String.valueOf(vaccineId), LocalDate.now().toString(),"Vaccinated"}; // Assume petID and vacID are available
        DBConnector.executeUpdate(query, params);

    }

    public void markIncomplete(MouseEvent mouseEvent) {
        if(statusText.getText().equals("Not Vaccinated")) return;
        statusText.setText("Not Vaccinated");
        setColor("Not Vaccinated");
        String query = "DELETE FROM vaccination_records WHERE pet_id = ? AND vac_id = ?";

        String[] params = new String[]{String.valueOf(petId), String.valueOf(vaccineId)}; // Assume petID and vacID are available
        DBConnector.executeUpdate(query, params);


    }

    public void markMissed(MouseEvent mouseEvent) {

        if(statusText.getText().equals("Missed")) return;
        statusText.setText("Missed");
        setColor("Missed");
        String deleteQuery = "DELETE FROM vaccination_records WHERE pet_id = ? AND vac_id = ?";

        String[] deleteParams = new String[]{String.valueOf(petId), String.valueOf(vaccineId)}; // Assume petID and vacID are available
        DBConnector.executeUpdate(deleteQuery, deleteParams);

        String query = "INSERT INTO vaccination_records (pet_id, vac_id,status) VALUES (?, ?, ?)";

        String[] params = new String[]{String.valueOf(petId), String.valueOf(vaccineId),"Missed"}; // Assume petID and vacID are available
        DBConnector.executeUpdate(query, params);

    }
}
