package com.example.norkholis.securegateopener.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListRegistrasi {
    @SerializedName("status")
    @Expose
    private MessageRegistrasi status;
    @SerializedName("data")
    @Expose
    private DataRegistrasi data;

    public MessageRegistrasi getStatus() {
        return status;
    }

    public void setStatus(MessageRegistrasi status) {
        this.status = status;
    }

    public DataRegistrasi getData() {
        return data;
    }

    public void setData(DataRegistrasi data) {
        this.data = data;
    }
}
