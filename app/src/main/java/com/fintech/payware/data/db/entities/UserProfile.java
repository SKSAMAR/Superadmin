package com.fintech.payware.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_profile")
public class UserProfile {

    public  int CURRENT_USER_ID = 0;

    @PrimaryKey(autoGenerate = false)
    public int uId = CURRENT_USER_ID;

    public String PROFILE_IMG;
    public String MAIN_OWNER;
    public String MAIN_OWNER_ID;
    public String OWNER_ID;
    public String USER_ID;
    public String ALTERNATE_PHONE_NO;
    public String DOB;
    public String GENDER;
    public String COUNTRY;
    public String STATE;
    public String FACEBOOK_URL;
    public String TWITTER_URL;
    public String LINKEDIN_URL;
    public String INSTAGRAM_URL;
    public String DRIBBLE_BOX_URL;
    public String DROPBOX_URL;
    public String GOOGLE_PLUS_URL;
    public String PINTEREST_URL;
    public String SKYPE_URL;
    public String VINE_URL;
    public String AADHAR_CARD_NO;
    public String PAN_CARD_NO;
    public String BANK;
    public String B_NAME;
    public String AC_HOLDER_NAME;
    public String AC_NUM;
    public String IFSC_CODE;
    public String PASSBOOK;
    public String AADHAAR;
    public String PAN;
    public String DATE;


    public UserProfile(){

    }

    public UserProfile(String PROFILE_IMG, String MAIN_OWNER, String MAIN_OWNER_ID, String OWNER_ID, String USER_ID, String ALTERNATE_PHONE_NO, String DOB, String GENDER, String COUNTRY, String STATE, String FACEBOOK_URL, String TWITTER_URL, String LINKEDIN_URL, String INSTAGRAM_URL, String DRIBBLE_BOX_URL, String DROPBOX_URL, String GOOGLE_PLUS_URL, String PINTEREST_URL, String SKYPE_URL, String VINE_URL, String AADHAR_CARD_NO, String PAN_CARD_NO, String BANK, String b_NAME, String AC_HOLDER_NAME, String AC_NUM, String IFSC_CODE, String PASSBOOK, String AADHAAR, String PAN, String DATE) {
        this.PROFILE_IMG = PROFILE_IMG;
        this.MAIN_OWNER = MAIN_OWNER;
        this.MAIN_OWNER_ID = MAIN_OWNER_ID;
        this.OWNER_ID = OWNER_ID;
        this.USER_ID = USER_ID;
        this.ALTERNATE_PHONE_NO = ALTERNATE_PHONE_NO;
        this.DOB = DOB;
        this.GENDER = GENDER;
        this.COUNTRY = COUNTRY;
        this.STATE = STATE;
        this.FACEBOOK_URL = FACEBOOK_URL;
        this.TWITTER_URL = TWITTER_URL;
        this.LINKEDIN_URL = LINKEDIN_URL;
        this.INSTAGRAM_URL = INSTAGRAM_URL;
        this.DRIBBLE_BOX_URL = DRIBBLE_BOX_URL;
        this.DROPBOX_URL = DROPBOX_URL;
        this.GOOGLE_PLUS_URL = GOOGLE_PLUS_URL;
        this.PINTEREST_URL = PINTEREST_URL;
        this.SKYPE_URL = SKYPE_URL;
        this.VINE_URL = VINE_URL;
        this.AADHAR_CARD_NO = AADHAR_CARD_NO;
        this.PAN_CARD_NO = PAN_CARD_NO;
        this.BANK = BANK;
        this.B_NAME = b_NAME;
        this.AC_HOLDER_NAME = AC_HOLDER_NAME;
        this.AC_NUM = AC_NUM;
        this.IFSC_CODE = IFSC_CODE;
        this.PASSBOOK = PASSBOOK;
        this.AADHAAR = AADHAAR;
        this.PAN = PAN;
        this.DATE = DATE;
    }

    public String getAADHAAR() {
        return AADHAAR;
    }

    public void setAADHAAR(String AADHAAR) {
        this.AADHAAR = AADHAAR;
    }

    public String getPAN() {
        return PAN;
    }

