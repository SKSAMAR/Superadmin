package com.fintech.superadmin.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "paysprint_api")
public class PaySprintApi {

    public  int CURRENT_USER_ID = 0;

    @PrimaryKey(autoGenerate = false)
    public int uId = CURRENT_USER_ID;

    public String id;
    public String owner;
    public String ownerid;
    public String partnerid;
    public String merchantcode;
    public String firm;
    public String jwtkey;
    public String authorisedkey;
    public String status;
    public String key;
    public String keyiv;
    public String date;

    public PaySprintApi(String id, String owner, String ownerid, String partnerid, String merchantcode, String firm, String jwtkey, String authorisedkey, String status, String key, String keyiv, String date) {
        this.id = id;
        this.owner = owner;
        this.ownerid = ownerid;
        this.partnerid = partnerid;
        this.merchantcode = merchantcode;
        this.firm = firm;
        this.jwtkey = jwtkey;
        this.authorisedkey = authorisedkey;
        this.status = status;
        this.key = key;
        this.keyiv = keyiv;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getMerchantcode() {
        return merchantcode;
    }

    public void setMerchantcode(String merchantcode) {
        this.merchantcode = merchantcode;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getJwtkey() {
        return jwtkey;
    }

    public void setJwtkey(String jwtkey) {
        this.jwtkey = jwtkey;
    }

    public String getAuthorisedkey() {
        return authorisedkey;
    }

    public void setAuthorisedkey(String authorisedkey) {
        this.authorisedkey = authorisedkey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyiv() {
        return keyiv;
    }

    public void setKeyiv(String keyiv) {
        this.keyiv = keyiv;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
