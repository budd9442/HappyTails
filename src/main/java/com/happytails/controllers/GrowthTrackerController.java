package com.happytails.controllers;

import com.happytails.HappyTails;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GrowthTrackerController implements Initializable {
    public StackPane weightPane;
    public StackPane heightPane;
    public StackPane parent;

    public void setParent(StackPane sp){
        parent = sp;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Defining the X axis
        CategoryAxis xAxis = new CategoryAxis();

        //defining the y Axis
        NumberAxis yAxis = new NumberAxis(0, 15, 2.5);
        yAxis.setLabel("Weight(kg)");

        CategoryAxis xAxis2 = new CategoryAxis();

        //defining the y Axis
        NumberAxis yAxis2 = new NumberAxis(0, 15, 2.5);
        yAxis.setLabel("Height(cm)");

        //Creating the Area chart
        AreaChart<String, Number> areaChart = new AreaChart<>(xAxis, yAxis);
        AreaChart<String, Number> areaChart2 = new AreaChart<>(xAxis2, yAxis2);
        areaChart.setTitle("Weight records");
        areaChart2.setTitle("Height records");

        //Prepare XYChart.Series objects by setting data
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("John");
        series1.getData().add(new XYChart.Data("Monday", 3));
        series1.getData().add(new XYChart.Data("Tuesday", 4));
        series1.getData().add(new XYChart.Data("Wednesday", 3));
        series1.getData().add(new XYChart.Data("Thursday", 5));
        series1.getData().add(new XYChart.Data("Friday", 4));
        series1.getData().add(new XYChart.Data("Saturday", 10));
        series1.getData().add(new XYChart.Data("Sunday", 12));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Jane");
        series2.getData().add(new XYChart.Data("Monday", 1));
        series2.getData().add(new XYChart.Data("Tuesday", 3));
        series2.getData().add(new XYChart.Data("Wednesday", 4));
        series2.getData().add(new XYChart.Data("Thursday", 3));
        series2.getData().add(new XYChart.Data("Friday", 3));
        series2.getData().add(new XYChart.Data("Saturday", 5));
        series2.getData().add(new XYChart.Data("Sunday", 4));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("John");
        series3.getData().add(new XYChart.Data("Monday", 3));
        series3.getData().add(new XYChart.Data("Tuesday", 4));
        series3.getData().add(new XYChart.Data("Wednesday", 3));
        series3.getData().add(new XYChart.Data("Thursday", 5));
        series3.getData().add(new XYChart.Data("Friday", 4));
        series3.getData().add(new XYChart.Data("Saturday", 10));
        series3.getData().add(new XYChart.Data("Sunday", 12));

        XYChart.Series series4 = new XYChart.Series();
        series4.setName("Jane");
        series4.getData().add(new XYChart.Data("Monday", 1));
        series4.getData().add(new XYChart.Data("Tuesday", 3));
        series4.getData().add(new XYChart.Data("Wednesday", 4));
        series4.getData().add(new XYChart.Data("Thursday", 3));
        series4.getData().add(new XYChart.Data("Friday", 3));
        series4.getData().add(new XYChart.Data("Saturday", 5));
        series4.getData().add(new XYChart.Data("Sunday", 4));

        //Setting the XYChart.Series objects to area chart
        areaChart.getData().addAll(series1,series2);
        areaChart2.getData().addAll(series3,series4);

        weightPane.getChildren().add(areaChart);
        heightPane.getChildren().add(areaChart2);
    }

    public void backBtnClicked(MouseEvent mouseEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("home-view.fxml"));
            Parent view = loader.load();
            parent.getChildren().setAll(view); // Replace current content
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
