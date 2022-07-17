package com.example.virtuvianapplication.response;

import com.example.virtuvianapplication.model.NotificationModel;

public class NotificationResponse {
    private NotificationModel[] data;

    public NotificationModel[] getData() {
        return data;
    }

    public void setData(NotificationModel[] data) {
        this.data = data;
    }
}
