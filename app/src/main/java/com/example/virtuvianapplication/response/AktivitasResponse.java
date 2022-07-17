package com.example.virtuvianapplication.response;

import com.example.virtuvianapplication.model.AktivitasModel;

public class AktivitasResponse {
    private AktivitasModel[] data;

    public AktivitasModel[] getData() {
        return data;
    }

    public void setData(AktivitasModel[] data) {
        this.data = data;
    }
}
