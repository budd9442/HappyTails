package com.happytails.controllers;

import com.happytails.HappyTails;
import com.happytails.models.TodoItem;
import com.happytails.utils.DBConnector;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.happytails.utils.DBConnector.getNextAppointmentDate;
import static com.happytails.utils.DBConnector.getUpcomingBirthday;
import static com.happytails.utils.Utils.getDaySuffix;

public class HomeViewController {
    public HBox petsList;
    public StackPane selectedPetImage;
    public VBox todoList;
    public Label clock;
    public Pane addTodoPanel;

    public MFXTextField todoTextField;
    public String selectedColor;
    public HBox colorBox;
    public ScrollPane todoListContainer;
    public Label clearListLabel;
    public Label addItemLabel;
    public Text addTodoErrorLabel;
    public Label todoListEmpty;
    public Label nextBirthdayLabel;
    public Label nextAppLabel;
    public Label dateLabel;
    public Label monthLabel;
    public Label yearLabel;

    @FXML
    private StackPane mainStackPane; // Reference to the StackPane in menu-view.fxml

    public void setMainStackPane(StackPane mainStackPane) {
        this.mainStackPane = mainStackPane;
    }

    @FXML
    public void initialize() throws IOException {
        addTodoPanel.setVisible(false);
        if (selectedPetImage == null) return;
        addColorBoxes();

        // Load todo list from the database
        loadTodoListFromDatabase();
        nextBirthdayLabel.setText(getUpcomingBirthday());
        nextAppLabel.setText(getNextAppointmentDate());
        setCurrentDate();
    }

    private void setCurrentDate() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Extract day, month, and year
        int day = currentDate.getDayOfMonth();
        String month = currentDate.getMonth().name(); // Full month name
        int year = currentDate.getYear();

        // Format the day with "th", "st", "nd", "rd"
        String dayFormatted = day + getDaySuffix(day);

        // Format the month to start with a capital letter
        String monthFormatted = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();

        // Set the labels
        dateLabel.setText(dayFormatted);
        monthLabel.setText(monthFormatted);
        yearLabel.setText(String.valueOf(year));
    }


    private void loadTodoListFromDatabase() throws IOException {
        // Query to retrieve the todos for the current user
        String query = "SELECT text, color, done FROM todo WHERE user_id = ? ORDER BY created_at DESC";

        // Get the current user's UUID
        String currentUserId = DBConnector.currentUserID;

        // Use the DBConnector.query method to get the list of todos
        List<TodoItem> todoItems = DBConnector.query(query, new String[]{currentUserId}, resultSet -> {
            try {
                String text = resultSet.getString("text");
                String color = resultSet.getString("color");
                boolean done = resultSet.getBoolean("done");
                return new TodoItem(text, color, done);  // Return a TodoItem object
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });

        // Add each todo item to the todoList
        for (TodoItem todoItem : todoItems) {
            if (todoItem != null) {
                todoList.getChildren().add(createTodo(todoItem,false));
            }
        }

        todoListEmpty.setVisible(todoList.getChildren().isEmpty()); // hide todo empty message

    }




    public Parent createTodo(TodoItem item, boolean saveToDb) throws IOException {

        System.out.println("Creating todo");
        FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("components/todo-item.fxml"));
        Parent todoItem = loader.load();

        TodoItemController controller = loader.getController();
        controller.setData(item.getText(), item.getColor(), item.isDone());
        if(saveToDb)storeTodoInDatabase(item.getText(), item.getColor(), item.isDone());

        // Attach a delete event handler
        Node deleteButtonNode = todoItem.lookup("#deleteBtn");
        if (deleteButtonNode instanceof ImageView deleteButton) {
            deleteButton.setOnMouseClicked(event -> {
                todoList.getChildren().remove(todoItem);
                // Optionally remove from the database when deleting
                deleteTodoFromDatabase(item.getText(), item.getColor(), item.isDone());
                todoListEmpty.setVisible(todoList.getChildren().isEmpty());
            });
        }

        return todoItem;
    }

    private void storeTodoInDatabase(String text, String color, boolean done) {
        String insertTodoQuery = """
    INSERT INTO todo (user_id, text, color) 
    VALUES (?, ?, ?)
    """;

        // Get the current user's UUID
        String currentUserId = DBConnector.currentUserID;

        // Execute the query to insert the todo
        DBConnector.executeUpdate(insertTodoQuery, new String[]{currentUserId, text, color});
    }

    private void deleteTodoFromDatabase(String text, String color, boolean done) {
        String deleteTodoQuery = """
    DELETE FROM todo 
    WHERE user_id = ? AND text = ?
    """;

        String currentUserId = DBConnector.currentUserID;

        System.out.println( DBConnector.executeUpdate(deleteTodoQuery, new String[]{currentUserId, text}));

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
            DBConnector.executeUpdate("DELETE from todo where user_id = ? ",new String[]{DBConnector.currentUserID});
        }else{
            addTodoErrorLabel.setText("");
            addItemLabel.setText("Add Item");
            clearListLabel.setText("Clear List");
            todoListContainer.setVisible(true);
            addTodoPanel.setVisible(false);
        }
        todoListEmpty.setVisible(todoList.getChildren().isEmpty());

    }

    public void addTodoClicked(MouseEvent mouseEvent) throws IOException {
        todoListEmpty.setVisible(false);
        String query = "SELECT COUNT(*) FROM todo WHERE user_id = ? AND text = ?";
        String currentUserId = DBConnector.currentUserID;

        List<Integer> results = DBConnector.query(query, new String[]{currentUserId, todoTextField.getText()}, resultSet -> {
            try {
                return resultSet.getInt(1); // Return the count of matching todos
            } catch (SQLException e) {
                return 0;
            }
        });

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
            if(results.get(0) > 0){
                addTodoErrorLabel.setText("Todo already exists");
                return;
            }
            addTodoErrorLabel.setText("");
            todoList.getChildren().addFirst(createTodo(new TodoItem(todoTextField.getText(),selectedColor,false),true));
            todoTextField.clear();
            todoListContainer.setVisible(true);
            addTodoPanel.setVisible(false);
            addItemLabel.setText("Add Item");
            clearListLabel.setText("Clear List");

        }


    }

    public void onMedicalHistoryClick(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HappyTails.class.getResource("medical-history-view.fxml"));
            Parent myPetsView = loader.load();
            MedicalHistoryController controller = loader.getController();
            controller.setParent(mainStackPane);
            mainStackPane.getChildren().clear();
            mainStackPane.getChildren().add(myPetsView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
