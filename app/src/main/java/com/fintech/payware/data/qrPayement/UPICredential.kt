package com.fintech.payware.data.qrPayement

import java.io.Serializable

data class UPICredential(
    var upi: String?,
    var name: String?,
    val mc: String? = null,
    val mode: String? = null,
    val purpose: String? = null,
    val fullUrl: String? = null,
    var amount: String? = null
): Serializable{
    override fun toString(): String {
        return "UPICredential(upi='$upi', name='$name', mc=$mc, mode=$mode, purpose=$purpose)"
    }
}
