package com.happytails.controllers;

import com.happytails.HappyTails;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class VaccinationsController implements Initializable {


    public VBox vaccinesList;
    public ImageView selectedPetImage;
    public MFXComboBox petSelector;
    public ImageView noVacsImg;
    @FXML
    private Label nameLabel;

    public void backBtnClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("home-view.fxml"));
            Parent view = loader.load();
            parent.getChildren().setAll(view); // Replace current content
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StackPane parent;

    public void setParent(StackPane sp) {
        parent = sp;
    }

    public void addRecordBtnClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("components/vaccine-item.fxml"));
        Parent vacItem = loader.load();
        //PetItemController controller = loader.getController();
        //controller.setData(pet.getPetName(),pet.getSpecies(),pet.getBreed(),pet.getDob());


        // Add petItem to the GridPane at the current column and row
        vaccinesList.getChildren().add(vacItem);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedPetImage.setClip(new Circle(100, 100, 100));
        petSelector.setItems(FXCollections.observableArrayList("Dog 1", "Dog 2", "Dog 3"));




        // URLs for your round images
        String[] imageUrls = {
                "/com/happytails/images/pet1.png",
                "/com/happytails/images/pet2.png",
                "/com/happytails/images/pet3.png",
                "/com/happytails/images/pet4.png"
        };
//
//        for (String imageUrl : imageUrls) {
//
//            // Create an ImageView for each image
//            ImageView imageView = new ImageView(new Image(HappyTails.class.getResource("img/dogg.jpg").toString()));
//            imageView.setFitWidth(70); // Match wrapper size
//            imageView.setFitHeight(70);
//            imageView.setPreserveRatio(false); // Allow stretching to fill completely
//
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
//            petList.getChildren().add(wrapper);
    }

    public void onSelect(ActionEvent actionEvent) {
        noVacsImg.setVisible(false);
    }
}
