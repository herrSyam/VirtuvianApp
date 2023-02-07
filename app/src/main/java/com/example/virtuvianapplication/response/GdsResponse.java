package com.example.virtuvianapplication.response;

import com.example.virtuvianapplication.model.GdsModel;

public class GdsResponse {
    private GdsModel[] data;

    public GdsModel[] getData() {
        return data;
    }

    public void setData(GdsModel[] data) {
        this.data = data;
    }
}
