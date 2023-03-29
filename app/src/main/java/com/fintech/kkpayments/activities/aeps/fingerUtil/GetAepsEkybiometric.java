package com.fintech.kkpayments.activities.aeps.fingerUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAepsEkybiometric {
    @SerializedName("bcid")
    @Expose
    private String bcid;
    @SerializedName("piddata")
    @Expose
    private String piddata;
    @SerializedName("devicesrno")
    @Expose
    private String devicesrno;
    @SerializedName("ci")
    @Expose
    private String ci;
    @SerializedName("dc")
    @Expose
    private String dc;
    @SerializedName("dpid")
    @Expose
    private String dpid;
    @SerializedName("hmac")
    @Expose
    private String hmac;
    @SerializedName("mc")
    @Expose
    private String mc;
    @SerializedName("mi")
    @Expose
    private String mi;
    @SerializedName("nmPoints")
    @Expose
    private String nmPoints;
    @SerializedName("rdsid")
    @Expose
    private String rdsid;
    @SerializedName("rdver")
    @Expose
    private String rdver;
    @SerializedName("skey")
    @Expose
    private String skey;
    @SerializedName("aadhar")
    @Expose
    private String aadhar;
    @SerializedName("primaryKeyId")
    @Expose
    private String primaryKeyId;
    @SerializedName("encodeFPTxnId")
    @Expose
    private String encodeFPTxnId;
    @SerializedName("fType")
    @Expose
    private String fType;

    public GetAepsEkybiometric() {
    }

    public String getfType() {
        return this.fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }

    public String getBcid() {
        return this.bcid;
    }

    public void setBcid(String bcid) {
        this.bcid = bcid;
    }

    public String getPiddata() {
        return this.piddata;
    }

    public void setPiddata(String piddata) {
        this.piddata = piddata;
    }

    public String getDevicesrno() {
        return this.devicesrno;
    }

    public void setDevicesrno(String devicesrno) {
        this.devicesrno = devicesrno;
    }

    public String getCi() {
        return this.ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getDc() {
        return this.dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getDpid() {
        return this.dpid;
    }

    public void setDpid(String dpid) {
        this.dpid = dpid;
    }

    public String getHmac() {
        return this.hmac;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
    }

    public String getMc() {
        return this.mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getMi() {
        return this.mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public String getNmPoints() {
        return this.nmPoints;
    }

    public void setNmPoints(String nmPoints) {
        this.nmPoints = nmPoints;
    }

    public String getRdsid() {
        return this.rdsid;
    }

    public void setRdsid(String rdsid) {
        this.rdsid = rdsid;
    }

    public String getRdver() {
        return this.rdver;
    }

    public void setRdver(String rdver) {
        this.rdver = rdver;
    }

    public String getSkey() {
        return this.skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public String getAadhar() {
        return this.aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPrimaryKeyId() {
        return this.primaryKeyId;
    }

    public void setPrimaryKeyId(String primaryKeyId) {
        this.primaryKeyId = primaryKeyId;
    }

    public String getEncodeFPTxnId() {
        return this.encodeFPTxnId;
    }

    public void setEncodeFPTxnId(String encodeFPTxnId) {
        this.encodeFPTxnId = encodeFPTxnId;
    }
}
