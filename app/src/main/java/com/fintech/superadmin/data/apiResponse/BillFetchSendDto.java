package com.fintech.superadmin.data.apiResponse;


import java.util.ArrayList;

public class BillFetchSendDto{

    public String fetchbillAPP = "fetchbillAPP";
    public String billerid;
    public ArrayList<ParamData> inputdata;

    public BillFetchSendDto(String billerid, ArrayList<ParamData> inputdata) {
        this.billerid = billerid;
        this.inputdata = inputdata;
    }
}