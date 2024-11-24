package com.happytails.models;

import javafx.beans.property.*;

public class TodoItem {
    private final StringProperty itemName = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final BooleanProperty completed = new SimpleBooleanProperty();
    private final StringProperty categoryIcon = new SimpleStringProperty();

    public TodoItem(String name, String catIcon) {
        this.itemName.set(name);
        this.categoryIcon.set(catIcon);
    }

    public String getItemName() {
        return itemName.get();
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public boolean isCompleted() {
        return completed.get();
    }

    public BooleanProperty completedProperty() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }

    public String getCategoryIcon() {
        return categoryIcon.get();
    }

    public StringProperty categoryIconProperty() {
        return categoryIcon;
    }
}
