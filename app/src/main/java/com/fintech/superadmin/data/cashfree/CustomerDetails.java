package com.fintech.superadmin.data.cashfree;

import com.google.gson.annotations.SerializedName;

public class CustomerDetails{

	@SerializedName("customer_email")
	private String customerEmail;

	@SerializedName("customer_phone")
	private String customerPhone;

	@SerializedName("customer_name")
	private Object customerName;

	@SerializedName("customer_id")
	private String customerId;

	public String getCustomerEmail(){
		return customerEmail;
	}

	public String getCustomerPhone(){
		return customerPhone;
	}

	public Object getCustomerName(){
		return customerName;
	}

	public String getCustomerId(){
		return customerId;
	}
}