package com.happytails.controllers;

import com.happytails.models.Appointment;
import com.happytails.utils.DBConnector;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.happytails.utils.Utils.openWebBrowser;

public class AppointmentItemController implements Initializable {
    public Text dateLabel;
    public Text timeLabel;
    public Text locationLabel;
    public Text petLabel;

    public String clinicName;
    public String address;
    public String locationURL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void setData(Appointment app) {
        dateLabel.setText(app.getDate());
        timeLabel.setText(app.getStartTime());
        petLabel.setText(app.getPetName());

        String query = "SELECT name,address,locationURL FROM clinics WHERE clinicID = ?";
        try {
            DBConnector.query(query, new String[]{app.getClinicID()}, resultSet -> {
                try {
                    locationLabel.setText(resultSet.getString("name"));
                    this.address = resultSet.getString("address");
                    this.locationURL = resultSet.getString("locationURL");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public void openLocation(MouseEvent mouseEvent) {
        openWebBrowser(this.locationURL);
    }


}