    public void setPAN(String PAN) {
        this.PAN = PAN;
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

    public String getPROFILE_IMG() {
        return PROFILE_IMG;
    }

    public void setPROFILE_IMG(String PROFILE_IMG) {
        this.PROFILE_IMG = PROFILE_IMG;
    }

    public String getMAIN_OWNER() {
        return MAIN_OWNER;
    }

    public void setMAIN_OWNER(String MAIN_OWNER) {
        this.MAIN_OWNER = MAIN_OWNER;
    }

    public String getMAIN_OWNER_ID() {
        return MAIN_OWNER_ID;
    }

    public void setMAIN_OWNER_ID(String MAIN_OWNER_ID) {
        this.MAIN_OWNER_ID = MAIN_OWNER_ID;
    }

    public String getOWNER_ID() {
        return OWNER_ID;
    }

    public void setOWNER_ID(String OWNER_ID) {
        this.OWNER_ID = OWNER_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getALTERNATE_PHONE_NO() {
        return ALTERNATE_PHONE_NO;
    }

    public void setALTERNATE_PHONE_NO(String ALTERNATE_PHONE_NO) {
        this.ALTERNATE_PHONE_NO = ALTERNATE_PHONE_NO;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGENDER() {
        return GENDER;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }

    public String getCOUNTRY() {
        return COUNTRY;
    }

    public void setCOUNTRY(String COUNTRY) {
        this.COUNTRY = COUNTRY;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getFACEBOOK_URL() {
        return FACEBOOK_URL;
    }

    public void setFACEBOOK_URL(String FACEBOOK_URL) {
        this.FACEBOOK_URL = FACEBOOK_URL;
    }

    public String getTWITTER_URL() {
        return TWITTER_URL;
    }

    public void setTWITTER_URL(String TWITTER_URL) {
        this.TWITTER_URL = TWITTER_URL;
    }

    public String getLINKEDIN_URL() {
        return LINKEDIN_URL;
    }

    public void setLINKEDIN_URL(String LINKEDIN_URL) {
        this.LINKEDIN_URL = LINKEDIN_URL;
    }

    public String getINSTAGRAM_URL() {
        return INSTAGRAM_URL;
    }

    public void setINSTAGRAM_URL(String INSTAGRAM_URL) {
        this.INSTAGRAM_URL = INSTAGRAM_URL;
    }

    public String getDRIBBLE_BOX_URL() {
        return DRIBBLE_BOX_URL;
    }

    public void setDRIBBLE_BOX_URL(String DRIBBLE_BOX_URL) {
        this.DRIBBLE_BOX_URL = DRIBBLE_BOX_URL;
    }

    public String getDROPBOX_URL() {
        return DROPBOX_URL;
    }

    public void setDROPBOX_URL(String DROPBOX_URL) {
        this.DROPBOX_URL = DROPBOX_URL;
    }

    public String getGOOGLE_PLUS_URL() {
        return GOOGLE_PLUS_URL;
    }

    public void setGOOGLE_PLUS_URL(String GOOGLE_PLUS_URL) {
        this.GOOGLE_PLUS_URL = GOOGLE_PLUS_URL;
    }

    public String getPINTEREST_URL() {
        return PINTEREST_URL;
    }

    public void setPINTEREST_URL(String PINTEREST_URL) {
        this.PINTEREST_URL = PINTEREST_URL;
    }

    public String getSKYPE_URL() {
        return SKYPE_URL;
    }

    public void setSKYPE_URL(String SKYPE_URL) {
        this.SKYPE_URL = SKYPE_URL;
    }

    public String getVINE_URL() {
        return VINE_URL;
    }

    public void setVINE_URL(String VINE_URL) {
        this.VINE_URL = VINE_URL;
    }

    public String getAADHAR_CARD_NO() {
        return AADHAR_CARD_NO;
    }

    public void setAADHAR_CARD_NO(String AADHAR_CARD_NO) {
        this.AADHAR_CARD_NO = AADHAR_CARD_NO;
    }

    public String getPAN_CARD_NO() {
        return PAN_CARD_NO;
    }

    public void setPAN_CARD_NO(String PAN_CARD_NO) {
        this.PAN_CARD_NO = PAN_CARD_NO;
    }

    public String getBANK() {
        return BANK;
    }

    public void setBANK(String BANK) {
        this.BANK = BANK;
    }

    public String getB_NAME() {
        return B_NAME;
    }

    public void setB_NAME(String b_NAME) {
        B_NAME = b_NAME;
    }

    public String getAC_HOLDER_NAME() {
        return AC_HOLDER_NAME;
    }

    public void setAC_HOLDER_NAME(String AC_HOLDER_NAME) {
        this.AC_HOLDER_NAME = AC_HOLDER_NAME;
    }

    public String getAC_NUM() {
        return AC_NUM;
    }

    public void setAC_NUM(String AC_NUM) {
        this.AC_NUM = AC_NUM;
    }

    public String getIFSC_CODE() {
        return IFSC_CODE;
    }

    public void setIFSC_CODE(String IFSC_CODE) {
        this.IFSC_CODE = IFSC_CODE;
    }

    public String getPASSBOOK() {
        return PASSBOOK;
    }

    public void setPASSBOOK(String PASSBOOK) {
        this.PASSBOOK = PASSBOOK;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }
}
