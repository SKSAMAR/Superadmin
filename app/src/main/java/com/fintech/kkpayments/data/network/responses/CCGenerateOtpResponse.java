package com.fintech.kkpayments.data.network.responses;

public class CCGenerateOtpResponse {
    public boolean status;
    public Integer response_code;
    public Integer responsecode;
    public String mobile;
    public String stateresp;
    public String message;
    public String ref_id;


    public int getResponse_code() {
        if (response_code == null){
            return  responsecode;
        }
        return response_code;
    }
}
