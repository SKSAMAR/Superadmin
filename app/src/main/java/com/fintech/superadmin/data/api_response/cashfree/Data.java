package com.fintech.superadmin.data.api_response.cashfree;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("nameAtBank")
	private String nameAtBank;

	@SerializedName("accountExists")
	private String accountExists;

	public String getNameAtBank(){
		return nameAtBank;
	}

	public String getAccountExists(){
		return accountExists;
	}
}