package com.happytails.controllers;

import com.happytails.HappyTails;
import io.github.palexdev.materialfx.controls.MFXCheckListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class HomeViewController {
    public HBox petsList;
    public StackPane selectedPetImage;
    public VBox todoList;

    @FXML
    private StackPane mainStackPane; // Reference to the StackPane in menu-view.fxml

//    public void setMainStackPane(StackPane mainStackPane) {
//        this.mainStackPane = mainStackPane;
//    }

    @FXML
    public void initialize() throws IOException {
        if(selectedPetImage == null) return;
//        selectedPetImage.setClip(new Circle(100, 100, 100));
//        petsList.setSpacing(10); // Distance in pixels between images
//        petsList.setPadding(new Insets(10, 0, 5, 0)); // Optional: padding around the HBox
//
////        todoList.getItems().add("Shower");
////        todoList.getItems().add("Go for a walk");
////        todoList.getItems().add("PLaytime");
//
//
//        // URLs for your round images
//        String[] imageUrls = {
//                "/com/happytails/images/pet1.png",
//                "/com/happytails/images/pet2.png",
//                "/com/happytails/images/pet3.png",
//                "/com/happytails/images/pet4.png"
//        };
//
//        for (String url : imageUrls) {
//
//            // Create an ImageView for each image
//            ImageView imageView = new ImageView(new Image(HappyTails.class.getResource("img/dogg.jpg").toString()));
//            imageView.setFitWidth(70); // Match wrapper size
//            imageView.setFitHeight(70);
//            imageView.setPreserveRatio(false); // Allow stretching to fill completely
//
//            // Create a wrapper pane (e.g., StackPane) for circular background
//            StackPane wrapper = new StackPane(imageView);
//            wrapper.setPrefSize(70, 70); // Size of the circular background
//            wrapper.setStyle(
//                    "-fx-background-color: #D4BEE4; " + // Background color
//                            "-fx-background-radius: 35; " +    // Half of prefSize for circular shape
//                            "-fx-padding: 0;"                  // No padding, image fills completely
//            );
//
//            // Clip the StackPane to a circle to ensure circular appearance
//            Circle clip = new Circle(35, 35, 35); // Center x, y, and radius
//            wrapper.setClip(clip);
//
//            // Add wrapper to the HBox
//            petsList.getChildren().add(wrapper);
//        }


        // load todo list

        for (int i = 0; i < 10; i++) {

            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("components/todo-item.fxml"));
            Parent todoItem = loader.load();
            //PetItemController controller = loader.getController();
            //controller.setData(pet.getPetName(),pet.getSpecies(),pet.getBreed(),pet.getDob());


            // Add petItem to the GridPane at the current column and row
            todoList.getChildren().add(todoItem);

        }

    }




    public void onGrowthTrackerClick(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            // Load the growth-tracker.fxml
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("growth-tracker.fxml"));
            Parent growthTrackerView = loader.load();
            GrowthTrackerController controller = loader.getController();
            controller.setParent(mainStackPane);

            // Replace the current child of the StackPane
            mainStackPane.getChildren().clear();
            mainStackPane.getChildren().add(growthTrackerView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onMyPetsClick(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("my-pets.fxml"));
            Parent myPetsView = loader.load();
            MyPetsController controller = loader.getController();
            controller.setParent(mainStackPane);
            mainStackPane.getChildren().clear();
            mainStackPane.getChildren().add(myPetsView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onVaccinationsClick(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("vaccinations-view.fxml"));
            Parent myPetsView = loader.load();
            VaccinationsController controller = loader.getController();
            controller.setParent(mainStackPane);
            mainStackPane.getChildren().clear();
            mainStackPane.getChildren().add(myPetsView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
