package com.example.virtuvianapplication.response;

import com.example.virtuvianapplication.model.EventAktivitasModel;

public class EventAktivitasResponse {
    private EventAktivitasModel[] data;

    public EventAktivitasModel[] getData() {
        return data;
    }

    public void setData(EventAktivitasModel[] data) {
        this.data = data;
    }
}
