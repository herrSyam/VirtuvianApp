package com.example.virtuvianapplication.model;

public class CheckBoxAktivitasModel {
    private String aktivitas_id;
    private Double par;

    public CheckBoxAktivitasModel(String aktivitas_id, Double par) {
        this.aktivitas_id = aktivitas_id;
        this.par = par;
    }


    public String getAktivitas_id() {
        return aktivitas_id;
    }

    public void setAktivitas_id(String aktivitas_id) {
        this.aktivitas_id = aktivitas_id;
    }

    public Double getPar() {
        return par;
    }

    public void setPar(Double par) {
        this.par = par;
    }
}
