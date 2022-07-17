package com.example.virtuvianapplication.model;

import java.util.ArrayList;

public class QuestionModelAnswer {
    private String type;
    private ArrayList<QuestionModelAnswerData> data;

    public QuestionModelAnswer(String type, ArrayList<QuestionModelAnswerData> data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<QuestionModelAnswerData> getData() {
        return data;
    }

    public void setData(ArrayList<QuestionModelAnswerData> data) {
        this.data = data;
    }
}
