package com.fintech.paytcash.data.api_response.cashfree;

import com.google.gson.annotations.SerializedName;

public class UPIVerifyResponse{

	@SerializedName("data")
	private Data data;

	@SerializedName("subCode")
	private String subCode;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public Data getData(){
		return data;
	}

	public String getSubCode(){
		return subCode;
	}

	public String getMessage(){
		return message;
	}

	public String getStatus(){
		return status;
	}
}