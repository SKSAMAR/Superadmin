package com.fintech.superadmin.clean.presentation.gstregistration

import android.content.Context
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.util.Accessable
import com.fintech.superadmin.util.PathsInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class GSTRegistrationViewModel @Inject constructor(private val fintechAPI: FintechAPI) :
    BaseViewModel<Any>() {


    var c_name by mutableStateOf("")
    var c_num by mutableStateOf("")
    var cmnpy_name by mutableStateOf("")
    var cus_email by mutableStateOf("")
    var x_state by mutableStateOf("")
    var cus_address by mutableStateOf("")
    var pan_name by mutableStateOf("")
    var pan_no by mutableStateOf("")
    var contact_p by mutableStateOf("")
    var mobile by mutableStateOf("")
    var email by mutableStateOf("")
    var bussiness_type by mutableStateOf("")
    var consitituion_of_buss by mutableStateOf("")
    var option_of_composition by mutableStateOf("")
    var commenceement_b by mutableStateOf("")

    // Director 1
    var d_fname by mutableStateOf("")
    var d_mname by mutableStateOf("")
    var d_lname by mutableStateOf("")
    var d_gender by mutableStateOf("")
    var d_dob by mutableStateOf("")
    var d_contact_no by mutableStateOf("")
    var d_email by mutableStateOf("")
    var d_aadhar_no by mutableStateOf("")
    var d_address by mutableStateOf("")
    var d_building_no by mutableStateOf("")
    var d_postoffice by mutableStateOf("")
    var d_locality by mutableStateOf("")
    var d_state by mutableStateOf("")
    var d_city by mutableStateOf("")

    var d_profile by mutableStateOf<Uri?>(null)

    var d2_fname by mutableStateOf("")
    var d2_mname by mutableStateOf("")
    var d2_lname by mutableStateOf("")
    var d2_gender by mutableStateOf("")
    var d2_dob by mutableStateOf("")
    var d2_contact_no by mutableStateOf("")
    var d2_email by mutableStateOf("")
    var d2_aadhar_no by mutableStateOf("")
    var d2_address by mutableStateOf("")
    var d2_building_no by mutableStateOf("")
    var d2_postoffice by mutableStateOf("")
    var d2_locality by mutableStateOf("")
    var d2_state by mutableStateOf("")
    var d2_city by mutableStateOf("")
    var d2_profile by mutableStateOf<Uri?>(null)


    var d3_fname by mutableStateOf("")
    var d3_mname by mutableStateOf("")
    var d3_lname by mutableStateOf("")
    var d3_gender by mutableStateOf("")
    var d3_dob by mutableStateOf("")
    var d3_contact_no by mutableStateOf("")
    var d3_email by mutableStateOf("")
    var d3_aadhar_no by mutableStateOf("")
    var d3_address by mutableStateOf("")
    var d3_building_no by mutableStateOf("")
    var d3_postoffice by mutableStateOf("")
    var d3_locality by mutableStateOf("")
    var d3_state by mutableStateOf("")
    var d3_city by mutableStateOf("")
    var d3_profile by mutableStateOf<Uri?>(null)


    var d4_fname by mutableStateOf("")
    var d4_mname by mutableStateOf("")
    var d4_lname by mutableStateOf("")
    var d4_gender by mutableStateOf("")
    var d4_dob by mutableStateOf("")
    var d4_contact_no by mutableStateOf("")
    var d4_email by mutableStateOf("")
    var d4_aadhar_no by mutableStateOf("")
    var d4_address by mutableStateOf("")
    var d4_building_no by mutableStateOf("")
    var d4_postoffice by mutableStateOf("")
    var d4_locality by mutableStateOf("")
    var d4_state by mutableStateOf("")
    var d4_city by mutableStateOf("")
    var d4_profile by mutableStateOf<Uri?>(null)


    var d5_fname by mutableStateOf("")
    var d5_mname by mutableStateOf("")
    var d5_lname by mutableStateOf("")
    var d5_gender by mutableStateOf("")
    var d5_dob by mutableStateOf("")
    var d5_contact_no by mutableStateOf("")
    var d5_email by mutableStateOf("")
    var d5_aadhar_no by mutableStateOf("")
    var d5_address by mutableStateOf("")
    var d5_building_no by mutableStateOf("")
    var d5_postoffice by mutableStateOf("")
    var d5_locality by mutableStateOf("")
    var d5_state by mutableStateOf("")
    var d5_city by mutableStateOf("")
    var d5_profile by mutableStateOf<Uri?>(null)


    var d6_fname by mutableStateOf("")
    var d6_mname by mutableStateOf("")
    var d6_lname by mutableStateOf("")
    var d6_gender by mutableStateOf("")
    var d6_dob by mutableStateOf("")
    var d6_contact_no by mutableStateOf("")
    var d6_email by mutableStateOf("")
    var d6_aadhar_no by mutableStateOf("")
    var d6_address by mutableStateOf("")
    var d6_building_no by mutableStateOf("")
    var d6_postoffice by mutableStateOf("")
    var d6_locality by mutableStateOf("")
    var d6_state by mutableStateOf("")
    var d6_city by mutableStateOf("")
    var d6_profile by mutableStateOf<Uri?>(null)


    var ppb_address by mutableStateOf("")
    var ppb_building by mutableStateOf("")
    var ppb_postoffice by mutableStateOf("")
    var ppb_locality by mutableStateOf("")
    var ppb_state by mutableStateOf("")
    var bank_account by mutableStateOf("")
    var ifsc_code by mutableStateOf("")
    var acc_type by mutableStateOf("")

    var product1 by mutableStateOf("")
    var product2 by mutableStateOf("")
    var product3 by mutableStateOf("")
    var product4 by mutableStateOf("")
    var product5 by mutableStateOf("")

    var prossession by mutableStateOf("")
    var buss_activity by mutableStateOf("")
    var authorised_sign by mutableStateOf("")
    var place by mutableStateOf("")

    // Docs Uploads
    var docs1 by mutableStateOf<Uri?>(null)
    var docs2 by mutableStateOf<Uri?>(null)
    var docs3 by mutableStateOf<Uri?>(null)
    var docs4 by mutableStateOf<Uri?>(null)


    fun validateTheForm(context: ComponentActivity) {
        if (Accessable.isAccessable()) {
            if (isFieldEmpty(c_name)) {
                displayResponseMessage("Enter a valid Customer Name")
            } else if (isFieldEmpty(c_num)) {
                displayResponseMessage("Enter a valid Customer Mobile Number")
            } else if (isFieldEmpty(cmnpy_name)) {
                displayResponseMessage("Enter a valid Company Name")
            } else if (isFieldEmpty(cus_email)) {
                displayResponseMessage("Enter a valid Customer Email")
            } else if (isFieldEmpty(x_state)) {
                displayResponseMessage("Enter a valid State")
            } else if (isFieldEmpty(cus_address)) {
                displayResponseMessage("Enter a valid Customer Address")
            } else if (isFieldEmpty(pan_name)) {
                displayResponseMessage("Enter a valid PAN Name")
            } else if (isFieldEmpty(pan_no)) {
                displayResponseMessage("Enter a valid PAN Number")
            } else if (isFieldEmpty(contact_p)) {
                displayResponseMessage("Enter a valid Contact Person")
            } else if (isFieldEmpty(mobile)) {
                displayResponseMessage("Enter a valid Mobile Number")
            } else if (isFieldEmpty(email)) {
                displayResponseMessage("Enter a valid Email")
            } else if (isFieldEmpty(bussiness_type)) {
                displayResponseMessage("Enter a valid Business Type")
            } else if (isFieldEmpty(consitituion_of_buss)) {
                displayResponseMessage("Enter a valid Constitution of Business")
            } else if (isFieldEmpty(option_of_composition)) {
                displayResponseMessage("Enter a valid Option of Composition")
            } else if (isFieldEmpty(commenceement_b)) {
                displayResponseMessage("Enter a valid Commencement Date")
            } else if (isFieldEmpty(d_fname)) {
                displayResponseMessage("Enter a valid Director 1 First Name")
            } else if (isFieldEmpty(d_mname)) {
                displayResponseMessage("Enter a valid Director 1 Middle Name")
            } else if (isFieldEmpty(d_lname)) {
                displayResponseMessage("Enter a valid Director 1 Last Name")
            } else if (isFieldEmpty(d_gender)) {
                displayResponseMessage("Enter a valid Director 1 Gender")
            } else if (isFieldEmpty(d_dob)) {
                displayResponseMessage("Enter a valid Director 1 Date of Birth")
            } else if (isFieldEmpty(d_contact_no)) {
                displayResponseMessage("Enter a valid Director 1 Contact Number")
            } else if (isFieldEmpty(d_email)) {
                displayResponseMessage("Enter a valid Director 1 Email")
            } else if (isFieldEmpty(d_aadhar_no)) {
                displayResponseMessage("Enter a valid Director 1 Aadhar Number")
            } else if (isFieldEmpty(d_address)) {
                displayResponseMessage("Enter a valid Director 1 Address")
            } else if (isFieldEmpty(d_building_no)) {
                displayResponseMessage("Enter a valid Director 1 Building Number")
            } else if (isFieldEmpty(d_postoffice)) {
                displayResponseMessage("Enter a valid Director 1 Post Office")
            } else if (isFieldEmpty(d_locality)) {
                displayResponseMessage("Enter a valid Director 1 Locality")
            } else if (isFieldEmpty(d_state)) {
                displayResponseMessage("Enter a valid Director 1 State")
            } else if (isFieldEmpty(d_city)) {
                displayResponseMessage("Enter a valid Director 1 City")
            } else if (isUriEmpty(d_profile)) {
                displayResponseMessage("Upload a valid Director 1 Profile Image")
            } else if (isFieldEmpty(d2_fname)) {
                displayResponseMessage("Enter a valid Director 2 First Name")
            } else if (isFieldEmpty(d2_mname)) {
                displayResponseMessage("Enter a valid Director 2 Middle Name")
            } else if (isFieldEmpty(d2_lname)) {
                displayResponseMessage("Enter a valid Director 2 Last Name")
            } else if (isFieldEmpty(d2_gender)) {
                displayResponseMessage("Enter a valid Director 2 Gender")
            } else if (isFieldEmpty(d2_dob)) {
                displayResponseMessage("Enter a valid Director 2 Date of Birth")
            } else if (isFieldEmpty(d2_contact_no)) {
                displayResponseMessage("Enter a valid Director 2 Contact Number")
            } else if (isFieldEmpty(d2_email)) {
                displayResponseMessage("Enter a valid Director 2 Email")
            } else if (isFieldEmpty(d2_aadhar_no)) {
                displayResponseMessage("Enter a valid Director 2 Aadhar Number")
            } else if (isFieldEmpty(d2_address)) {
                displayResponseMessage("Enter a valid Director 2 Address")
            } else if (isFieldEmpty(d2_building_no)) {
                displayResponseMessage("Enter a valid Director 2 Building Number")
            } else if (isFieldEmpty(d2_postoffice)) {
                displayResponseMessage("Enter a valid Director 2 Post Office")
            } else if (isFieldEmpty(d2_locality)) {
                displayResponseMessage("Enter a valid Director 2 Locality")
            } else if (isFieldEmpty(d2_state)) {
                displayResponseMessage("Enter a valid Director 2 State")
            } else if (isFieldEmpty(d2_city)) {
                displayResponseMessage("Enter a valid Director 2 City")
            } else if (isUriEmpty(d2_profile)) {
                displayResponseMessage("Upload a valid Director 2 Profile Image")
            } else if (isFieldEmpty(d3_fname)) {
                displayResponseMessage("Enter a valid Director 3 First Name")
            } else if (isFieldEmpty(d3_mname)) {
                displayResponseMessage("Enter a valid Director 3 Middle Name")
            } else if (isFieldEmpty(d3_lname)) {
                displayResponseMessage("Enter a valid Director 3 Last Name")
            } else if (isFieldEmpty(d3_gender)) {
                displayResponseMessage("Enter a valid Director 3 Gender")
            } else if (isFieldEmpty(d3_dob)) {
                displayResponseMessage("Enter a valid Director 3 Date of Birth")
            } else if (isFieldEmpty(d3_contact_no)) {
                displayResponseMessage("Enter a valid Director 3 Contact Number")
            } else if (isFieldEmpty(d3_email)) {
                displayResponseMessage("Enter a valid Director 3 Email")
            } else if (isFieldEmpty(d3_aadhar_no)) {
                displayResponseMessage("Enter a valid Director 3 Aadhar Number")
            } else if (isFieldEmpty(d3_address)) {
                displayResponseMessage("Enter a valid Director 3 Address")
            } else if (isFieldEmpty(d3_building_no)) {
                displayResponseMessage("Enter a valid Director 3 Building Number")
            } else if (isFieldEmpty(d3_postoffice)) {
                displayResponseMessage("Enter a valid Director 3 Post Office")
            } else if (isFieldEmpty(d3_locality)) {
                displayResponseMessage("Enter a valid Director 3 Locality")
            } else if (isFieldEmpty(d3_state)) {
                displayResponseMessage("Enter a valid Director 3 State")
            } else if (isFieldEmpty(d3_city)) {
                displayResponseMessage("Enter a valid Director 3 City")
            } else if (isUriEmpty(d3_profile)) {
                displayResponseMessage("Upload a valid Director 3 Profile Image")
            }

// Validation for Director 4
            else if (isFieldEmpty(d4_fname)) {
                displayResponseMessage("Enter a valid Director 4 First Name")
            } else if (isFieldEmpty(d4_mname)) {
                displayResponseMessage("Enter a valid Director 4 Middle Name")
            } else if (isFieldEmpty(d4_lname)) {
                displayResponseMessage("Enter a valid Director 4 Last Name")
            } else if (isFieldEmpty(d4_gender)) {
                displayResponseMessage("Enter a valid Director 4 Gender")
            } else if (isFieldEmpty(d4_dob)) {
                displayResponseMessage("Enter a valid Director 4 Date of Birth")
            } else if (isFieldEmpty(d4_contact_no)) {
                displayResponseMessage("Enter a valid Director 4 Contact Number")
            } else if (isFieldEmpty(d4_email)) {
                displayResponseMessage("Enter a valid Director 4 Email")
            } else if (isFieldEmpty(d4_aadhar_no)) {
                displayResponseMessage("Enter a valid Director 4 Aadhar Number")
            } else if (isFieldEmpty(d4_address)) {
                displayResponseMessage("Enter a valid Director 4 Address")
            } else if (isFieldEmpty(d4_building_no)) {
                displayResponseMessage("Enter a valid Director 4 Building Number")
            } else if (isFieldEmpty(d4_postoffice)) {
                displayResponseMessage("Enter a valid Director 4 Post Office")
            } else if (isFieldEmpty(d4_locality)) {
                displayResponseMessage("Enter a valid Director 4 Locality")
            } else if (isFieldEmpty(d4_state)) {
                displayResponseMessage("Enter a valid Director 4 State")
            } else if (isFieldEmpty(d4_city)) {
                displayResponseMessage("Enter a valid Director 4 City")
            } else if (isUriEmpty(d4_profile)) {
                displayResponseMessage("Upload a valid Director 4 Profile Image")
            }

            // Validation for Director 5
            else if (isFieldEmpty(d5_fname)) {
                displayResponseMessage("Enter a valid Director 5 First Name")
            } else if (isFieldEmpty(d5_mname)) {
                displayResponseMessage("Enter a valid Director 5 Middle Name")
            } else if (isFieldEmpty(d5_lname)) {
                displayResponseMessage("Enter a valid Director 5 Last Name")
            } else if (isFieldEmpty(d5_gender)) {
                displayResponseMessage("Enter a valid Director 5 Gender")
            } else if (isFieldEmpty(d5_dob)) {
                displayResponseMessage("Enter a valid Director 5 Date of Birth")
            } else if (isFieldEmpty(d5_contact_no)) {
                displayResponseMessage("Enter a valid Director 5 Contact Number")
            } else if (isFieldEmpty(d5_email)) {
                displayResponseMessage("Enter a valid Director 5 Email")
            } else if (isFieldEmpty(d5_aadhar_no)) {
                displayResponseMessage("Enter a valid Director 5 Aadhar Number")
            } else if (isFieldEmpty(d5_address)) {
                displayResponseMessage("Enter a valid Director 5 Address")
            } else if (isFieldEmpty(d5_building_no)) {
                displayResponseMessage("Enter a valid Director 5 Building Number")
            } else if (isFieldEmpty(d5_postoffice)) {
                displayResponseMessage("Enter a valid Director 5 Post Office")
            } else if (isFieldEmpty(d5_locality)) {
                displayResponseMessage("Enter a valid Director 5 Locality")
            } else if (isFieldEmpty(d5_state)) {
                displayResponseMessage("Enter a valid Director 5 State")
            } else if (isFieldEmpty(d5_city)) {
                displayResponseMessage("Enter a valid Director 5 City")
            } else if (isUriEmpty(d5_profile)) {
                displayResponseMessage("Upload a valid Director 5 Profile Image")
            } else if (isFieldEmpty(d6_fname)) {
                displayResponseMessage("Enter a valid Director 6 First Name")
            } else if (isFieldEmpty(d6_mname)) {
                displayResponseMessage("Enter a valid Director 6 Middle Name")
            } else if (isFieldEmpty(d6_lname)) {
                displayResponseMessage("Enter a valid Director 6 Last Name")
            } else if (isFieldEmpty(d6_gender)) {
                displayResponseMessage("Enter a valid Director 6 Gender")
            } else if (isFieldEmpty(d6_dob)) {
                displayResponseMessage("Enter a valid Director 6 Date of Birth")
            } else if (isFieldEmpty(d6_contact_no)) {
                displayResponseMessage("Enter a valid Director 6 Contact Number")
            } else if (isFieldEmpty(d6_email)) {
                displayResponseMessage("Enter a valid Director 6 Email")
            } else if (isFieldEmpty(d6_aadhar_no)) {
                displayResponseMessage("Enter a valid Director 6 Aadhar Number")
            } else if (isFieldEmpty(d6_address)) {
                displayResponseMessage("Enter a valid Director 6 Address")
            } else if (isFieldEmpty(d6_building_no)) {
                displayResponseMessage("Enter a valid Director 6 Building Number")
            } else if (isFieldEmpty(d6_postoffice)) {
                displayResponseMessage("Enter a valid Director 6 Post Office")
            } else if (isFieldEmpty(d6_locality)) {
                displayResponseMessage("Enter a valid Director 6 Locality")
            } else if (isFieldEmpty(d6_state)) {
                displayResponseMessage("Enter a valid Director 6 State")
            } else if (isFieldEmpty(d6_city)) {
                displayResponseMessage("Enter a valid Director 6 City")
            } else if (isUriEmpty(d6_profile)) {
                displayResponseMessage("Upload a valid Director 6 Profile Image")
            } else {
                displayDialogLoading("Please wait..")
                fintechAPI.submitFormGstRegistration(
                    c_name = c_name.toRequestBody(),
                    c_num = c_num.toRequestBody(),
                    cmnpy_name = cmnpy_name.toRequestBody(),
                    cus_email = cus_email.toRequestBody(),
                    x_state = x_state.toRequestBody(),
                    cus_address = cus_address.toRequestBody(),
                    pan_name = pan_name.toRequestBody(),
                    pan_no = pan_no.toRequestBody(),
                    contact_p = contact_p.toRequestBody(),
                    mobile = mobile.toRequestBody(),
                    email = email.toRequestBody(),
                    bussiness_type = bussiness_type.toRequestBody(),
                    consitituion_of_buss = consitituion_of_buss.toRequestBody(),
                    option_of_composition = option_of_composition.toRequestBody(),
                    commenceement_b = commenceement_b.toRequestBody(),

                    // Director 1
                    d_fname = d_fname.toRequestBody(),
                    d_mname = d_mname.toRequestBody(),
                    d_lname = d_lname.toRequestBody(),
                    d_gender = d_gender.toRequestBody(),
                    d_dob = d_dob.toRequestBody(),
                    d_contact_no = d_contact_no.toRequestBody(),
                    d_email = d_email.toRequestBody(),
                    d_aadhar_no = d_aadhar_no.toRequestBody(),
                    d_address = d_address.toRequestBody(),
                    d_building_no = d_building_no.toRequestBody(),
                    d_postoffice = d_postoffice.toRequestBody(),
                    d_locality = d_locality.toRequestBody(),
                    d_state = d_state.toRequestBody(),
                    d_city = d_city.toRequestBody(),
                    d_profile =  getParted(context, d_profile, "d_profile"),

                    // Director 2
                    d2_fname = d2_fname.toRequestBody(),
                    d2_mname = d2_mname.toRequestBody(),
                    d2_lname = d2_lname.toRequestBody(),
                    d2_gender = d2_gender.toRequestBody(),
                    d2_dob = d2_dob.toRequestBody(),
                    d2_contact_no = d2_contact_no.toRequestBody(),
                    d2_email = d2_email.toRequestBody(),
                    d2_aadhar_no = d2_aadhar_no.toRequestBody(),
                    d2_address = d2_address.toRequestBody(),
                    d2_building_no = d2_building_no.toRequestBody(),
                    d2_postoffice = d2_postoffice.toRequestBody(),
                    d2_locality = d2_locality.toRequestBody(),
                    d2_state = d2_state.toRequestBody(),
                    d2_city = d2_city.toRequestBody(),
                    d2_profile = getParted(context, d2_profile, "d2_profile"),


                    // Director 3
                    d3_fname = d3_fname.toRequestBody(),
                    d3_mname = d3_mname.toRequestBody(),
                    d3_lname = d3_lname.toRequestBody(),
                    d3_gender = d3_gender.toRequestBody(),
                    d3_dob = d3_dob.toRequestBody(),
                    d3_contact_no = d3_contact_no.toRequestBody(),
                    d3_email = d3_email.toRequestBody(),
                    d3_aadhar_no = d3_aadhar_no.toRequestBody(),
                    d3_address = d3_address.toRequestBody(),
                    d3_building_no = d3_building_no.toRequestBody(),
                    d3_postoffice = d3_postoffice.toRequestBody(),
                    d3_locality = d3_locality.toRequestBody(),
                    d3_state = d3_state.toRequestBody(),
                    d3_city = d3_city.toRequestBody(),
                    d3_profile = getParted(context, d3_profile, "d3_profile"),



                    // Director 4
                    d4_fname = d4_fname.toRequestBody(),
                    d4_mname = d4_mname.toRequestBody(),
                    d4_lname = d4_lname.toRequestBody(),
                    d4_gender = d4_gender.toRequestBody(),
                    d4_dob = d4_dob.toRequestBody(),
                    d4_contact_no = d4_contact_no.toRequestBody(),
                    d4_email = d4_email.toRequestBody(),
                    d4_aadhar_no = d4_aadhar_no.toRequestBody(),
                    d4_address = d4_address.toRequestBody(),
                    d4_building_no = d4_building_no.toRequestBody(),
                    d4_postoffice = d4_postoffice.toRequestBody(),
                    d4_locality = d4_locality.toRequestBody(),
                    d4_state = d4_state.toRequestBody(),
                    d4_city = d4_city.toRequestBody(),
                    d4_profile = getParted(context, d4_profile, "d4_profile"),

                    // Director 5
                    d5_fname = d5_fname.toRequestBody(),
                    d5_mname = d5_mname.toRequestBody(),
                    d5_lname = d5_lname.toRequestBody(),
                    d5_gender = d5_gender.toRequestBody(),
                    d5_dob = d5_dob.toRequestBody(),
                    d5_contact_no = d5_contact_no.toRequestBody(),
                    d5_email = d5_email.toRequestBody(),
                    d5_aadhar_no = d5_aadhar_no.toRequestBody(),
                    d5_address = d5_address.toRequestBody(),
                    d5_building_no = d5_building_no.toRequestBody(),
                    d5_postoffice = d5_postoffice.toRequestBody(),
                    d5_locality = d5_locality.toRequestBody(),
                    d5_state = d5_state.toRequestBody(),
                    d5_city = d5_city.toRequestBody(),
                    d5_profile = getParted(context, d5_profile, "d5_profile"),

                    // Director 6
                    d6_fname = d6_fname.toRequestBody(),
                    d6_mname = d6_mname.toRequestBody(),
                    d6_lname = d6_lname.toRequestBody(),
                    d6_gender = d6_gender.toRequestBody(),
                    d6_dob = d6_dob.toRequestBody(),
                    d6_contact_no = d6_contact_no.toRequestBody(),
                    d6_email = d6_email.toRequestBody(),
                    d6_aadhar_no = d6_aadhar_no.toRequestBody(),
                    d6_address = d6_address.toRequestBody(),
                    d6_building_no = d6_building_no.toRequestBody(),
                    d6_postoffice = d6_postoffice.toRequestBody(),
                    d6_locality = d6_locality.toRequestBody(),
                    d6_state = d6_state.toRequestBody(),
                    d6_city = d6_city.toRequestBody(),
                    d6_profile = getParted(context, d6_profile, "d6_profile"),

                    ppb_address = ppb_address.toRequestBody(),
                    ppb_building = ppb_building.toRequestBody(),
                    ppb_postoffice = ppb_postoffice.toRequestBody(),
                    ppb_locality = ppb_locality.toRequestBody(),
                    ppb_state = ppb_state.toRequestBody(),
                    bank_account = bank_account.toRequestBody(),
                    ifsc_code = ifsc_code.toRequestBody(),
                    acc_type = acc_type.toRequestBody(),
                    product1 = product1.toRequestBody(),
                    product2 = product2.toRequestBody(),
                    product3 = product3.toRequestBody(),
                    product4 = product4.toRequestBody(),
                    product5 = product5.toRequestBody(),
                    prossession = prossession.toRequestBody(),
                    buss_activity = buss_activity.toRequestBody(),
                    authorised_sign = authorised_sign.toRequestBody(),
                    place = place.toRequestBody(),
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

    // Helper function to check if a field is empty
    private fun isFieldEmpty(value: String): Boolean {
        return value.trim().isEmpty()
    }

    private fun isUriEmpty(value: Uri?): Boolean {
        return value == null
    }

    private fun String.toRequestBody(): RequestBody {
        return toRequestBody("text/plain".toMediaTypeOrNull())
    }



}


fun String.toRequestBody(): RequestBody {
    return toRequestBody("text/plain".toMediaTypeOrNull())
}


fun getParted(context: Context, uri: Uri?, postName: String): MultipartBody.Part? {
    try {
        val dest: String = PathsInformation.getPathFromURI(context, uri)
        val imageFile1 = File(dest)
        return createMultipartPart(postName, imageFile1)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun createMultipartPart(postName: String, file: File): MultipartBody.Part {
    val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(postName, file.name, requestFile)
}

var stateList = getIndianStatesAndUnionTerritories()

fun getIndianStatesAndUnionTerritories(): List<String> {
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