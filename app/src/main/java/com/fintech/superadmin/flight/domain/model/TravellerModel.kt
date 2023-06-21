package com.fintech.superadmin.flight.domain.model

import android.os.Build
import android.os.Parcelable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import com.fintech.superadmin.flight.data.remote.dto.seatMap.SeatDetailsItem
import com.fintech.superadmin.flight.data.remote.dto.ssr.SSRDetailsItem
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.Period


data class TravellerModel(
    val id: Int = 1,
    val internationalBased: Boolean = false,
    val typeFor: String,
    val fname: MutableState<String> = mutableStateOf(""),
    val lname: MutableState<String> = mutableStateOf(""),
    val dob: MutableState<String> = mutableStateOf(""),
    val title: MutableState<NameTitle?> = mutableStateOf(null),
    val passportInfo: TravellerPassportModel? = null,
    val sSRDetails: MutableMap<String, SSRDetailsItem> = mutableStateMapOf(),
    val seatDetailsItem: MutableState<SeatDetailsItem?> = mutableStateOf(null),

) {
    fun paxType(): Int {
        if (typeFor.trim().contains("adult", true)) {
            return 0 // ADULT
        }
        if (typeFor.trim().contains("child", true)) {
            return 1 //CHILD
        }
        return 2 //INFANT
    }

    fun gender(): Int {
        if (title.value == NameTitle.Mstr || title.value == NameTitle.Mister) {
            return 0 // MALE
        }
        if (typeFor.trim().contains("child", true)) {
            return 1 //FEMALE
        }
        return 0 //INFANT
    }

    fun genderName(): String {
        if (title.value == NameTitle.Mstr || title.value == NameTitle.Mister) {
            return "Male" // MALE
        }
        if (title.value == NameTitle.Mrs || title.value == NameTitle.Miss || title.value == NameTitle.Ms) {
            return "Female" //FEMALE
        }
        return "Others" //INFANT
    }


    fun calculateAge(): String? {
        try {
            val birthDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDate.parse(dob.value)
            } else {
                return null
            }
            val currentDate = LocalDate.now()
            return Period.between(birthDate, currentDate).years.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun getFrequentFlyers(): SSRDetailsItem?{
        try {
            return sSRDetails["FREQUENTFLYER"]
        }catch (e: Exception){
            e.printStackTrace()
            return null
        }
    }
}


data class TravellerPassportModel(
    val passportNumber: MutableState<String> = mutableStateOf(""),
    val issuingCountry: MutableState<String> = mutableStateOf(""),
    val dateOfExpiry: MutableState<String> = mutableStateOf(""),
    val nationality: MutableState<String> = mutableStateOf(""),
)


sealed class NameTitle(var title: String) {
    object Mister : NameTitle(title = "Mr")
    object Ms : NameTitle(title = "Ms")
    object Miss : NameTitle(title = "Miss")
    object Mrs : NameTitle(title = "Mrs")
    object Mstr : NameTitle(title = "Mstr")
}

fun String?.titleToNameTitle(): NameTitle? {
    try {
        nameListTitle.forEach {
            if (this?.trim()?.lowercase()?.contains(it.title.trim().lowercase(), true) == true) {
                return it
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


data class TravellerSendData(
    val Pax_Id: Int,
    val Pax_type: Int,
    val Title: String,
    val First_Name: String,
    val Last_Name: String,
    val Gender: Int,
    val Age: String? = null,
    val DOB: String? = null,
    val Passport_Number: String? = null,
    val Passport_Issuing_Country: String? = null,
    val Passport_Expiry: String? = null,
    val Nationality: String? = null,
    val FrequentFlyerDetails: SSRDetailsItem? = null,
)

fun TravellerModel.toTravellerSendData(): TravellerSendData {
    return TravellerSendData(
        Pax_Id = id,
        Pax_type = paxType(),
        Title = title.value?.title ?: "",
        First_Name = fname.value,
        Last_Name = lname.value,
        Gender = gender(),
        Age = calculateAge(),
        DOB = dob.value,
        Passport_Number = passportInfo?.passportNumber?.value,
        Passport_Issuing_Country = passportInfo?.issuingCountry?.value,
        Passport_Expiry = passportInfo?.dateOfExpiry?.value,
        Nationality = passportInfo?.nationality?.value,
        FrequentFlyerDetails = getFrequentFlyers()
    )
}


val nameListTitle = listOf(
    NameTitle.Mister,
    NameTitle.Ms,
    NameTitle.Miss,
    NameTitle.Mrs,
    NameTitle.Mstr
)
