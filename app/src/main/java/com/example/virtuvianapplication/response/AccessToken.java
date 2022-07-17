package com.example.virtuvianapplication.response;

import com.example.virtuvianapplication.model.UserAccessToken;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessToken {
    @SerializedName("responses")
    @Expose
    private Integer responses;
    private UserAccessToken[] data;

    public Integer getResponses() {
        return responses;
    }

    public void setResponses(Integer responses) {
        this.responses = responses;
    }

    public UserAccessToken[] getData() {
        return data;
    }

    public void setData(UserAccessToken[] data) {
        this.data = data;
    }
}
