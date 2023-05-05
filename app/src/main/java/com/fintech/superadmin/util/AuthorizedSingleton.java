package com.fintech.superadmin.util;

public class AuthorizedSingleton {
    private String cp_id;
    private String phone1;
    private String emailid;
    private String logourl;
    private String companyname;
    private String Message;
    private String StatusCode;
    private String BcId = "";
    private String PhoneBc = "";
    private String EmailidBc = "";
    private String UserId = "";
    private String secretkey = "";
    private String saltkey = "";
    private String androidlogourl;
    private String exstingcpid;
    private String existingcpphone;
    private String existingcpemail;
    private String existingcplogourl;
    private String existingcpcompanyname;
    private String newcpid;
    private String newcpphone;
    private String newcpemail;
    private String newcplogourl;
    private String newcpcompanyname;
    private String maxAmount;
    private String minAmount;
    private String pipe1;
    private String pipe2;
    private String pipe3;
    private String isequitaslive;
    private String isicicilive;
    private String iskotaklive;
    private String deviceid;
    private String device_sno;
    private String latitude;
    private String longitude;
    private String minLimit;
    private String maxLimit;
    private String branding;
    private String biopidtxn;
    private String ekycpid;
    public static AuthorizedSingleton ourInstance = new AuthorizedSingleton();
    private String bc_f_name;

    public static AuthorizedSingleton getInstance() {
        if (ourInstance == null) {
            ourInstance = new AuthorizedSingleton();
        }

        return ourInstance;
    }

    public AuthorizedSingleton() {
    }

    public String getBranding() {
        return this.branding;
    }

    public void setBranding(String branding) {
        this.branding = branding;
    }

    public void setDevice_sno(String device_sno) {
        this.device_sno = device_sno;
    }

    public String getDevice_sno() {
        return this.device_sno;
    }

    public String getDeviceid() {
        return this.deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getPipe3() {
        return this.pipe3;
    }

    public void setPipe3(String pipe3) {
        this.pipe3 = pipe3;
    }

    public void setIskotaklive(String iskotaklive) {
        this.iskotaklive = iskotaklive;
    }

    public String getIskotaklive() {
        return this.iskotaklive;
    }

    public String getPipe1() {
        return this.pipe1;
    }

    public void setPipe1(String pipe1) {
        this.pipe1 = pipe1;
    }

    public void setPipe2(String pipe2) {
        this.pipe2 = pipe2;
    }

    public String getBc_f_name() {
        return this.bc_f_name;
    }

    public void setBc_f_name(String bc_f_name) {
        this.bc_f_name = bc_f_name;
    }

    public String getPipe2() {
        return this.pipe2;
    }

    public String getIsequitaslive() {
        return this.isequitaslive;
    }

    public void setIsequitaslive(String isequitaslive) {
        this.isequitaslive = isequitaslive;
    }

    public void setIsicicilive(String isicicilive) {
        this.isicicilive = isicicilive;
    }

    public String getIsicicilive() {
        return this.isicicilive;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getMinAmount() {
        return this.minAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getMaxAmount() {
        return this.maxAmount;
    }

    public String getExstingcpid() {
        return this.exstingcpid;
    }

    public void setExstingcpid(String exstingcpid) {
        this.exstingcpid = exstingcpid;
    }

    public String getExistingcpphone() {
        return this.existingcpphone;
    }

    public void setExistingcpphone(String existingcpphone) {
        this.existingcpphone = existingcpphone;
    }

    public String getExistingcpemail() {
        return this.existingcpemail;
    }

    public void setExistingcpemail(String existingcpemail) {
        this.existingcpemail = existingcpemail;
    }

    public String getExistingcplogourl() {
        return this.existingcplogourl;
    }

    public void setExistingcplogourl(String existingcplogourl) {
        this.existingcplogourl = existingcplogourl;
    }

    public String getExistingcpcompanyname() {
        return this.existingcpcompanyname;
    }

    public void setExistingcpcompanyname(String existingcpcompanyname) {
        this.existingcpcompanyname = existingcpcompanyname;
    }

    public String getNewcpid() {
        return this.newcpid;
    }

    public void setNewcpid(String newcpid) {
        this.newcpid = newcpid;
    }

    public String getNewcpphone() {
        return this.newcpphone;
    }

    public void setNewcpphone(String newcpphone) {
        this.newcpphone = newcpphone;
    }

    public String getNewcpemail() {
        return this.newcpemail;
    }

    public void setNewcpemail(String newcpemail) {
        this.newcpemail = newcpemail;
    }

    public String getNewcplogourl() {
        return this.newcplogourl;
    }

    public void setNewcplogourl(String newcplogourl) {
        this.newcplogourl = newcplogourl;
    }

    public String getNewcpcompanyname() {
        return this.newcpcompanyname;
    }

    public void setNewcpcompanyname(String newcpcompanyname) {
        this.newcpcompanyname = newcpcompanyname;
    }

    public String getSecretkey() {
        return this.secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    public String getSaltkey() {
        return this.saltkey;
    }

    public void setSaltkey(String saltkey) {
        this.saltkey = saltkey;
    }

    public String getCp_id() {
        return this.cp_id;
    }

    public void setCp_id(String cp_id) {
        this.cp_id = cp_id;
    }

    public String getPhone1() {
        return this.phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getUserId() {
        return this.UserId;
    }

    public void setUserId(String userId) {
        this.UserId = userId;
    }

    public String getEmailid() {
        return this.emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getLogourl() {
        return this.logourl;
    }

    public void setLogourl(String logourl) {
        this.logourl = logourl;
    }

    public String getCompanyname() {
        return this.companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getMessage() {
        return this.Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public String getStatusCode() {
        return this.StatusCode;
    }

    public void setStatusCode(String statusCode) {
        this.StatusCode = statusCode;
    }

    public String getBcId() {
        return this.BcId;
    }

    public void setBcId(String bcId) {
        this.BcId = bcId;
    }

    public String getPhoneBc() {
        return this.PhoneBc;
    }

    public void setPhoneBc(String phoneBc) {
        this.PhoneBc = phoneBc;
    }

    public String getEmailidBc() {
        return this.EmailidBc;
    }

    public void setEmailidBc(String emailidBc) {
        this.EmailidBc = emailidBc;
    }

    public String getAndroidlogourl() {
        return this.androidlogourl;
    }

    public void setAndroidlogourl(String androidlogourl) {
        this.androidlogourl = androidlogourl;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMinLimit() {
        return this.minLimit;
    }

    public void setMinLimit(String minLimit) {
        this.minLimit = minLimit;
    }

    public String getMaxLimit() {
        return this.maxLimit;
    }

    public void setMaxLimit(String maxLimit) {
        this.maxLimit = maxLimit;
    }

    public String getBiopidtxn() {
        return this.biopidtxn;
    }

    public void setBiopidtxn(String biopidtxn) {
        this.biopidtxn = biopidtxn;
    }

    public String getEkycpid() {
        return this.ekycpid;
    }

    public void setEkycpid(String ekycpid) {
        this.ekycpid = ekycpid;
    }
}
