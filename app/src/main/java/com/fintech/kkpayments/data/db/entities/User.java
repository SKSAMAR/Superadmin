package com.fintech.kkpayments.data.db.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "user")
public class User {

    public  int CURRENT_USER_ID = 0;
    @PrimaryKey(autoGenerate = false)
    public int uId = CURRENT_USER_ID;
    public String email;
    public String mobile;
    public String password;
    public String name;
    public String lastname;
    public String ownerid;
    public String ownerstatus;
    public String userstatus;
    public String token;
    public String id;
    public String mainbalance;
    public String aepsbalance;
    public String settlementbalance;
    public String matmbalance;
    public String userstatusname;
    public String pin;
    public String address;
    public String us_status;
    public String partner_id;

    public User(String email, String mobile, String password, String name, String lastname, String ownerid, String ownerstatus, String userstatus, String token, String id, String mainbalance, String aepsbalance, String settlementbalance, String matmbalance, String userstatusname, String pin, String address, String us_status, String partner_id) {
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.ownerid = ownerid;
        this.ownerstatus = ownerstatus;
        this.userstatus = userstatus;
        this.token = token;
        this.id = id;
        this.mainbalance = mainbalance;
        this.aepsbalance = aepsbalance;
        this.settlementbalance = settlementbalance;
        this.matmbalance = matmbalance;
        this.userstatusname = userstatusname;
        this.pin = pin;
        this.address = address;
        this.us_status = us_status;
        this.partner_id = partner_id;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getUs_status() {
        return us_status;
    }

    public void setUs_status(String us_status) {
        this.us_status = us_status;
    }

    public String getSettlementbalance() {
        return settlementbalance;
    }

    public void setSettlementbalance(String settlementbalance) {
        this.settlementbalance = settlementbalance;
    }

    public String getMatmbalance() {
        return matmbalance;
    }

    public void setMatmbalance(String matmbalance) {
        this.matmbalance = matmbalance;
    }

    public int getCURRENT_USER_ID() {
        return CURRENT_USER_ID;
    }

    public void setCURRENT_USER_ID(int CURRENT_USER_ID) {
        this.CURRENT_USER_ID = CURRENT_USER_ID;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }

    public String getOwnerstatus() {
        return ownerstatus;
    }

    public void setOwnerstatus(String ownerstatus) {
        this.ownerstatus = ownerstatus;
    }

    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMainbalance() {
        return mainbalance;
    }

    public void setMainbalance(String mainbalance) {
        this.mainbalance = mainbalance;
    }

    public String getAepsbalance() {
        return aepsbalance;
    }

    public void setAepsbalance(String aepsbalance) {
        this.aepsbalance = aepsbalance;
    }

    public String getUserstatusname() {
        return userstatusname;
    }

    public void setUserstatusname(String userstatusname) {
        this.userstatusname = userstatusname;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
