package com.fintech.scnpay.data.deer_response;

public class CFPOTPResponse {

    public String rs_code;
    public String smsotpst;
    public String OTPHASH;


    public CFPOTPResponse(String rs_code, String smsotpst, String OTPHASH) {
        this.rs_code = rs_code;
        this.smsotpst = smsotpst;
        this.OTPHASH = OTPHASH;
    }

    public String getRs_code() {
        return rs_code;
    }

    public void setRs_code(String rs_code) {
        this.rs_code = rs_code;
    }

    public String getSmsotpst() {
        return smsotpst;
    }

    public void setSmsotpst(String smsotpst) {
        this.smsotpst = smsotpst;
    }

    public String getOTPHASH() {
        return OTPHASH;
    }

    public void setOTPHASH(String OTPHASH) {
        this.OTPHASH = OTPHASH;
    }

    @Override
    public String toString() {
        return "CFPOTPResponse{" +
                "rs_code='" + rs_code + '\'' +
                ", smsotpst='" + smsotpst + '\'' +
                ", OTPHASH='" + OTPHASH + '\'' +
                '}';
    }
}
