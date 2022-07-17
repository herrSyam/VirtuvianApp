package com.example.virtuvianapplication.model;

public class CheckBoxModel {
    private String diet_id;
    private String question;
    private String diet_event_id;

    public CheckBoxModel(String diet_id, String question, String diet_event_id) {
        this.diet_id = diet_id;
        this.question = question;
        this.diet_event_id = diet_event_id;
    }

    public String getDiet_id() {
        return diet_id;
    }

    public void setDiet_id(String diet_id) {
        this.diet_id = diet_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDiet_event_id() {
        return diet_event_id;
    }

    public void setDiet_event_id(String diet_event_id) {
        this.diet_event_id = diet_event_id;
    }
}
