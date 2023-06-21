package com.fintech.superadmin.flight.data.remote.dto

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.fintech.superadmin.flight.data.remote.sendDto.FilteredAirlineItem

@Parcelize
data class AirlineDto(

    @field:SerializedName("AirlineCode")
    val airlineCode: String? = null,

    @field:SerializedName("AirlinesName")
    val airlinesName: String? = null,

    @field:SerializedName("ID")
    val iD: String? = null
) : Parcelable

fun AirlineDto.toFilteredAirlineItem(): FilteredAirlineItem{
    return FilteredAirlineItem(airlineCode = airlineCode?.trim())
}