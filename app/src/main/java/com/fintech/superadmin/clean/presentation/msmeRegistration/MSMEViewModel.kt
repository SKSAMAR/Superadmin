package com.fintech.superadmin.clean.presentation.msmeRegistration

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.clean.presentation.gstregistration.getParted
import com.fintech.superadmin.clean.presentation.gstregistration.toRequestBody
import com.fintech.superadmin.util.Accessable
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class MSMEViewModel
@Inject constructor(private val fintechAPI: FintechAPI) : BaseViewModel<Any>() {


    var c_name by mutableStateOf("")
    var c_num by mutableStateOf("")
    var cmnpy_name by mutableStateOf("")
    var cus_email by mutableStateOf("")
    var cus_address by mutableStateOf("")
    var aadhar_no by mutableStateOf("")
    var entrepreneur by mutableStateOf("")
    var category by mutableStateOf("")
    var gender by mutableStateOf("")
    var handicaped by mutableStateOf("")
    var enterpr_buss by mutableStateOf("")
    var organisation by mutableStateOf("")
    var pan_no by mutableStateOf("")
    var block by mutableStateOf("")
    var building by mutableStateOf("")
    var postoffice by mutableStateOf("")
    var x_state by mutableStateOf("")
    var city by mutableStateOf("")
    var o_block by mutableStateOf("")
    var o_building by mutableStateOf("")
    var o_postoffice by mutableStateOf("")
    var o_state by mutableStateOf("")
    var o_city by mutableStateOf("")
    var o_mobile by mutableStateOf("")
    var o_email by mutableStateOf("")
    var account_no by mutableStateOf("")
    var ifsc_code by mutableStateOf("")
    var oth_buss_type by mutableStateOf("")
    var commoditiessupplied by mutableStateOf("")
    var d_service by mutableStateOf("")
    var investment_b by mutableStateOf("")

    var proprietor by mutableStateOf<Uri?>(null)


    fun validateTheForm(context: ComponentActivity) {
        if (Accessable.isAccessable()) {
            if (c_name.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Customer Name")
            } else if (c_num.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Customer Number")
            } else if (cmnpy_name.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Company Name")
            } else if (cus_email.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Customer Email")
            } else if (cus_address.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Customer Address")
            } else if (aadhar_no.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Aadhar Number")
            } else if (entrepreneur.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Entrepreneur")
            } else if (category.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Category")
            } else if (gender.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Gender")
            } else if (handicaped.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Handicapped")
            } else if (enterpr_buss.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Entrepreneur Business")
            } else if (organisation.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Organisation")
            } else if (pan_no.trim().isEmpty()) {
                displayResponseMessage("Enter a valid PAN Number")
            } else if (block.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Block")
            } else if (building.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Building")
            } else if (postoffice.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Post Office")
            } else if (x_state.trim().isEmpty()) {
                displayResponseMessage("Enter a valid State")
            } else if (city.trim().isEmpty()) {
                displayResponseMessage("Enter a valid City")
            } else if (o_block.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Office Block")
            } else if (o_building.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Office Building")
            } else if (o_postoffice.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Office Post Office")
            } else if (o_state.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Office State")
            } else if (o_city.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Office City")
            } else if (o_mobile.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Office Mobile Number")
            } else if (o_email.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Office Email")
            } else if (account_no.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Account Number")
            } else if (ifsc_code.trim().isEmpty()) {
                displayResponseMessage("Enter a valid IFSC Code")
            } else if (oth_buss_type.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Other Business Type")
            } else if (commoditiessupplied.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Commodities Supplied")
            } else if (d_service.trim().isEmpty()) {
                displayResponseMessage("Enter a valid D Service")
            } else if (investment_b.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Investment Business")
            }else if (proprietor == null) {
                displayResponseMessage("Select a valid proprietor")
            } else {
                displayDialogLoading("Please wait..")
                fintechAPI.uploadFormMsme(
                    c_name = c_name.toRequestBody(),
                    c_num = c_num.toRequestBody(),
                    cmnpy_name = cmnpy_name.toRequestBody(),
                    cus_email = cus_email.toRequestBody(),
                    cus_address = cus_address.toRequestBody(),
                    aadhar_no = aadhar_no.toRequestBody(),
                    entrepreneur = entrepreneur.toRequestBody(),
                    category = category.toRequestBody(),
                    gender = gender.toRequestBody(),
                    handicaped = handicaped.toRequestBody(),
                    enterpr_buss = enterpr_buss.toRequestBody(),
                    organisation = organisation.toRequestBody(),
                    pan_no = pan_no.toRequestBody(),
                    block = block.toRequestBody(),
                    building = building.toRequestBody(),
                    postoffice = postoffice.toRequestBody(),
                    x_state = x_state.toRequestBody(),
                    city = city.toRequestBody(),
                    o_block = o_block.toRequestBody(),
                    o_building = o_building.toRequestBody(),
                    o_postoffice = o_postoffice.toRequestBody(),
                    o_state = o_state.toRequestBody(),
                    o_city = o_city.toRequestBody(),
                    o_mobile = o_mobile.toRequestBody(),
                    o_email = o_email.toRequestBody(),
                    account_no = account_no.toRequestBody(),
                    ifsc_code = ifsc_code.toRequestBody(),
                    oth_buss_type = oth_buss_type.toRequestBody(),
                    commoditiessupplied = commoditiessupplied.toRequestBody(),
                    d_service = d_service.toRequestBody(),
                    investment_b = investment_b.toRequestBody(),
                    proprietor = getParted(context, proprietor, "proprietor")
                ).subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({result->
                        dismissDialog()
                        displayResponseMessage("" + result.message){
                            if (result.status){
                                context.onBackPressedDispatcher.onBackPressed()
                            }
                        }
                    }, {
                        displayResponseMessage("" + it.localizedMessage)
                    })


            }
        }
    }



}