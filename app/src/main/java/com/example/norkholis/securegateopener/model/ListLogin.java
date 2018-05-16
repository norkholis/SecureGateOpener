package com.example.norkholis.securegateopener.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListLogin {
    @SerializedName("status")
    @Expose
    private MessageLogin status;
    @SerializedName("data")
    @Expose
    private List<DataUserLogin> data = null;

    public MessageLogin getStatus() {
        return status;
    }

    public void setStatus(MessageLogin status) {
        this.status = status;
    }

    public List<DataUserLogin> getData() {
        return data;
    }

    public void setData(List<DataUserLogin> data) {
        this.data = data;
    }
}
