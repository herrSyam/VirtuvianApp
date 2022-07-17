package com.example.virtuvianapplication.model;

import java.util.ArrayList;

public class ObatSaveModel {
    private String type;
    private ArrayList<ObatDataModel> data;

    public ObatSaveModel(String type, ArrayList<ObatDataModel> data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<ObatDataModel> getData() {
        return data;
    }

    public void setData(ArrayList<ObatDataModel> data) {
        this.data = data;
    }
}
