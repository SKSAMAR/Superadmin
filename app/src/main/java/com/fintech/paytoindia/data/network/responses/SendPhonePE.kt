package com.fintech.paytoindia.data.network.responses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class SendPhonePE(
	val amount: Int? = null,
	val merchantId: String? = null,
	val deviceContext: DeviceContext? = null,
	val mobileNumber: String? = null,
	val merchantTransactionId: String? = null,
	val callbackUrl: String? = null,
	val merchantUserId: String? = null,
	val paymentInstrument: PaymentInstrument? = null
) : Parcelable

@Parcelize
data class DeviceContext(
	val deviceOS: String? = null
) : Parcelable

@Parcelize
data class PaymentInstrument(
	val targetApp: String? = null,
	val type: String? = null
) : Parcelable
