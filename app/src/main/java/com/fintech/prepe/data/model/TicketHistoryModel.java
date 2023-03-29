package com.fintech.prepe.data.model;

import com.google.gson.annotations.SerializedName;

public class TicketHistoryModel {

	@SerializedName("STATUS")
	private String sTATUS;

	@SerializedName("ACTION_DATE")
	private String aCTIONDATE;

	@SerializedName("EMPLOYEE_ID")
	private String eMPLOYEEID;

	@SerializedName("DESCRIPTION")
	private String dESCRIPTION;

	@SerializedName("USER_ID")
	private String uSERID;

	@SerializedName("TRANSACTION_ID")
	private String tRANSACTIONID;

	@SerializedName("PROOF")
	private String pROOF;

	@SerializedName("ISSUE_DATE")
	private String iSSUEDATE;

	@SerializedName("DEPARTMENT")
	private String dEPARTMENT;

	@SerializedName("ID")
	private String iD;

	@SerializedName("REMARK")
	private String rEMARK;

	public String getSTATUS(){
		return sTATUS;
	}

	public String getACTIONDATE(){
		if(aCTIONDATE == null || aCTIONDATE.trim().isEmpty()){
			return "Action is yet to be taken";
		}
		return aCTIONDATE;
	}

	public String getEMPLOYEEID(){
		return eMPLOYEEID;
	}

	public String getDESCRIPTION(){
		return dESCRIPTION;
	}

	public String getUSERID(){
		return uSERID;
	}


	public String getPROOF(){
		return pROOF;
	}

	public String getISSUEDATE(){
		return iSSUEDATE;
	}

	public String getDEPARTMENT(){
		return dEPARTMENT;
	}

	public String getTRANSACTIONID() {
		return tRANSACTIONID;
	}

	public void setTRANSACTIONID(String tRANSACTIONID) {
		this.tRANSACTIONID = tRANSACTIONID;
	}

	public String getID(){
		return iD;
	}

	public String getREMARK(){
		if(rEMARK == null || rEMARK.trim().isEmpty()){
			return "Yet to be made";
		}
		return rEMARK;
	}
}