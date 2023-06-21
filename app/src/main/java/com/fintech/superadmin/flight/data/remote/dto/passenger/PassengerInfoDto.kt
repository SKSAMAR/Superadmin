package com.fintech.superadmin.flight.data.remote.dto.passenger

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.IgnoredOnParcel

@Parcelize
data class PassengerInfoDto(

	@field:SerializedName("LNAME")
	val lNAME: String? = null,

	@field:SerializedName("DOB")
	val dOB: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("TYPE")
	val tYPE: String? = null,

	@field:SerializedName("USID")
	val uSID: String? = null,

	@IgnoredOnParcel
	@field:SerializedName("PASSPORT")
	val pASSPORT: Any? = null,

	@field:SerializedName("FNAME")
	val fNAME: String? = null
) : Parcelable {

	override fun toString(): String {
		return "${fNAME?:""} ${lNAME?:""}"
	}
}
