package com.fintech.superadmin.clean.presentation.itrfilingsalraied

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.clean.presentation.gstregistration.getParted
import com.fintech.superadmin.util.Accessable
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class ITRFillingSalariedViewModel
@Inject constructor(private val fintechAPI: FintechAPI) : BaseViewModel<Any>() {


    var c_name by mutableStateOf("")
    var c_num by mutableStateOf("")
    var cmnpy_name by mutableStateOf("")
    var cus_email by mutableStateOf("")
    var x_state by mutableStateOf("")
    var cus_address by mutableStateOf("")
    var applicant_name by mutableStateOf("")
    var father_name by mutableStateOf("")
    var dob by mutableStateOf("")
    var pan_no by mutableStateOf("")
    var aadhar_no by mutableStateOf("")
    var applicant_address by mutableStateOf("")
    var pin_code by mutableStateOf("")
    var applicant_email by mutableStateOf("")
    var applicant_mobile_no by mutableStateOf("")
    var finacial_year by mutableStateOf("")
    var acc_no by mutableStateOf("")
    var ifsc_code by mutableStateOf("")
    var gross_salary by mutableStateOf("")
    var decduction by mutableStateOf("")
    var net_salary by mutableStateOf("")
    var turn_over by mutableStateOf("")
    var income by mutableStateOf("")
    var house_prop by mutableStateOf("")
    var bank_account by mutableStateOf("")
    var tution by mutableStateOf("")
    var health_insurance by mutableStateOf("")
    var other_deduction by mutableStateOf("")
    var deduction_amount by mutableStateOf("")
    var image by mutableStateOf("")
    var imageUri by mutableStateOf<Uri?>(null)


    fun validateTheForm(context: ComponentActivity) {
        if (Accessable.isAccessable()) {
            if (c_name.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Customer Name")
            } else if (c_num.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Customer Mobile Number")
            } else if (cmnpy_name.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Company Name")
            } else if (cus_email.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Customer Email")
            } else if (x_state.trim().isEmpty()) {
                displayResponseMessage("Enter a valid State")
            } else if (cus_address.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Customer Address")
            } else if (applicant_name.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Applicant Name")
            } else if (father_name.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Father's Name")
            } else if (dob.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Date of Birth")
            } else if (pan_no.trim().isEmpty()) {
                displayResponseMessage("Enter a valid PAN Number")
            } else if (aadhar_no.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Aadhar Number")
            } else if (applicant_address.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Applicant Address")
            } else if (pin_code.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Pin Code")
            } else if (applicant_email.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Applicant Email")
            } else if (applicant_mobile_no.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Applicant Mobile Number")
            } else if (finacial_year.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Financial Year")
            } else if (acc_no.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Account Number")
            } else if (ifsc_code.trim().isEmpty()) {
                displayResponseMessage("Enter a valid IFSC Code")
            } else if (gross_salary.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Gross Salary")
            } else if (decduction.trim().isEmpty()) {
                displayResponseMessage("Enter valid Deductions")
            } else if (net_salary.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Net Salary")
            } else if (turn_over.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Turnover")
            } else if (income.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Income")
            } else if (house_prop.trim().isEmpty()) {
                displayResponseMessage("Enter a valid House Property")
            } else if (bank_account.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Bank Account")
            } else if (tution.trim().isEmpty()) {
                displayResponseMessage("Enter valid Tution Fees")
            } else if (health_insurance.trim().isEmpty()) {
                displayResponseMessage("Enter valid Health Insurance Premium")
            } else if (other_deduction.trim().isEmpty()) {
                displayResponseMessage("Enter valid Other Deductions")
            } else if (deduction_amount.trim().isEmpty()) {
                displayResponseMessage("Enter valid Deduction Amount")
            } else if (imageUri == null) {
                displayResponseMessage("Select an Image")
            } else {

                displayDialogLoading("Please Wait..")

                fintechAPI.submitITRFillingSalaraied(
                    c_name = c_name.toRequestBody(),
                    c_num = c_num.toRequestBody(),
                    cmnpy_name = cmnpy_name.toRequestBody(),
                    cus_email = cus_email.toRequestBody(),
                    state = x_state.toRequestBody(), // Note: Changed the variable name to x_state
                    cus_address = cus_address.toRequestBody(),
                    applicant_name = applicant_name.toRequestBody(),
                    father_name = father_name.toRequestBody(),
                    dob = dob.toRequestBody(),
                    pan_no = pan_no.toRequestBody(),
                    aadhar_no = aadhar_no.toRequestBody(),
                    applicant_address = applicant_address.toRequestBody(),
                    pin_code = pin_code.toRequestBody(),
                    applicant_email = applicant_email.toRequestBody(),
                    applicant_mobile_no = applicant_mobile_no.toRequestBody(),
                    finacial_year = finacial_year.toRequestBody(), // Note: Corrected the variable name to finacial_year
                    acc_no = acc_no.toRequestBody(),
                    ifsc_code = ifsc_code.toRequestBody(),
                    gross_salary = gross_salary.toRequestBody(),
                    decduction = decduction.toRequestBody(), // Note: Corrected the variable name to decduction
                    net_salary = net_salary.toRequestBody(),
                    turn_over = turn_over.toRequestBody(),
                    income = income.toRequestBody(),
                    house_prop = house_prop.toRequestBody(),
                    bank_account = bank_account.toRequestBody(),
                    tution = tution.toRequestBody(),
                    health_insurance = health_insurance.toRequestBody(),
                    other_deduction = other_deduction.toRequestBody(),
                    deduction_amount = deduction_amount.toRequestBody(),
                    image = getParted(context, imageUri, "image") // Assuming you have the image Uri in imageUri
                )
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        dismissDialog()
                        displayResponseMessage("" + it.message){
                            if (it.status){
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