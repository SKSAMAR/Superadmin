package com.fintech.superadmin.clean.data.remote.dto.cashfree

data class PayoutBeneficiary(
    val ACCOUNT: String?,
    val ADDRESS: String?,
    val BANK: String?,
    val IFSC: String?,
    val MOBILE: String?,
    val NAME: String?,
    val BENEID: String?,
    val AMOUNT: String?
) : java.io.Serializable
