package com.example.virtuvianapplication.response;

import com.example.virtuvianapplication.model.EventDietModel;

public class EventDietResponse {
    private EventDietModel[] data;

    public EventDietModel[] getData() {
        return data;
    }

    public void setData(EventDietModel[] data) {
        this.data = data;
    }
}
