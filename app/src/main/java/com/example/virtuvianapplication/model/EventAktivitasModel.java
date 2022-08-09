package com.example.virtuvianapplication.model;

public class EventAktivitasModel {
    private String user_id;
    private Double total;
    private String aktivitas_status;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getAktivitas_status() {
        return aktivitas_status;
    }

    public void setAktivitas_status(String aktivitas_status) {
        this.aktivitas_status = aktivitas_status;
    }
}
