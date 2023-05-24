package com.fintech.superadmin.data.cashfree;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CashFreeLatestGateway{

	@SerializedName("settlements")
	private Settlements settlements;

	@SerializedName("cf_order_id")
	private int cfOrderId;

	@SerializedName("order_meta")
	private OrderMeta orderMeta;

	@SerializedName("order_currency")
	private String orderCurrency;

	@SerializedName("payments")
	private Payments payments;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("refunds")
	private Refunds refunds;

	@SerializedName("order_note")
	private Object orderNote;

	@SerializedName("order_status")
	private String orderStatus;

	@SerializedName("customer_details")
	private CustomerDetails customerDetails;

	@SerializedName("order_expiry_time")
	private String orderExpiryTime;

	@SerializedName("order_splits")
	private List<Object> orderSplits;

	@SerializedName("order_amount")
	private Object orderAmount;

	@SerializedName("payment_session_id")
	private String paymentSessionId;

	@SerializedName("terminal_data")
	private Object terminalData;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("entity")
	private String entity;

	@SerializedName("order_tags")
	private Object orderTags;

	public Settlements getSettlements(){
		return settlements;
	}

	public int getCfOrderId(){
		return cfOrderId;
	}

	public OrderMeta getOrderMeta(){
		return orderMeta;
	}

	public String getOrderCurrency(){
		return orderCurrency;
	}

	public Payments getPayments(){
		return payments;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public Refunds getRefunds(){
		return refunds;
	}

	public Object getOrderNote(){
		return orderNote;
	}

	public String getOrderStatus(){
		return orderStatus;
	}

	public CustomerDetails getCustomerDetails(){
		return customerDetails;
	}

	public String getOrderExpiryTime(){
		return orderExpiryTime;
	}

	public List<Object> getOrderSplits(){
		return orderSplits;
	}

	public Object getOrderAmount(){
		return orderAmount;
	}

	public String getPaymentSessionId(){
		return paymentSessionId;
	}

	public Object getTerminalData(){
		return terminalData;
	}

	public String getOrderId(){
		return orderId;
	}

	public String getEntity(){
		return entity;
	}

	public Object getOrderTags(){
		return orderTags;
	}
}