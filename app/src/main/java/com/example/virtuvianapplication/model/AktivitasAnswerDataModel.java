package com.example.virtuvianapplication.model;

public class AktivitasAnswerDataModel {
    private String user_id;
    private String activitas_id;
    private Double par;

    public AktivitasAnswerDataModel(String user_id, String activitas_id, Double par) {
        this.user_id = user_id;
        this.activitas_id = activitas_id;
        this.par = par;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getActivitas_id() {
        return activitas_id;
    }

    public void setActivitas_id(String activitas_id) {
        this.activitas_id = activitas_id;
    }

    public Double getPar() {
        return par;
    }

    public void setPar(Double par) {
        this.par = par;
    }
}
