package com.fintech.prepe.data.browseplan;

import com.google.gson.annotations.SerializedName;

public class BrowsePlanResponse {
    @SerializedName("info")
    public Info info;
    @SerializedName("message")
    public String message;
    @SerializedName("response_code")
    public Integer responseCode;
    @SerializedName("status")
    public Boolean status;

    @Override
    public String toString() {
        return "BrowsePlanResponse{" +
                "info=" + info +
                ", message='" + message + '\'' +
                ", responseCode=" + responseCode +
                ", status=" + status +
                '}';
    }
}
