package com.fintech.superadmin.data.browseplan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Info {
    @SerializedName("COMBO")
    public List<Plan> cOMBO;
    @SerializedName("FULLTT")
    public List<Plan> fULLTT;
    @SerializedName("2G")
    public List<Plan> g;
    @SerializedName("3G/4G")
    public List<Plan> g4G;
    @SerializedName("RATE CUTTER")
    public List<Plan> rATECUTTER;
    @SerializedName("Romaing")
    public List<Plan> romaing;
    @SerializedName("SMS")
    public List<Plan> sMS;
    @SerializedName("TOPUP")
    public List<Plan> tOPUP;

    @SerializedName("LOCAL")
    public List<Plan> lOCAL;

    @SerializedName("OTHER")
    public List<Plan> oTher;
}
