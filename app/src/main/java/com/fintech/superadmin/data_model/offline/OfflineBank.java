package com.fintech.superadmin.data_model.offline;

import com.google.gson.annotations.SerializedName;

public class OfflineBank {

    @SerializedName("ID")
    public String ID;
    @SerializedName("BANK_NAME")
    public String BANK_NAME;

    @Override
    public String toString() {
        return BANK_NAME;
    }
}
