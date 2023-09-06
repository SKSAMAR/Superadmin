package com.fintech.superadmin.clean.presentation.companyIncoporation

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
class CompanyIncoViewModel
@Inject constructor(private val fintechAPI: FintechAPI) : BaseViewModel<Any>() {


    var c_name by mutableStateOf("")
    var c_num by mutableStateOf("")
    var cmnpy_name by mutableStateOf("")
    var cus_email by mutableStateOf("")
    var x_state by mutableStateOf("")
    var cus_address by mutableStateOf("")
    var prefernce1 by mutableStateOf("")
    var prefernce2 by mutableStateOf("")
    var activity by mutableStateOf("")

    // Director1 variables
    var d_fname by mutableStateOf("")
    var d_mname by mutableStateOf("")
    var d_lname by mutableStateOf("")
    var d_gender by mutableStateOf("")
    var d_dob by mutableStateOf("")
    var d_contact_no by mutableStateOf("")
    var d_email by mutableStateOf("")
    var d_father_name by mutableStateOf("")
    var d_father_mname by mutableStateOf("")
    var d_father_lname by mutableStateOf("")

    // Director1 file uploads
    var d_passportphoto by mutableStateOf<Uri?>(null)
    var d_aadhar_pic by mutableStateOf<Uri?>(null)
    var d_pancard by mutableStateOf<Uri?>(null)

    // Director2 variables
    var d2_fname by mutableStateOf("")
    var d2_mname by mutableStateOf("")
    var d2_lname by mutableStateOf("")
    var d2_gender by mutableStateOf("")
    var d2_dob by mutableStateOf("")
    var d2_contact_no by mutableStateOf("")
    var d2_email by mutableStateOf("")
    var d2_father_name by mutableStateOf("")
    var d2_father_mname by mutableStateOf("")
    var d2_father_lname by mutableStateOf("")

    // Director2 file uploads
    var d2_passportphoto by mutableStateOf<Uri?>(null)
    var d2_aadhar_pic by mutableStateOf<Uri?>(null)
    var d2_pancard by mutableStateOf<Uri?>(null)

    // Director3 variables
    var d3_fname by mutableStateOf("")
    var d3_mname by mutableStateOf("")
    var d3_lname by mutableStateOf("")
    var d3_gender by mutableStateOf("")
    var d3_dob by mutableStateOf("")
    var d3_contact_no by mutableStateOf("")
    var d3_email by mutableStateOf("")
    var d3_father_name by mutableStateOf("")
    var d3_father_mname by mutableStateOf("")
    var d3_father_lname by mutableStateOf("")

    // Director3 file uploads
    var d3_passportphoto by mutableStateOf<Uri?>(null)
    var d3_aadhar_pic by mutableStateOf<Uri?>(null)
    var d3_pancard by mutableStateOf<Uri?>(null)

    // Director4 variables
    var d4_fname by mutableStateOf("")
    var d4_mname by mutableStateOf("")
    var d4_lname by mutableStateOf("")
    var d4_gender by mutableStateOf("")
    var d4_dob by mutableStateOf("")
    var d4_contact_no by mutableStateOf("")
    var d4_email by mutableStateOf("")
    var d4_father_name by mutableStateOf("")
    var d4_father_mname by mutableStateOf("")
    var d4_father_lname by mutableStateOf("")

    // Director4 file uploads
    var d4_passportphoto by mutableStateOf<Uri?>(null)
    var d4_aadhar_pic by mutableStateOf<Uri?>(null)
    var d4_pancard by mutableStateOf<Uri?>(null)

    // Director5 variables
    var d5_fname by mutableStateOf("")
    var d5_mname by mutableStateOf("")
    var d5_lname by mutableStateOf("")
    var d5_gender by mutableStateOf("")
    var d5_dob by mutableStateOf("")
    var d5_contact_no by mutableStateOf("")
    var d5_email by mutableStateOf("")
    var d5_father_name by mutableStateOf("")
    var d5_father_mname by mutableStateOf("")
    var d5_father_lname by mutableStateOf("")

    // Director5 file uploads
    var d5_passportphoto by mutableStateOf<Uri?>(null)
    var d5_aadhar_pic by mutableStateOf<Uri?>(null)
    var d5_pancard by mutableStateOf<Uri?>(null)

    // Director6 variables
    var d6_fname by mutableStateOf("")
    var d6_mname by mutableStateOf("")
    var d6_lname by mutableStateOf("")
    var d6_gender by mutableStateOf("")
    var d6_dob by mutableStateOf("")
    var d6_contact_no by mutableStateOf("")
    var d6_email by mutableStateOf("")
    var d6_father_name by mutableStateOf("")
    var d6_father_mname by mutableStateOf("")
    var d6_father_lname by mutableStateOf("")

    // Director6 file uploads
    var d6_passportphoto by mutableStateOf<Uri?>(null)
    var d6_aadhar_pic by mutableStateOf<Uri?>(null)
    var d6_pancard by mutableStateOf<Uri?>(null)

    var copmany_registration by mutableStateOf("")
    var company_type by mutableStateOf("")


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
            } else if (x_state.trim().isEmpty()) {
                displayResponseMessage("Enter a valid State")
            } else if (cus_address.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Customer Address")
            } else if (prefernce1.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Preference 1")
            } else if (prefernce2.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Preference 2")
            } else if (activity.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Activity")
            } else if (d_fname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director1 First Name")
            } else if (d_mname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director1 Middle Name")
            } else if (d_lname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director1 Last Name")
            } else if (d_gender.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director1 Gender")
            } else if (d_dob.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director1 Date of Birth")
            } else if (d_contact_no.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director1 Contact Number")
            } else if (d_email.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director1 Email")
            } else if (d_father_name.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director1 Father's Name")
            } else if (d_father_mname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director1 Father's Middle Name")
            } else if (d_father_lname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director1 Father's Last Name")
            } else if (d2_fname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director2 First Name")
            } else if (d2_mname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director2 Middle Name")
            } else if (d2_lname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director2 Last Name")
            } else if (d2_gender.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director2 Gender")
            } else if (d2_dob.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director2 Date of Birth")
            } else if (d2_contact_no.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director2 Contact Number")
            } else if (d2_email.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director2 Email")
            } else if (d2_father_name.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director2 Father's Name")
            } else if (d2_father_mname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director2 Father's Middle Name")
            } else if (d2_father_lname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director2 Father's Last Name")
            } else if (d3_fname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director3 First Name")
            } else if (d3_mname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director3 Middle Name")
            } else if (d3_lname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director3 Last Name")
            } else if (d3_gender.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director3 Gender")
            } else if (d3_dob.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director3 Date of Birth")
            } else if (d3_contact_no.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director3 Contact Number")
            } else if (d3_email.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director3 Email")
            } else if (d3_father_name.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director3 Father's Name")
            } else if (d3_father_mname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director3 Father's Middle Name")
            } else if (d3_father_lname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director3 Father's Last Name")
            } else if (d4_fname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director4 First Name")
            } else if (d4_mname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director4 Middle Name")
            } else if (d4_lname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director4 Last Name")
            } else if (d4_gender.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director4 Gender")
            } else if (d4_dob.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director4 Date of Birth")
            } else if (d4_contact_no.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director4 Contact Number")
            } else if (d4_email.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director4 Email")
            } else if (d4_father_name.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director4 Father's Name")
            } else if (d4_father_mname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director4 Father's Middle Name")
            } else if (d4_father_lname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director4 Father's Last Name")
            } else if (d5_fname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director5 First Name")
            } else if (d5_mname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director5 Middle Name")
            } else if (d5_lname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director5 Last Name")
            } else if (d5_gender.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director5 Gender")
            } else if (d5_dob.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director5 Date of Birth")
            } else if (d5_contact_no.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director5 Contact Number")
            } else if (d5_email.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director5 Email")
            } else if (d5_father_name.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director5 Father's Name")
            } else if (d5_father_mname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director5 Father's Middle Name")
            } else if (d5_father_lname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director5 Father's Last Name")
            } else if (d6_fname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director6 First Name")
            } else if (d6_mname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director6 Middle Name")
            } else if (d6_lname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director6 Last Name")
            } else if (d6_gender.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director6 Gender")
            } else if (d6_dob.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director6 Date of Birth")
            } else if (d6_contact_no.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director6 Contact Number")
            } else if (d6_email.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director6 Email")
            } else if (d6_father_name.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director6 Father's Name")
            } else if (d6_father_mname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director6 Father's Middle Name")
            } else if (d6_father_lname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Director6 Father's Last Name")
            } else if (copmany_registration.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Company Registration")
            } else if (company_type.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Company Type")
            } else {
                // All fields are valid, proceed with your logic
                displayDialogLoading("Please wait..")
                fintechAPI.uploadFormCompanyIcoOp(
                    c_name = c_name.toRequestBody(),
                    c_num = c_num.toRequestBody(),
                    cmnpy_name = cmnpy_name.toRequestBody(),
                    cus_email = cus_email.toRequestBody(),
                    x_state = x_state.toRequestBody(),
                    cus_address = cus_address.toRequestBody(),
                    prefernce1 = prefernce1.toRequestBody(),
                    prefernce2 = prefernce2.toRequestBody(),
                    activity = activity.toRequestBody(),

                    d_fname = d_fname.toRequestBody(),
                    d_mname = d_mname.toRequestBody(),
                    d_lname = d_lname.toRequestBody(),
                    d_gender = d_gender.toRequestBody(),
                    d_dob = d_dob.toRequestBody(),
                    d_contact_no = d_contact_no.toRequestBody(),
                    d_email = d_email.toRequestBody(),
                    d_father_name = d_father_name.toRequestBody(),
                    d_father_mname = d_father_mname.toRequestBody(),
                    d_father_lname = d_father_lname.toRequestBody(),

                    d_passportphoto = getParted(context, d_passportphoto, "d_passportphoto"),
                    d_aadhar_pic = getParted(context, d_aadhar_pic, "d_aadhar_pic"),
                    d_pancard = getParted(context, d_pancard, "d_pancard"),

                    d2_fname = d2_fname.toRequestBody(),
                    d2_mname = d2_mname.toRequestBody(),
                    d2_lname = d2_lname.toRequestBody(),
                    d2_gender = d2_gender.toRequestBody(),
                    d2_dob = d2_dob.toRequestBody(),
                    d2_contact_no = d2_contact_no.toRequestBody(),
                    d2_email = d2_email.toRequestBody(),
                    d2_father_name = d2_father_name.toRequestBody(),
                    d2_father_mname = d2_father_mname.toRequestBody(),
                    d2_father_lname = d2_father_lname.toRequestBody(),

                    d2_passportphoto = getParted(context, d2_passportphoto, "d2_passportphoto"),
                    d2_aadhar_pic = getParted(context, d2_aadhar_pic, "d2_aadhar_pic"),
                    d2_pancard = getParted(context, d2_pancard, "d2_pancard"),


                    d3_fname = d3_fname.toRequestBody(),
                    d3_mname = d3_mname.toRequestBody(),
                    d3_lname = d3_lname.toRequestBody(),
                    d3_gender = d3_gender.toRequestBody(),
                    d3_dob = d3_dob.toRequestBody(),
                    d3_contact_no = d3_contact_no.toRequestBody(),
                    d3_email = d3_email.toRequestBody(),
                    d3_father_name = d3_father_name.toRequestBody(),
                    d3_father_mname = d3_father_mname.toRequestBody(),
                    d3_father_lname = d3_father_lname.toRequestBody(),

                    d3_passportphoto = getParted(context, d3_passportphoto, "d3_passportphoto"),
                    d3_aadhar_pic = getParted(context, d3_aadhar_pic, "d3_aadhar_pic"),
                    d3_pancard = getParted(context, d3_pancard, "d3_pancard"),


                    d4_fname = d4_fname.toRequestBody(),
                    d4_mname = d4_mname.toRequestBody(),
                    d4_lname = d4_lname.toRequestBody(),
                    d4_gender = d4_gender.toRequestBody(),
                    d4_dob = d4_dob.toRequestBody(),
                    d4_contact_no = d4_contact_no.toRequestBody(),
                    d4_email = d4_email.toRequestBody(),
                    d4_father_name = d4_father_name.toRequestBody(),
                    d4_father_mname = d4_father_mname.toRequestBody(),
                    d4_father_lname = d4_father_lname.toRequestBody(),

                    d4_passportphoto = getParted(context, d4_passportphoto, "d4_passportphoto"),
                    d4_aadhar_pic = getParted(context, d4_aadhar_pic, "d4_aadhar_pic"),
                    d4_pancard = getParted(context, d4_pancard, "d4_pancard"),

                    d5_fname = d5_fname.toRequestBody(),
                    d5_mname = d5_mname.toRequestBody(),
                    d5_lname = d5_lname.toRequestBody(),
                    d5_gender = d5_gender.toRequestBody(),
                    d5_dob = d5_dob.toRequestBody(),
                    d5_contact_no = d5_contact_no.toRequestBody(),
                    d5_email = d5_email.toRequestBody(),
                    d5_father_name = d5_father_name.toRequestBody(),
                    d5_father_mname = d5_father_mname.toRequestBody(),
                    d5_father_lname = d5_father_lname.toRequestBody(),

                    d5_passportphoto = getParted(context, d5_passportphoto, "d5_passportphoto"),
                    d5_aadhar_pic = getParted(context, d5_aadhar_pic, "d5_aadhar_pic"),
                    d5_pancard = getParted(context, d5_pancard, "d5_pancard"),

                    d6_fname = d6_fname.toRequestBody(),
                    d6_mname = d6_mname.toRequestBody(),
                    d6_lname = d6_lname.toRequestBody(),
                    d6_gender = d6_gender.toRequestBody(),
                    d6_dob = d6_dob.toRequestBody(),
                    d6_contact_no = d6_contact_no.toRequestBody(),
                    d6_email = d6_email.toRequestBody(),
                    d6_father_name = d6_father_name.toRequestBody(),
                    d6_father_mname = d6_father_mname.toRequestBody(),
                    d6_father_lname = d6_father_lname.toRequestBody(),

                    d6_passportphoto = getParted(context, d6_passportphoto, "d6_passportphoto"),
                    d6_aadhar_pic = getParted(context, d6_aadhar_pic, "d6_aadhar_pic"),
                    d6_pancard = getParted(context, d6_pancard, "d6_pancard"),


                    copmany_registration = copmany_registration.toRequestBody(),
                    company_type = company_type.toRequestBody()
                ).subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ result ->
                        dismissDialog()
                        displayResponseMessage("" + result.message) {
                            if (result.status) {
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