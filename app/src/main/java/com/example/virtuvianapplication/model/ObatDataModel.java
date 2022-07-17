package com.example.virtuvianapplication.model;

public class ObatDataModel {
    private String user_id;
    private String obat_event_id;

    public ObatDataModel(String user_id, String obat_event_id) {
        this.user_id = user_id;
        this.obat_event_id = obat_event_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getObat_event_id() {
        return obat_event_id;
    }

    public void setObat_event_id(String obat_event_id) {
        this.obat_event_id = obat_event_id;
    }
}
