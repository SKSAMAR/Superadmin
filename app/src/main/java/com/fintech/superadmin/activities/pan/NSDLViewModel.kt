package com.fintech.superadmin.activities.pan

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.clean.util.ViewUtils.openInCustomBrowser
import com.fintech.superadmin.util.Accessable
import com.fintech.superadmin.util.DisplayMessageUtil
import com.fintech.superadmin.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NSDLViewModel
@Inject constructor(private val fintechAPI: FintechAPI) : BaseViewModel<Any>() {

    var titleTypeList = listOf("Mr.", "Mrs.")
    var titleType by mutableStateOf<String?>(null)
    var titleDialog by mutableStateOf(false)

    var genderTypeList = listOf("Male", "Female")
    var genderType by mutableStateOf<String?>(null)
    var genderDialog by mutableStateOf(false)

    var transactionTypeList = listOf("Physical", "Electronic")
    var transactionType by mutableStateOf<String?>(null)
    var transactionDialog by mutableStateOf(false)

    var serviceTypeList = listOf("CREATION", "CORRECTION")
    var serviceType by mutableStateOf<String?>(null)
    var serviceDialog by mutableStateOf(false)

    var fname by mutableStateOf("")
    var lname by mutableStateOf("")
    var email by mutableStateOf("")

    fun createNsdl(context: Context) {
        if (Accessable.isAccessable()) {
            if (serviceType.isNullOrEmpty()) {
                displayResponseMessage("Select a valid Service Type")
            } else if (titleType.isNullOrEmpty()) {
                displayResponseMessage("Select a valid Title")
            } else if (fname.isEmpty()) {
                displayResponseMessage("Enter a valid First name")
            } else if (lname.isEmpty()) {
                displayResponseMessage("Enter a valid Last name")
            } else if (genderType.isNullOrEmpty()) {
                displayResponseMessage("Select a valid Gender")
            } else if (transactionType.isNullOrEmpty()) {
                displayResponseMessage("Select a valid PAN Type")
            } else {

                if (serviceType == serviceTypeList.firstOrNull()) {

                    NetworkUtil.getNetworkResult(
                        fintechAPI.naslCreationPanCardRedirect(
                            first_name = fname.trim(),
                            last_name = lname.trim(),
                            mode = if (transactionType == transactionTypeList.firstOrNull()) "P" else "E",
                            title = if (genderType == genderTypeList.firstOrNull()) "1" else "2",
                            gender = genderType.toString()
                        ),
                        context
                    ) { res ->
                        if (res.status != null && res.status) {
                            openInCustomBrowser(context, res.data?.url, res.data?.encdata)
                            titleType = null
                            genderType = null
                            transactionType = null
                            serviceType = null
                            fname = ""
                            lname = ""
                        } else {
                            displayDialogFailure(res.message)
                        }
                    }
                }
                else {
                    if (email.isEmpty()) {
                        displayResponseMessage("Enter valid Email")
                        return
                    }
                    NetworkUtil.getNetworkResult(
                        fintechAPI.naslChangePanCardRedirect(
                            first_name = fname.trim(),
                            last_name = lname.trim(),
                            email = email,
                            mode = if (transactionType == transactionTypeList.firstOrNull()) "P" else "E",
                            title = if (genderType == genderTypeList.firstOrNull()) "1" else "2",
                            gender = genderType.toString()
                        ),
                        context
                    ) { res ->
                        if (res.status != null && res.status) {
                            openInCustomBrowser(context, res.data?.url, res.data?.encdata)
                            titleType = null
                            genderType = null
                            transactionType = null
                            serviceType = null
                            fname = ""
                            lname = ""
                        } else {
                            displayDialogFailure(res.message)
                        }
                    }
                }
            }
        }
    }


}