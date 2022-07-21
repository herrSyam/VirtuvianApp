package com.example.virtuvianapplication.response;

import com.example.virtuvianapplication.model.Result;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotifResponse {

    @SerializedName("multicast_id")
    @Expose
    private String multicast_id;

    @SerializedName("success")
    @Expose
    private Integer success;

    @SerializedName("failure")
    @Expose
    private Integer failure;

    @SerializedName("canonical_ids")
    @Expose
    private Integer canonical_ids;

    @SerializedName("results")
    @Expose
    private Result results;

    public String getMulticast_id() {
        return multicast_id;
    }

    public void setMulticast_id(String multicast_id) {
        this.multicast_id = multicast_id;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getFailure() {
        return failure;
    }

    public void setFailure(Integer failure) {
        this.failure = failure;
    }

    public Integer getCanonical_ids() {
        return canonical_ids;
    }

    public void setCanonical_ids(Integer canonical_ids) {
        this.canonical_ids = canonical_ids;
    }

    public Result getResults() {
        return results;
    }

    public void setResults(Result results) {
        this.results = results;
    }
}
