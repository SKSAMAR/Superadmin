package com.fintech.petoindia.data.network.responses;

public class My_Token_Res {
    public Token_Res token_res;
    public String merchant_id;

    public My_Token_Res(Token_Res token_res, String merchant_id) {
        this.token_res = token_res;
        this.merchant_id = merchant_id;
    }

    public Token_Res getToken_res() {
        return token_res;
    }

    public void setToken_res(Token_Res token_res) {
        this.token_res = token_res;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }
}
