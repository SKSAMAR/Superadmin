package com.fintech.superadmin.activities.pan

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.helper.SimpleCustomChromeTabsHelper
import com.fintech.superadmin.util.Accessable
import com.fintech.superadmin.util.Constant
import com.fintech.superadmin.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NSDLViewModel
@Inject constructor(private val fintechAPI: FintechAPI) : BaseViewModel<Any>() {


    var genderTypeList = listOf("Male", "Female")
    var genderType by mutableStateOf<String?>(null)
    var genderDialog by mutableStateOf(false)

    var transactionTypeList = listOf("Physical", "Electronic")
    var transactionType by mutableStateOf<String?>(null)
    var transactionDialog by mutableStateOf(false)


    var fname by mutableStateOf("")
    var mobile by mutableStateOf("")
    var email by mutableStateOf("")

    fun createNsdl(context: ComponentActivity) {
        if (Accessable.isAccessable()) {
            if (fname.isEmpty()) {
                displayResponseMessage("Enter a valid First name")
            } else if (mobile.isEmpty() || mobile.trim().length < 10) {
                displayResponseMessage("Enter a valid Mobile")
            } else if (email.isEmpty()) {
                displayResponseMessage("Enter a valid Email")
            } else if (genderType.isNullOrEmpty()) {
                displayResponseMessage("Select a valid Gender")
            } else if (transactionType.isNullOrEmpty()) {
                displayResponseMessage("Select a valid PAN Type")
            } else {
                NetworkUtil.getNetworkResult(
                    fintechAPI.nsdlChangePanCardSubmit(
                        name = fname.trim(),
                        em = email.trim(),
                        mob = mobile.trim(),
                        gen = genderType.toString().trim(),
                        appmode = if (transactionType == transactionTypeList.firstOrNull()) "P" else "E",
                    ),
                    context
                ) { res ->
                    dismissDialog()
                    if (res.status) {

                        displayResponseMessage("Success")
                        val simple = SimpleCustomChromeTabsHelper(context)
                        simple.openUrlForResult(res.getMessage(), Constant.CHROME_CUSTOM_TAB_REQUEST_CODE)
                        genderType = null
                        transactionType = null
                        fname = ""
                        email = ""
                        mobile = ""
                    } else {
                        displayDialogFailure(""+res.getMessage())
                    }
                }
            }
        }
    }


}