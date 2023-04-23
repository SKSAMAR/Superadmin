package com.fintech.superadmin.data.browseplan;

import com.google.gson.annotations.SerializedName;

public class Plan {
    @SerializedName("desc")
    public String desc;
    @SerializedName("last_update")
    public String lastUpdate;
    @SerializedName("rs")
    public String rs;
    @SerializedName("validity")
    public String validity;
}
