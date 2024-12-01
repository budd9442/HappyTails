package com.happytails.controllers;

import com.happytails.HappyTails;
import io.github.palexdev.materialfx.controls.MFXCheckListView;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HomeViewController {
    public HBox petsList;
    public StackPane selectedPetImage;
    public VBox todoList;
    public Label clock;
    public Pane addTodoPanel;
    public MFXComboBox colorPicker;
    public MFXTextField todoTextField;
    public String selectedColor;
    public HBox colorBox;
    public ScrollPane todoListContainer;
    public Label clearListLabel;
    public Label addItemLabel;
    public Text addTodoErrorLabel;

    @FXML
    private StackPane mainStackPane; // Reference to the StackPane in menu-view.fxml

    public void setMainStackPane(StackPane mainStackPane) {
        this.mainStackPane = mainStackPane;
    }

    @FXML
    public void initialize() throws IOException {
        addTodoPanel.setVisible(false);
        if(selectedPetImage == null) return;
        addColorBoxes();


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

        for (int i = 0; i < 3; i++) {

            todoList.getChildren().add(createTodo("Sample Todo Item","#FFCCEA",false));

        }

    }

    public  Parent createTodo(String text, String color, boolean done) throws IOException {
        FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("components/todo-item.fxml"));
        Parent todoItem = loader.load();
        //PetItemController controller = loader.getController();
        //controller.setData(pet.getPetName(),pet.getSpecies(),pet.getBreed(),pet.getDob());


        // Retrieve the controller for the FXML
        TodoItemController controller = loader.getController();
        controller.setData(text,color,done);

        // Attach a delete event handler
        Node deleteButtonNode = todoItem.lookup("#deleteBtn");
        if (deleteButtonNode instanceof ImageView deleteButton) {
            deleteButton.setOnMouseClicked(event -> {
                todoList.getChildren().remove(todoItem);
            });
        }
        return  todoItem;
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


    private void addColorBoxes() {
        // Define an array of pastel colors
        String[] pastelColors = {
                "#FFCCEA", "#FFD8B1", "#FFF3BF",
                "#D4F4DD", "#CCF1FF", "#DCCEFF", "#F2CEFF"
        };

        // Loop through colors and add boxes
        for (String color : pastelColors) {
            StackPane colorBoxItem = createColorBox(color);
            colorBox.getChildren().add(colorBoxItem);
        }
    }

    private StackPane createColorBox(String color) {
        // Create a StackPane for the color box
        StackPane colorBoxItem = new StackPane();
        colorBoxItem.setPrefSize(32, 32);
        colorBoxItem.setStyle(
                "-fx-background-color: " + color + "; " +
                        "-fx-background-radius: 16;" // Rounded corners (half of size)
        );

        // Add a click event to handle selection
        colorBoxItem.setOnMouseClicked(event -> {
            // Update selectedColor
            selectedColor = color;

            // Remove outline from all color boxes
            for (Node node : colorBox.getChildren()) {
                node.setStyle(
                        node.getStyle().replace("-fx-border-color: white;", "")
                                .replace("-fx-border-width: 2;", "")
                                .replace("-fx-border-radius: 16;", "") // Remove border radius
                );
            }

            // Add outline with a curved border to the selected box
            colorBoxItem.setStyle(
                    colorBoxItem.getStyle() +
                            "-fx-border-color: white; " +
                            "-fx-border-width: 2; " +
                            "-fx-border-radius: 16;" // Matches the corner radius
            );
        });

        return colorBoxItem;
    }

    public void clearListClicked(MouseEvent mouseEvent) {
        if(clearListLabel.getText().equals("Clear List")){
            todoList.getChildren().clear();
        }else{
            addItemLabel.setText("Add Item");
            clearListLabel.setText("Clear List");
            todoListContainer.setVisible(true);
            addTodoPanel.setVisible(false);
        }
    }

    public void addTodoClicked(MouseEvent mouseEvent) throws IOException {
        if(addItemLabel.getText().equals("Add Item")){
            addItemLabel.setText("Confirm");
            clearListLabel.setText("Cancel");
            todoListContainer.setVisible(false);
            addTodoPanel.setVisible(true);
        }else{
            if(selectedColor== null ){
                addTodoErrorLabel.setText("Please select a color");
                return;
            }
            if(todoTextField.getText().isBlank() ){
                addTodoErrorLabel.setText("Please enter todo text");
                return;
            }
            addTodoErrorLabel.setText("");
            todoList.getChildren().addFirst(createTodo(todoTextField.getText(),selectedColor,false));
            todoTextField.clear();
            todoListContainer.setVisible(true);
            addTodoPanel.setVisible(false);
            addItemLabel.setText("Add Item");
            clearListLabel.setText("Clear List");

        }

        
    }
}
