package com.fintech.superadmin.clean.presentation.addMember

import android.content.Context
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.clean.presentation.gstregistration.getParted
import com.fintech.superadmin.clean.presentation.gstregistration.toRequestBody
import com.fintech.superadmin.data.db.entities.User
import com.fintech.superadmin.util.Accessable
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class AddMemberViewModel
@Inject constructor(private val fintechAPI: FintechAPI) : BaseViewModel<Any>() {

    lateinit var user: User
    var selectedUserType by mutableStateOf<UserTypeSystem?>(null)
    var u_type by mutableStateOf("")
    var mobile by mutableStateOf("")
    var debit_wallet by mutableStateOf("")
    var credit_wallet by mutableStateOf("")
    var fname by mutableStateOf("")
    var lname by mutableStateOf("")
    var email by mutableStateOf("")
    var address by mutableStateOf("")
    var city by mutableStateOf("")
    var xstate by mutableStateOf("")
    var pincode by mutableStateOf("")
    var status by mutableStateOf("")
    var password by mutableStateOf("")
    var firm_name by mutableStateOf("")
    var dob by mutableStateOf("")
    var gender by mutableStateOf("")
    var Landmark by mutableStateOf("")
    var aadhar_no by mutableStateOf("")
    var pan_no by mutableStateOf("")
    var gst_no by mutableStateOf("")


    var aadhar_front by mutableStateOf<Uri?>(null)
    var aadhar_back by mutableStateOf<Uri?>(null)
    var pancard by mutableStateOf<Uri?>(null)

    fun validateTheForm(context: ComponentActivity) {
        if (Accessable.isAccessable()) {
            if (selectedUserType == null) {
                displayResponseMessage("Select a valid User Type")
            } else if (fname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid First Name")
            } else if (lname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Last Name")
            } else if (mobile.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Mobile number")
            } else if (email.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Email address")
            } else if (address.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Address")
            } else if (city.trim().isEmpty()) {
                displayResponseMessage("Enter a valid City")
            } else if (xstate.trim().isEmpty()) {
                displayResponseMessage("Enter a valid State")
            } else if (pincode.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Pincode")
            } else if (status.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Status")
            } else if (password.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Password")
            } else if (firm_name.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Firm Name")
            } else if (dob.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Date of Birth")
            } else if (gender.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Gender")
            } else if (Landmark.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Landmark")
            } else if (aadhar_no.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Aadhar Number")
            } else if (pan_no.trim().isEmpty()) {
                displayResponseMessage("Enter a valid PAN Number")
            } else if (aadhar_front == null) {
                displayResponseMessage("Select a valid Aadhaar Front Image")
            } else if (aadhar_back == null) {
                displayResponseMessage("Select a valid Aadhaar Back Image")
            } else if (pancard == null) {
                displayResponseMessage("Select a valid Pan Image")
            } else {
                displayDialogLoading("Please wait..")
                fintechAPI.addNewMember(
                    u_type = selectedUserType?.type?.toRequestBody() ?: "1".toRequestBody(),
                    fname = fname.toRequestBody(),
                    lname = lname.toRequestBody(),
                    mobile = mobile.toRequestBody(),
                    email = email.toRequestBody(),
                    address = address.toRequestBody(),
                    city = city.toRequestBody(),
                    state = xstate.toRequestBody(),
                    pincode = pincode.toRequestBody(),
                    status = status.toRequestBody(),
                    password = password.toRequestBody(),
                    firm_name = firm_name.toRequestBody(),
                    dob = dob.toRequestBody(),
                    gender = gender.toRequestBody(),
                    Landmark = Landmark.toRequestBody(),
                    aadhar_no = aadhar_no.toRequestBody(),
                    pan_no = pan_no.toRequestBody(),
                    gst_no = gst_no.toRequestBody(),
                    debit_wallet = debit_wallet.toRequestBody(),
                    credit_wallet = credit_wallet.toRequestBody(),
                    aadhar_back = getParted(context, aadhar_back, "aadhar_back"),
                    aadhar_front = getParted(context, aadhar_front, "aadhar_front"),
                    pancard = getParted(context, pancard, "pancard"),
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


    fun addableList(): List<UserTypeSystem> {
        try {
            val aList = ArrayList<UserTypeSystem>()
            if (user.userstatus == "3") {
                aList.add(UserTypeSystem("Distributor", "2"))
                aList.add(UserTypeSystem("Retailer", "1"))
            }
            if (user.userstatus == "47") {
                aList.add(UserTypeSystem("Retailer", "1"))
            }
            return aList
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }


    fun additionalPerson(): Boolean {
        try {
            if (user.userstatus == "3" && selectedUserType?.type == "Retailer") {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }


}

data class UserTypeSystem(
    val name: String, val type: String
) {
    override fun toString(): String {
        return name
    }
}