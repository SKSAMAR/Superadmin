package com.fintech.superadmin.data.api_response.billavenue.billbillers

//import kotlinx.parcelize.Parcelize
//import android.os.Parcelable
//
//@Parcelize
//data class BillBillersList(
//	val billBillersList: List<BillBillersListItem?>? = null
//) : Parcelable
//
//@Parcelize
//data class Group(
//	val param: String? = null,
//	val attributes: Attributes? = null,
//	val group: List<GroupItem?>? = null
//) : Parcelable
//
//@Parcelize
//data class Biller(
//	val billerId: String? = null,
//	val billerSupportBillValidation: String? = null,
//	val billerPaymentExactness: String? = null,
//	val billerTimeout: String? = null,
//	val billerFetchRequiremet: String? = null,
//	val billerAdditionalInfo: BillerAdditionalInfo? = null,
//	val billerPaymentChannels: BillerPaymentChannels? = null,
//	val billerName: String? = null,
//	val billerAdhoc: String? = null,
//	val billerCategory: String? = null,
//	val billerInputParams: BillerInputParams? = null,
//	val supportPendingStatus: String? = null,
//	val billerAmountOptions: String? = null,
//	val billerPaymentModes: String? = null,
//	val billerCoverage: String? = null,
//	val rechargeAmountInValidationRequest: String? = null,
//	val supportDeemed: String? = null,
//	val customerParamGroups: CustomerParamGroups? = null
//) : Parcelable
//
//@Parcelize
//data class ParamInfoItem(
//	val paramName: String? = null,
//	val regEx: String? = null,
//	val dataType: String? = null,
//	val minLength: String? = null,
//	val isOptional: String? = null,
//	val maxLength: String? = null
//) : Parcelable
//
//@Parcelize
//data class Attributes(
//	val input: String? = null,
//	val name: String? = null
//) : Parcelable
//
//@Parcelize
//data class GroupItem(
//	val param: List<String?>? = null,
//	val attributes: Attributes? = null
//) : Parcelable
//
//@Parcelize
//data class BillBillersListItem(
//	val bILLERID: String? = null,
//	val iD: String? = null,
//	val rESPONSE: RESPONSE? = null
//) : Parcelable
//
//@Parcelize
//data class BillerInputParams(
//	val paramInfo: List<ParamInfoItem?>? = null
//) : Parcelable
//
//@Parcelize
//data class BillerPaymentChannels(
//	val paymentChannelInfo: List<PaymentChannelInfoItem?>? = null
//) : Parcelable
//
//@Parcelize
//data class PaymentChannelInfoItem(
//	val paymentChannelName: String? = null,
//	val minAmount: String? = null,
//	val maxAmount: String? = null
//) : Parcelable
//
//@Parcelize
//data class CustomerParamGroups(
//	val group: Group? = null
//) : Parcelable
//
//@Parcelize
//data class BillerAdditionalInfo(
//	val paramInfo: List<ParamInfoItem?>? = null
//) : Parcelable
//
//@Parcelize
//data class RESPONSE(
//	val biller: Biller? = null,
//	val responseCode: String? = null
//) : Parcelable
