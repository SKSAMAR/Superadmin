package com.fintech.superadmin.clean.presentation.rakeshpan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.util.Accessable
import com.fintech.superadmin.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UTIApplyViewModel
@Inject constructor(private val fintechAPI: FintechAPI) : BaseViewModel<Any>() {

    var name by mutableStateOf("")
    var address by mutableStateOf("")
    var pincode by mutableStateOf("")
    var homeState by mutableStateOf("")
    var phone by mutableStateOf("")
    var email by mutableStateOf("")
    var pan by mutableStateOf("")
    var shop by mutableStateOf("")
    var adhaar by mutableStateOf("")


    fun register() {
        if (name.trim().isEmpty()) {
            displayResponseMessage("Enter a valid name")
        } else if (address.trim().isEmpty()) {
            displayResponseMessage("Enter a valid address")
        } else if (pincode.trim().isEmpty()) {
            displayResponseMessage("Enter a valid Pin Code")
        }  else if (homeState.trim().isEmpty()) {
            displayResponseMessage("Enter a valid State")
        } else if (phone.trim().isEmpty()) {
            displayResponseMessage("Enter a valid phone")
        } else if (email.trim().isEmpty()) {
            displayResponseMessage("Enter a valid email")
        } else if (pan.trim().isEmpty()) {
            displayResponseMessage("Enter a valid pan")
        } else if (shop.trim().isEmpty()) {
            displayResponseMessage("Enter a valid shop")
        } else if (adhaar.trim().isEmpty()) {
            displayResponseMessage("Enter a valid Aadhaar")
        } else {
            if (Accessable.isAccessable()) {
                displayDialogLoading("Please wait")
                NetworkUtil.getNetworkResult(fintechAPI.rakeshUTIRegister(name = name.trim(), address = address, pincode = pincode, state = homeState, phone = phone, email = email, pan = pan, shop = shop, adhaar = adhaar), null){
                    displayResponseMessage(it.toString())
                }
            }
        }
    }

}