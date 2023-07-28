package com.fintech.superadmin.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class EkoBankModel(

	@field:SerializedName("ShortCode")
	val shortCode: String? = null,

	@field:SerializedName("STATUS")
	val sTATUS: String? = null,

	@field:SerializedName("STATIC_IFSC")
	var sTATICIFSC: String? = null,

	@field:SerializedName("IMPS_STATUS")
	val iMPSSTATUS: String? = null,

	@field:SerializedName("IsVerficationAvailable")
	val isVerficationAvailable: String? = null,

	@field:SerializedName("NEFT_Status")
	val nEFTStatus: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("Ifsccode_Status")
	val ifsccodeStatus: String? = null,

	@field:SerializedName("BANK_NAME")
	val bANKNAME: String? = null
) : Parcelable
