package com.example.virtuvianapplication.model;

import java.util.List;

public class DietModel {
    private String event;
    private List<QuestionModel> data;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public List<QuestionModel> getData() {
        return data;
    }

    public void setData(List<QuestionModel> data) {
        this.data = data;
    }
}
