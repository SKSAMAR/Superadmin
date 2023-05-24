package com.fintech.superadmin.data.cashfree;

import com.google.gson.annotations.SerializedName;

public class Settlements{

	@SerializedName("url")
	private String url;

	public String getUrl(){
		return url;
	}
}