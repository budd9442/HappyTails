package com.happytails.controllers;

import com.happytails.HappyTails;

import com.happytails.utils.DBConnector;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class AddPetController implements Initializable {

    public StackPane parent;
    public ImageView imageView;
    public GridPane avatarGrid;
    private static final String IMAGE_FOLDER = "src/main/resources/com/happytails/imagestore/default/";
    public MFXTextField nameField;
    public MFXComboBox speciesField;
    public MFXTextField weightField;
    public MFXTextField breedField;
    public MFXDatePicker dobField;
    public MFXComboBox genderField;
    public MFXTextField heightField;
    public Text errorMsg;
    public MFXProgressSpinner loadingSpinner;
    public MFXButton addPetBtn;
    private String selectedFileName = "1.dog.png";

    public void setParent(StackPane sp) {
        parent = sp;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        genderField.getItems().addAll(new String[]{"Male", "Female"});
        speciesField.getItems().addAll(new String[]{"Dog", "Cat", "Fish", "Bird", "Rabbit"});

        File folder = new File(IMAGE_FOLDER);
        File[] imageFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg"));

        if (imageFiles != null) {
            int column = 0;
            int row = 0;
            for (int i = 0; i < Math.min(imageFiles.length, 6); i++) {
                File imageFile = imageFiles[i];
                Image image = new Image(imageFile.toURI().toString());
                System.out.println(imageFile.toURI().toString());
                ImageView avatar = new ImageView(image);
                avatar.setFitHeight(80);
                avatar.setFitWidth(80);
                avatar.setPreserveRatio(true);

                // Add click event to update ImageView and filename
                avatar.setOnMouseClicked(event -> handleImageClick(event, imageFile.getName()));

                // Add avatar to GridPane
                avatarGrid.add(avatar, column, row);

                // Move to next column or row
                column++;
                if (column >= 3) {
                    column = 0;
                    row++;
                }
            }
        }

    }

    private void handleImageClick(MouseEvent event, String fileName) {
        if (fileName.equals("5.add.png")) {
            openImagePicker();
            return;
        }
        ImageView clickedImageView = (ImageView) event.getSource();
        imageView.setImage(clickedImageView.getImage());
        selectedFileName = fileName;
        System.out.println("Selected Image: " + selectedFileName);
    }

    public void backBtnClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("my-pets.fxml"));
            Parent myPetsView = loader.load();
            MyPetsController controller = loader.getController();
            controller.setParent(parent);
            parent.getChildren().clear();
            parent.getChildren().add(myPetsView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openImagePicker() {
        // Open a file chooser dialog to select an image file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        // Show the file chooser and get the selected file
        File selectedFile = fileChooser.showOpenDialog(imageView.getScene().getWindow());

        if (selectedFile != null) {
            try {
                // Load the selected image
                Image originalImage = new Image(new FileInputStream(selectedFile));

                // Determine the dimensions for the circular crop
                double diameter = Math.min(originalImage.getWidth(), originalImage.getHeight());

                // Create a canvas for the circular crop
                Canvas canvas = new Canvas(diameter, diameter);
                GraphicsContext gc = canvas.getGraphicsContext2D();

                // Fill the background with transparent color
                gc.setFill(Color.TRANSPARENT);
                gc.fillRect(0, 0, diameter, diameter);

                // Draw the circular crop
                gc.beginPath();
                gc.arc(diameter / 2, diameter / 2, diameter / 2, diameter / 2, 0, 360);
                gc.closePath();
                gc.clip();
                gc.drawImage(originalImage, (diameter - originalImage.getWidth()) / 2,
                        (diameter - originalImage.getHeight()) / 2, originalImage.getWidth(), originalImage.getHeight());

                // Convert the canvas to a WritableImage with transparent background
                WritableImage circularImage = new WritableImage((int) diameter, (int) diameter);
                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT); // Set transparent background
                canvas.snapshot(params, circularImage);

                // Display the circular image in the ImageView
                imageView.setImage(circularImage);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addPet(MouseEvent mouseEvent) throws IOException {


        if (nameField.getText().isBlank() ||
                dobField.getText().isBlank() ||
                speciesField.getText().isBlank() ||
                breedField.getText().isBlank() ||
                weightField.getText().isBlank() ||
                heightField.getText().isBlank() ||
                genderField.getText().isBlank()
        ) {
            errorMsg.setText("Please fill in all the required fields.");
            errorMsg.setVisible(true);
            return;
        }
        LocalDate selectedDate = dobField.getValue();
        if (selectedDate.isAfter(LocalDate.now())) {
            errorMsg.setText("Invalid date.");
            errorMsg.setVisible(true);
            return;
        }
        try{
            Integer.parseInt(heightField.getText());
            Integer.parseInt(weightField.getText());
        } catch (NumberFormatException e) {
            errorMsg.setText("Invalid data");
            errorMsg.setVisible(true);
            return;
        }
        errorMsg.setVisible(false);
        addPetBtn.setDisable(true);
        loadingSpinner.setVisible(true);
        // Save the circular image to the resources/imagestore folder
        File destinationDir = new File("ImageStore/");
        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }
        String picUUID = String.valueOf(UUID.randomUUID());
        File destinationFile = new File(destinationDir, picUUID + ".png");
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
        ImageIO.write(bufferedImage, "png", destinationFile);

        String query1 = "INSERT INTO pet (PetName, Species, Breed, Gender, DateOfBirth, Owner, PicURL) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        String[] params1 = new String[]{
                nameField.getText(),
                speciesField.getText(),
                breedField.getText(),
                (genderField.getText().equals("Male")) ? "M" : "F",
                dobField.getValue().toString(),
                DBConnector.currentUserID,
                picUUID + ".png"
        };
        DBConnector.executeUpdate(query1, params1);


        String query2 = "SELECT PetID FROM pet WHERE PetName = ? AND Owner = ?";

        String[] params2 = new String[]{
                nameField.getText(),
                DBConnector.currentUserID
        };

        List<String> result = DBConnector.query(query2, params2, resultSet -> {
            try {
                return resultSet.getString("PetID");
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });

        String petId = result.isEmpty() ? null : result.get(0);
        String query3 = "INSERT INTO measurements ( PetID, RecordDate, Value, MeasurementType)" +
                "VALUES (?, ?, ?, ?)";


        DBConnector.executeUpdate(query3,new String[]{petId,LocalDate.now().toString(),heightField.getText(),"height"});
        DBConnector.executeUpdate(query3,new String[]{petId,LocalDate.now().toString(), weightField.getText(),"weight"});





        Platform.runLater(() -> {
            loadingSpinner.setVisible(true);
        });

        // Wait for 5 seconds before hiding the loading spinner
        new Thread(() -> {
            try {
                // Sleep for 5 seconds (5000 milliseconds)
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Hide the loading spinner after 5 seconds
            Platform.runLater(() -> {
                loadingSpinner.setVisible(false);
                backBtnClicked(mouseEvent);
            });
        }).start();
    }


}


