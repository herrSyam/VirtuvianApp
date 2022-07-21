package com.example.virtuvianapplication.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObatNotifResponse {
    @SerializedName("message")
    @Expose
    private String message;

    private obatID obatId;

    public class obatID {
        private String obat_event_id;

        public String getObat_event_id() {
            return obat_event_id;
        }

        public void setObat_event_id(String obat_event_id) {
            this.obat_event_id = obat_event_id;
        }
    }

    public obatID getObatId() {
        return obatId;
    }

    public void setObatId(obatID obatId) {
        this.obatId = obatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

