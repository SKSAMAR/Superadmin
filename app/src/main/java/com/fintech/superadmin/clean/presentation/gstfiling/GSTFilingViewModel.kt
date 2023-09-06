package com.fintech.superadmin.clean.presentation.gstfiling

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
class GSTFilingViewModel
@Inject constructor(private val fintechAPI: FintechAPI) : BaseViewModel<Any>() {

    // Mutable variables for $_POST variables
    var c_name by mutableStateOf("")
    var c_num by mutableStateOf("")
    var cmnpy_name by mutableStateOf("")
    var cus_email by mutableStateOf("")
    var cus_address by mutableStateOf("")
    var trade_name by mutableStateOf("")
    var x_state by mutableStateOf("")
    var gstin by mutableStateOf("")
    var contact_p by mutableStateOf("")
    var tax_mb_no by mutableStateOf("")
    var tax_emailId by mutableStateOf("")
    var tax_type by mutableStateOf("")
    var tax_filling_period by mutableStateOf("")
    var gstr by mutableStateOf("")
    var turn_over by mutableStateOf("")
    var gst_user_id by mutableStateOf("")
    var gst_portal_pass by mutableStateOf("")
    var sales_tax_val by mutableStateOf("")
    var igst_percent by mutableStateOf("")
    var cgst_percent by mutableStateOf("")
    var sgst_percent by mutableStateOf("")
    var exempted_supply by mutableStateOf("")

    // Mutable variables for $_FILES variables
    var docs1 by mutableStateOf<Uri?>(null)
    var docs2 by mutableStateOf<Uri?>(null)
    var docs3 by mutableStateOf<Uri?>(null)
    var docs4 by mutableStateOf<Uri?>(null)


    var stateList = getIndianStatesAndUnionTerritories()


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
            } else if (trade_name.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Trade Name")
            } else if (x_state.trim().isEmpty()) {
                displayResponseMessage("Enter a valid State")
            } else if (gstin.trim().isEmpty()) {
                displayResponseMessage("Enter a valid GSTIN")
            } else if (contact_p.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Contact Person")
            } else if (tax_mb_no.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Tax Mobile Number")
            } else if (tax_emailId.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Tax Email ID")
            } else if (tax_type.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Tax Type")
            } else if (tax_filling_period.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Tax Filling Period")
            } else if (gstr.trim().isEmpty()) {
                displayResponseMessage("Enter a valid GSTR")
            } else if (turn_over.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Turn Over")
            } else if (gst_user_id.trim().isEmpty()) {
                displayResponseMessage("Enter a valid GST User ID")
            } else if (gst_portal_pass.trim().isEmpty()) {
                displayResponseMessage("Enter a valid GST Portal Password")
            } else if (sales_tax_val.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Sales Tax Value")
            } else if (igst_percent.trim().isEmpty()) {
                displayResponseMessage("Enter a valid IGST Percentage")
            } else if (cgst_percent.trim().isEmpty()) {
                displayResponseMessage("Enter a valid CGST Percentage")
            } else if (sgst_percent.trim().isEmpty()) {
                displayResponseMessage("Enter a valid SGST Percentage")
            } else if (exempted_supply.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Exempted Supply")
            } else if (docs1 == null) {
                displayResponseMessage("Select a valid Document 1")
            } else if (docs2 == null) {
                displayResponseMessage("Select a valid Document 2")
            } else if (docs3 == null) {
                displayResponseMessage("Select a valid Document 3")
            } else if (docs4 == null) {
                displayResponseMessage("Select a valid Document 4")
            } else{
                displayDialogLoading("Please wait..")
                fintechAPI.uploadFormGstFiling(
                    c_name = c_name.toRequestBody(),
                    c_num = c_num.toRequestBody(),
                    cmnpy_name = cmnpy_name.toRequestBody(),
                    cus_email = cus_email.toRequestBody(),
                    cus_address = cus_address.toRequestBody(),
                    trade_name = trade_name.toRequestBody(),
                    x_state = x_state.toRequestBody(),
                    gstin = gstin.toRequestBody(),
                    contact_p = contact_p.toRequestBody(),
                    tax_mb_no = tax_mb_no.toRequestBody(),
                    tax_emailId = tax_emailId.toRequestBody(),
                    tax_type = tax_type.toRequestBody(),
                    tax_filling_period = tax_filling_period.toRequestBody(),
                    gstr = gstr.toRequestBody(),
                    turn_over = turn_over.toRequestBody(),
                    gst_user_id = gst_user_id.toRequestBody(),
                    gst_portal_pass = gst_portal_pass.toRequestBody(),
                    sales_tax_val = sales_tax_val.toRequestBody(),
                    igst_percent = igst_percent.toRequestBody(),
                    cgst_percent = cgst_percent.toRequestBody(),
                    sgst_percent = sgst_percent.toRequestBody(),
                    exempted_supply = exempted_supply.toRequestBody(),
                    docs1 = getParted(context, docs1, "docs1"), // Assuming createMultipartBodyPart is a function to create MultipartBody.Part
                    docs2 = getParted(context, docs2, "docs2"),
                    docs3 = getParted(context, docs3, "docs3"),
                    docs4 = getParted(context, docs4, "docs4")
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


    private fun getIndianStatesAndUnionTerritories(): List<String> {
        return listOf(
            "Andaman and Nicobar Islands",
            "Andhra Pradesh",
            "Arunachal Pradesh",
            "Assam",
            "Bihar",
            "Chandigarh",
            "Chhattisgarh",
            "Dadra and Nagar Haveli and Daman and Diu",
            "Delhi",
            "Goa",
            "Gujarat",
            "Haryana",
            "Himachal Pradesh",
            "Jammu and Kashmir",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Ladakh",
            "Lakshadweep",
            "Madhya Pradesh",
            "Maharashtra",
            "Manipur",
            "Meghalaya",
            "Mizoram",
            "Nagaland",
            "Odisha",
            "Puducherry",
            "Punjab",
            "Rajasthan",
            "Sikkim",
            "Tamil Nadu",
            "Telangana",
            "Tripura",
            "Uttar Pradesh",
            "Uttarakhand",
            "West Bengal"
        )
    }


}