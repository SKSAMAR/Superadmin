package com.fintech.scnpay.data_model.request;

import com.google.gson.annotations.SerializedName;

public class RequestedHistoryModel{

	@SerializedName("DATE")
	private String dATE;

	@SerializedName("STATUS")
	private String sTATUS;

	@SerializedName("PAYMENT_MODE")
	private String pAYMENTMODE;

	@SerializedName("AMOUNT")
	private String aMOUNT;

	@SerializedName("FUND_TYPE")
	private String fUNDTYPE;

	@SerializedName("USER_ID")
	private String uSERID;

	@SerializedName("ID")
	private String iD;

	@SerializedName("TRANSACTION_ID")
	private String tRANSACTIONID;

	@SerializedName("REMARK")
	private String rEMARK;

	public String getDATE(){
		return dATE;
	}

	public String getSTATUS(){
		return sTATUS;
	}

	public String getPAYMENTMODE(){
		return pAYMENTMODE;
	}

	public String getAMOUNT(){
		return aMOUNT;
	}

	public String getFUNDTYPE(){
		return fUNDTYPE;
	}

	public String getUSERID(){
		return uSERID;
	}

	public String getID(){
		return iD;
	}

	public String getTRANSACTIONID(){
		return tRANSACTIONID;
	}

	public String getREMARK(){
		return rEMARK;
	}
}