package com.example.virtuvianapplication.response;

import com.example.virtuvianapplication.model.PersonObatModel;

public class PersonObatResponse {
    private Integer responses;
    private PersonObatModel data;

    public Integer getResponses() {
        return responses;
    }

    public void setResponses(Integer responses) {
        this.responses = responses;
    }

    public PersonObatModel getData() {
        return data;
    }

    public void setData(PersonObatModel data) {
        this.data = data;
    }
}
