package com.example.foodtruckclient.network.foodtruckapi.model;

import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("message")
    private String apiMessage;

    public String getApiMessage() {
        return apiMessage;
    }

    public void setApiMessage(String apiMessage) {
        this.apiMessage = apiMessage;
    }
}
