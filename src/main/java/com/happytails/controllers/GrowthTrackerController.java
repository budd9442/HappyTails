package com.happytails.controllers;

import com.happytails.HappyTails;
import com.happytails.utils.DBConnector;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class GrowthTrackerController implements Initializable {
    public StackPane weightPane;
    public StackPane heightPane;
    public StackPane parent;
    public MFXComboBox<String> petSelector;

    public void setParent(StackPane sp) {
        parent = sp;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populatePetSelector();

        petSelector.setOnAction(event -> {
            String selectedPet = petSelector.getValue();
            if (selectedPet != null) {
                populateCharts(selectedPet);
            }
        });
    }

    private void populatePetSelector() {
        String query = "SELECT PetName, Species, PetID FROM pet";

        // Fetching pet names, species, and PetID using DBConnector
        List<String> petDetails = DBConnector.query(query, new String[]{}, resultSet -> {
            try {
                String petName = resultSet.getString("PetName");
                String species = resultSet.getString("Species");
                String petID = resultSet.getString("PetID");
                return petName + " (" + species + ")";  // Display in format PetName (Species)
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });

        // Add "All" option to the ComboBox
        petSelector.getItems().add("All");

        // Adding fetched details to the ComboBox
        petSelector.getItems().addAll(petDetails);
        if (!petDetails.isEmpty()) {
            petSelector.setValue("All"); // Default to the first pet
            plotAllPetsData();
        }
    }
    private void populateCharts(String petDetails) {
        if (petDetails.equals("All")) {
            plotAllPetsData();
        } else {
            String petName = petDetails.split(" \\(")[0];
            String petIdQuery = "SELECT PetID FROM pet WHERE PetName = ?";
            List<Integer> petIds = DBConnector.query(petIdQuery, new String[]{petName}, resultSet -> {
                try {
                    return resultSet.getInt("PetID");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            if (petIds.isEmpty()) return;

            int petID = petIds.get(0);

            // Query to get the latest record date across all pets
            String latestDateQuery = "SELECT MAX(RecordDate) AS LatestDate FROM measurements";
            String latestDate = DBConnector.query(latestDateQuery, new String[]{}, resultSet -> {
                try {
                    return resultSet.getString("LatestDate");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }).get(0); // Get the latest date as a string

            if (latestDate == null) {
                return;
            }

            // Calculate the start date (10 months before the latest date)
            LocalDate latestLocalDate = LocalDate.parse(latestDate);
            LocalDate startDate = latestLocalDate.minusMonths(10);
            String startDateStr = startDate.toString(); // Convert back to string

            // Queries to fetch the latest 10 records within the last 10 months from the latest record date
            String weightQuery = """
        SELECT RecordDate, Value 
        FROM measurements 
        WHERE PetID = ? AND MeasurementType = 'weight' AND RecordDate >= ?
        ORDER BY RecordDate ASC
        LIMIT 10
        """;

            List<XYChart.Data<String, Number>> weightData = DBConnector.query(weightQuery, new String[]{String.valueOf(petID), startDateStr}, resultSet -> {
                try {
                    String date = resultSet.getString("RecordDate");
                    double weight = resultSet.getDouble("Value");
                    return new XYChart.Data<>(date, weight);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            String heightQuery = """
        SELECT RecordDate, Value 
        FROM measurements 
        WHERE PetID = ? AND MeasurementType = 'height' AND RecordDate >= ?
        ORDER BY RecordDate ASC
        LIMIT 10
        """;

            List<XYChart.Data<String, Number>> heightData = DBConnector.query(heightQuery, new String[]{String.valueOf(petID), startDateStr}, resultSet -> {
                try {
                    String date = resultSet.getString("RecordDate");
                    double height = resultSet.getDouble("Value");
                    return new XYChart.Data<>(date, height);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            weightPane.getChildren().clear();
            heightPane.getChildren().clear();

            // Create and populate weight chart
            CategoryAxis weightXAxis = new CategoryAxis();
            NumberAxis weightYAxis = new NumberAxis();
            weightYAxis.setLabel("Weight (kg)");
            AreaChart<String, Number> weightChart = new AreaChart<>(weightXAxis, weightYAxis);
            XYChart.Series<String, Number> weightSeries = new XYChart.Series<>();
            weightSeries.setName(petName + " Weight Growth");

            // Reversing the order of data to ensure earliest date is first
            weightData.sort((data1, data2) -> data1.getXValue().compareTo(data2.getXValue()));

            weightSeries.getData().addAll(weightData);
            weightChart.getData().add(weightSeries);

            // Create and populate height chart
            CategoryAxis heightXAxis = new CategoryAxis();
            NumberAxis heightYAxis = new NumberAxis();
            heightYAxis.setLabel("Height (cm)");
            AreaChart<String, Number> heightChart = new AreaChart<>(heightXAxis, heightYAxis);
            XYChart.Series<String, Number> heightSeries = new XYChart.Series<>();
            heightSeries.setName(petName + " Height Growth");

            // Reversing the order of data to ensure earliest date is first
            heightData.sort((data1, data2) -> data1.getXValue().compareTo(data2.getXValue()));

            heightSeries.getData().addAll(heightData);
            heightChart.getData().add(heightSeries);

            weightPane.getChildren().add(weightChart);
            heightPane.getChildren().add(heightChart);
        }
    }

    private void plotAllPetsData() {
        // Query to get the latest record date across all pets
        String latestDateQuery = "SELECT MAX(RecordDate) AS LatestDate FROM measurements";
        String latestDate = DBConnector.query(latestDateQuery, new String[]{}, resultSet -> {
            try {
                return resultSet.getString("LatestDate");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get(0); // Get the latest date as a string

        if (latestDate == null) {
            return;
        }

        // Calculate the start date (10 months before the latest date)
        LocalDate latestLocalDate = LocalDate.parse(latestDate);
        LocalDate startDate = latestLocalDate.minusMonths(10);
        String startDateStr = startDate.toString(); // Convert back to string

        // Fetching all pets' data
        String petQuery = "SELECT PetID, PetName FROM pet";
        List<String[]> petDetails = DBConnector.query(petQuery, new String[]{}, resultSet -> {
            try {
                int petId = resultSet.getInt("PetID");
                String petName = resultSet.getString("PetName");
                return new String[]{String.valueOf(petId), petName};
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });

        weightPane.getChildren().clear();
        heightPane.getChildren().clear();

        CategoryAxis weightXAxis = new CategoryAxis();
        NumberAxis weightYAxis = new NumberAxis();
        weightYAxis.setLabel("Weight (kg)");
        AreaChart<String, Number> weightChart = new AreaChart<>(weightXAxis, weightYAxis);

        CategoryAxis heightXAxis = new CategoryAxis();
        NumberAxis heightYAxis = new NumberAxis();
        heightYAxis.setLabel("Height (cm)");
        AreaChart<String, Number> heightChart = new AreaChart<>(heightXAxis, heightYAxis);

        for (String[] petDetail : petDetails) {
            int petId = Integer.parseInt(petDetail[0]);
            String petName = petDetail[1];

            // Queries to fetch the weight and height data for the last 10 months
            String weightQuery = """
        SELECT RecordDate, Value 
        FROM measurements 
        WHERE PetID = ? AND MeasurementType = 'weight' AND RecordDate >= ?
        ORDER BY RecordDate ASC
        """;

            List<XYChart.Data<String, Number>> petWeightData = DBConnector.query(weightQuery, new String[]{String.valueOf(petId), startDateStr}, resultSet -> {
                try {
                    String date = resultSet.getString("RecordDate");
                    double weight = resultSet.getDouble("Value");
                    return new XYChart.Data<>(date, weight);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            String heightQuery = """
        SELECT RecordDate, Value 
        FROM measurements 
        WHERE PetID = ? AND MeasurementType = 'height' AND RecordDate >= ?
        ORDER BY RecordDate ASC
        """;

            List<XYChart.Data<String, Number>> petHeightData = DBConnector.query(heightQuery, new String[]{String.valueOf(petId), startDateStr}, resultSet -> {
                try {
                    String date = resultSet.getString("RecordDate");
                    double height = resultSet.getDouble("Value");
                    return new XYChart.Data<>(date, height);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            XYChart.Series<String, Number> weightSeries = new XYChart.Series<>();
            weightSeries.setName(petName);
            weightSeries.getData().addAll(petWeightData);
            weightChart.getData().add(weightSeries);

            XYChart.Series<String, Number> heightSeries = new XYChart.Series<>();
            heightSeries.setName(petName);
            heightSeries.getData().addAll(petHeightData);
            heightChart.getData().add(heightSeries);
        }

        weightPane.getChildren().add(weightChart);
        heightPane.getChildren().add(heightChart);
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

    public void addRecord() {
        try {
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("components/add-record-popup.fxml"));
            Scene scene = new Scene(loader.load());
            AddRecordPopupController controller = loader.getController();
            controller.setOnSubmitCallback(() -> {
                String selectedPet = petSelector.getValue();
                if ("All".equals(selectedPet)) {
                    plotAllPetsData();
                } else {
                    populateCharts(selectedPet);
                }
            });

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Add Record");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
