package com.fintech.superadmin.activities.aeps.brandedComp;

import java.util.ArrayList;


public class CommonBankList {
    ArrayList<GetCommonBankListModel> bankListData;
    ArrayList<GetCommonBankListModel> tempList;
    /* 12 */   public static CommonBankList ourInstance = new CommonBankList();

    public static CommonBankList getInstance() {
        /* 15 */
        if (ourInstance == null)
            /* 16 */ ourInstance = new CommonBankList();
        /* 17 */
        return ourInstance;
    }


    public void setBankListData(ArrayList<GetCommonBankListModel> bankListData) {
        /* 25 */
        this.bankListData = bankListData;
    }

    public void setTempList(ArrayList<GetCommonBankListModel> bankListData) {
        /* 29 */
        this.tempList = bankListData;
    }


    public ArrayList<GetCommonBankListModel> getBankListData() {
        /* 34 */
        return this.bankListData;
    }

    public ArrayList<GetCommonBankListModel> getTempList() {
        /* 38 */
        return this.tempList;
    }
}
