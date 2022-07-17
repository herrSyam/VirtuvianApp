package com.example.virtuvianapplication.model;

import java.util.ArrayList;

public class AktivitasAnswerModel {
    private String type;
    private ArrayList<AktivitasAnswerDataModel> data;

    public AktivitasAnswerModel(String type, ArrayList<AktivitasAnswerDataModel> data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<AktivitasAnswerDataModel> getData() {
        return data;
    }

    public void setData(ArrayList<AktivitasAnswerDataModel> data) {
        this.data = data;
    }
}
