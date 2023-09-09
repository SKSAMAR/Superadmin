package com.fintech.superadmin.clean.data.remote.dto.addMember

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PanVerifyDto(

	@field:SerializedName("valid")
	val valid: Boolean? = null,

	@field:SerializedName("registered_name")
	val registeredName: String? = null,

	@field:SerializedName("reference_id")
	val referenceId: Int? = null,

	@field:SerializedName("father_name")
	val fatherName: String? = null,

	@field:SerializedName("name_provided")
	val nameProvided: String? = null,

	@field:SerializedName("pan")
	val pan: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable
