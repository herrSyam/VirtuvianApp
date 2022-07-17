package com.example.virtuvianapplication.response;

import com.example.virtuvianapplication.model.QuestionModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class QuestionResponse {
    private String event;

    private QuestionModel[] data;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public QuestionModel[] getData() {
        return data;
    }

    public void setData(QuestionModel[] data) {
        this.data = data;
    }
}
