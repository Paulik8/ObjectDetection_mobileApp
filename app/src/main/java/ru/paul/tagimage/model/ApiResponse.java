package ru.paul.tagimage.model;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("response")
    private Integer response;

    public ApiResponse(Integer response) {
        this.response = response;
    }

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }
}
