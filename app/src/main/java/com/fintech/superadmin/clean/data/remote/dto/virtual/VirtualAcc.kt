package com.fintech.superadmin.clean.data.remote.dto.virtual


import com.google.gson.annotations.SerializedName

data class VirtualAcc(
    @SerializedName("toBoard")
    var toBoard: Boolean?,
    @SerializedName("virtual_account_bank")
    var virtualAccountBank: VirtualAccountBank?
): java.io.Serializable