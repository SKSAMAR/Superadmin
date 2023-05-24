package com.fintech.superadmin.data.cashfree;

import com.google.gson.annotations.SerializedName;

public class OrderMeta{

	@SerializedName("payment_methods")
	private Object paymentMethods;

	@SerializedName("return_url")
	private Object returnUrl;

	@SerializedName("notify_url")
	private Object notifyUrl;

	public Object getPaymentMethods(){
		return paymentMethods;
	}

	public Object getReturnUrl(){
		return returnUrl;
	}

	public Object getNotifyUrl(){
		return notifyUrl;
	}
}