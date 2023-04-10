package com.fintech.scnpay.activities.mahagrm_bc

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fintech.scnpay.activities.HomeActivity
import com.fintech.scnpay.clean.util.common.DateDialogSys.showDateDialogFromPast
import com.fintech.scnpay.data.bc_response.BcDistrictDto
import com.fintech.scnpay.data.bc_response.BcStateDto
import com.fintech.scnpay.data.network.APIServices
import com.fintech.scnpay.util.Accessable
import com.fintech.scnpay.util.DisplayMessageUtil
import com.fintech.scnpay.util.NetworkUtil
import com.fintech.scnpay.util.ViewUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class BcViewModel
@Inject constructor(private val apiServices: APIServices) : ViewModel() {

    val bc_f_name = mutableStateOf("")
    val bc_m_name = mutableStateOf("")
    val bc_l_name = mutableStateOf("")
    val emailid = mutableStateOf("")
    val phone1 = mutableStateOf("")
    val phone2 = mutableStateOf("")
    val bc_dob = mutableStateOf("")
    val bc_state: MutableState<BcStateDto?> = mutableStateOf(null)
    val bc_district: MutableState<BcDistrictDto?> = mutableStateOf(null)
    val state = mutableStateOf(bc_state.value?.statename ?: "")
    val district = mutableStateOf(bc_district.value?.districtname ?: "")

    val bc_address = mutableStateOf("")
    val bc_block = mutableStateOf("")
    val bc_city = mutableStateOf("")
    val bc_landmark = mutableStateOf("")
    val bc_loc = mutableStateOf("")
    val bc_mohhalla = mutableStateOf("")
    val bc_pan = mutableStateOf("")
    val bc_pincode = mutableStateOf("")
    val shopname = mutableStateOf("")
    val otherShopName = mutableStateOf("")

    val kyc1Name = mutableStateOf("")
    val kyc2Name = mutableStateOf("")
    val kyc3Name = mutableStateOf("")
    val kyc4Name = mutableStateOf("")

    val kyc1 = mutableStateOf("")
    val kyc2 = mutableStateOf("")
    val kyc3 = mutableStateOf("")
    val kyc4 = mutableStateOf("")
    val shopType = mutableStateOf("")
    val qualification = mutableStateOf("")
    val population = mutableStateOf("")
    val locationType = mutableStateOf("")


    val shopList = listOf(
        "Kirana Shop ",
        "Mobile Shop",
        "Copier Shop",
        "Internet cafe",
        "other"
    )

    val qualificationList = listOf(
        "SSC",
        "HSC",
        "Graduate",
        "Post Graduate",
        "Diploma"
    )

    val populationList = listOf(
        "0 to 2000",
        "2000 to 5000",
        "5000 to 10000",
        "10000 to 50000",
        "50000 to 100000",
        "100000+"
    )

    val locationTypeList = listOf(
        "Rural",
        "Urban",
        "Metro Semi Urban"
    )

    @Composable
    fun isShopTypeIsOther(): Boolean {
        if (shopType.value.trim().contains("other", ignoreCase = true)) {
            return true
        }
        return false
    }

    @Composable
    fun isRegister_able(): Boolean {
        if (bc_f_name.value.isEmpty() || bc_l_name.value.isEmpty() || emailid.value.trim()
                .isEmpty() ||
            phone1.value.trim().isEmpty() || phone2.value.trim().isEmpty() || bc_dob.value.trim()
                .isEmpty() ||
            state.value.trim().isEmpty() || district.value.trim()
                .isEmpty() || bc_address.value.trim().isEmpty() ||
            bc_block.value.trim().isEmpty() || bc_city.value.trim()
                .isEmpty() || bc_landmark.value.trim().isEmpty() ||
            locationType.value.trim().isEmpty() || bc_mohhalla.value.trim()
                .isEmpty() || bc_pan.value.trim().isEmpty() ||
            bc_pincode.value.trim().isEmpty() || shopType.value.trim().isEmpty() ||
            shopname.value.trim().isEmpty() || qualification.value.trim().isEmpty() ||
            population.value.trim().isEmpty() || kyc1.value.isEmpty() || kyc2.value.isEmpty() ||
            kyc3.value.trim().isEmpty() || kyc4.value.trim().isEmpty()
        ) {
            return false
        }
        return true
    }

    fun canBeRegistered(): Boolean {
        if (bc_f_name.value.isEmpty() || bc_l_name.value.isEmpty() || emailid.value.trim()
                .isEmpty() ||
            phone1.value.trim().isEmpty() || phone2.value.trim().isEmpty() || bc_dob.value.trim()
                .isEmpty() ||
            state.value.trim().isEmpty() || district.value.trim()
                .isEmpty() || bc_address.value.trim().isEmpty() ||
            bc_block.value.trim().isEmpty() || bc_city.value.trim()
                .isEmpty() || bc_landmark.value.trim().isEmpty() ||
            locationType.value.trim().isEmpty() || bc_mohhalla.value.trim()
                .isEmpty() || bc_pan.value.trim().isEmpty() ||
            bc_pincode.value.trim().isEmpty() || shopType.value.trim().isEmpty() ||
            shopname.value.trim().isEmpty() || qualification.value.trim().isEmpty() ||
            population.value.trim().isEmpty() || kyc1.value.isEmpty() || kyc2.value.isEmpty() ||
            kyc3.value.trim().isEmpty() || kyc4.value.trim().isEmpty()
        ) {
            return false
        }
        return true
    }


    fun selectSate(context: Context) {
        if (Accessable.isAccessable()) {
            NetworkUtil.getNetworkResult(apiServices.getBcStates("state"), context) {

                ViewUtils.onSpinnerViewBring("Select State", context, it) {
                    bc_state.value = it
                    state.value = it.statename
                }

            }
        }
    }

    fun selectDistrict(context: Context) {
        if (bc_state.value == null) {
            selectSate(context)
            return
        }
        if (Accessable.isAccessable()) {
            NetworkUtil.getNetworkResult(
                apiServices.getBcDistrict(
                    "state",
                    bc_state.value?.stateid.toString()
                ), context
            ) {

                ViewUtils.onSpinnerViewBring("Select State", context, it) {
                    bc_district.value = it
                    district.value = it.districtname
                }

            }
        }
    }

    fun selectShopType(context: Context) {
        if (Accessable.isAccessable()) {
            ViewUtils.onSpinnerViewBring("Select Shop Type", context, shopList) {
                shopType.value = it
            }
        }
    }

    fun selectQualification(context: Context) {
        if (Accessable.isAccessable()) {
            ViewUtils.onSpinnerViewBring("Select Qualification", context, qualificationList) {
                qualification.value = it
            }
        }
    }

    fun selectPopulation(context: Context) {
        if (Accessable.isAccessable()) {
            ViewUtils.onSpinnerViewBring("Select Population", context, populationList) {
                population.value = it
            }
        }
    }

    fun selectDateOfBirth(context: Context) {
        if (Accessable.isAccessable()) {
            context.showDateDialogFromPast {
                toSpecificDates(it)
            }
        }
    }

    private fun toSpecificDates(date: Date) {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        bc_dob.value = sdf.format(date)
    }

    fun getStateName(): String = bc_state.value?.statename ?: ""

    fun getDistrictName(): String = bc_district.value?.districtname ?: ""

    fun register(context: Context) {
        if (Accessable.isAccessable()) {
            val respData = apiServices.doBcRegistration(
                bc_f_name.value,
                bc_l_name.value,
                emailid.value,
                phone1.value,
                phone2.value,
                bc_dob.value,
                bc_state.value?.stateid.toString(),
                bc_district.value?.districtid.toString(),
                bc_address.value,
                bc_block.value,
                bc_city.value,
                bc_landmark.value,
                bc_loc.value,
                bc_mohhalla.value,
                bc_pan.value,
                bc_pincode.value,
                shopname.value,
                shopType.value,
                qualification.value,
                population.value,
                locationType.value,
                kyc1.value,
                kyc2.value,
                kyc3.value,
                kyc4.value,
                "register"
            )
            NetworkUtil.getNetworkResult(respData, context) {
                if (it.first().successCode != null && (it.first().successCode == "000" || it.first().successCode == "001")){
                    val intent = Intent(context, HomeActivity::class.java)
                    intent.putExtra("status", true)
                    intent.putExtra("message", it.first().message)
                    context.startActivity(intent)
                }
                else{
                    if (it.first().statusCode == "000" || it.first().statusCode == "001") {
                        val intent = Intent(context, HomeActivity::class.java)
                        intent.putExtra("status", true)
                        intent.putExtra("message", it.first().message)
                        context.startActivity(intent)
                    } else {
                        DisplayMessageUtil.error(context, it.first().message)
                    }
                }
            }
        }
    }

    fun selectLocationType(context: Context) {
        if (Accessable.isAccessable()) {
            ViewUtils.onSpinnerViewBring("Select Locality Type", context, locationTypeList) {
                locationType.value = it
            }
        }
    }
}