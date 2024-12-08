package com.happytails.models;

public class TodoItem {
    private String text;
    private String color;
    private boolean done;

    public TodoItem(String text, String color, boolean done) {
        this.text = text;
        this.color = color;
        this.done = done;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    public boolean isDone() {
        return done;
    }
}
