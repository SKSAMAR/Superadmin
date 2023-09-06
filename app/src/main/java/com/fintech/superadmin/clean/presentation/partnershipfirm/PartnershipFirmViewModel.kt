package com.fintech.superadmin.clean.presentation.partnershipfirm

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
class PartnershipFirmViewModel
@Inject constructor(private val fintechAPI: FintechAPI) : BaseViewModel<Any>() {

    var c_name by mutableStateOf("")
    var c_num by mutableStateOf("")
    var cmnpy_name by mutableStateOf("")
    var cus_email by mutableStateOf("")
    var x_state by mutableStateOf("")
    var cus_address by mutableStateOf("")
    var name_firm by mutableStateOf("")
    var register_address by mutableStateOf("")
    var date_registration by mutableStateOf("")
    var ratio by mutableStateOf("")
    var object_firm by mutableStateOf("")

    // Partner 1
    var p_fname by mutableStateOf("")
    var p_fathername by mutableStateOf("")
    var p_dob by mutableStateOf("")
    var p_residence_address by mutableStateOf("")

    // Passport photo 1 upload
    var p_photo by mutableStateOf<Uri?>(null)

    // Aadhar_pic photo 1 upload
    var p_aadhar_pic by mutableStateOf<Uri?>(null)

    // Sign photo 1 upload
    var p_sign by mutableStateOf<Uri?>(null)

    // Partner 2
    var p2_fname by mutableStateOf("")
    var p2_fathername by mutableStateOf("")
    var p2_dob by mutableStateOf("")
    var p2_residence_address by mutableStateOf("")

    // Passport photo 2 upload
    var p_photo2 by mutableStateOf<Uri?>(null)

    // Aadhar_pic photo 2 upload
    var p_aadhar_pic2 by mutableStateOf<Uri?>(null)

    // Sign photo 2 upload
    var p2_sign by mutableStateOf<Uri?>(null)

    // Partner 3
    var p3_fname by mutableStateOf("")
    var p3_fathername by mutableStateOf("")
    var p3_dob by mutableStateOf("")
    var p3_residence_address by mutableStateOf("")

    // Passport photo 3 upload
    var p_photo3 by mutableStateOf<Uri?>(null)

    // Aadhar_pic photo 3 upload
    var p_aadhar_pic3 by mutableStateOf<Uri?>(null)

    // Sign photo 3 upload
    var p3_sign by mutableStateOf<Uri?>(null)

    // Partner 4
    var p4_fname by mutableStateOf("")
    var p4_fathername by mutableStateOf("")
    var p4_dob by mutableStateOf("")
    var p4_residence_address by mutableStateOf("")

    // Passport photo 4 upload
    var p_photo4 by mutableStateOf<Uri?>(null)

    // Aadhar_pic photo 4 upload
    var p_aadhar_pic4 by mutableStateOf<Uri?>(null)

    // Sign photo 4 upload
    var p4_sign by mutableStateOf<Uri?>(null)

    // Partner 5
    var p5_fname by mutableStateOf("")
    var p5_fathername by mutableStateOf("")
    var p5_dob by mutableStateOf("")
    var p5_residence_address by mutableStateOf("")

    // Passport photo 5 upload
    var p_photo5 by mutableStateOf<Uri?>(null)

    // Aadhar_pic photo 5 upload
    var p_aadhar_pic5 by mutableStateOf<Uri?>(null)

    // Sign photo 5 upload
    var p5_sign by mutableStateOf<Uri?>(null)

    // Partner 6
    var p6_fname by mutableStateOf("")
    var p6_fathername by mutableStateOf("")
    var p6_dob by mutableStateOf("")
    var p6_residence_address by mutableStateOf("")

    // Passport photo 6 upload
    var p_photo6 by mutableStateOf<Uri?>(null)

    // Aadhar_pic photo 6 upload
    var p_aadhar_pic6 by mutableStateOf<Uri?>(null)

    // Sign photo 6 upload
    var p6_sign by mutableStateOf<Uri?>(null)

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
            } else if (name_firm.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Name Firm")
            } else if (register_address.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Register Address")
            } else if (date_registration.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Date Registration")
            } else if (ratio.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Ratio")
            } else if (object_firm.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Object Firm")
            } else if (p_fname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 1 First Name")
            } else if (p_fathername.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 1 Father's Name")
            } else if (p_dob.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 1 Date of Birth")
            } else if (p_residence_address.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 1 Residence Address")
            } else if (p_photo == null) {
                displayResponseMessage("Select a valid Partner 1 Passport Photo")
            } else if (p_aadhar_pic == null) {
                displayResponseMessage("Select a valid Partner 1 Aadhaar Photo")
            } else if (p_sign == null) {
                displayResponseMessage("Select a valid Partner 1 Signature Photo")
            } else if (p2_fname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 2 First Name")
            } else if (p2_fathername.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 2 Father's Name")
            } else if (p2_dob.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 2 Date of Birth")
            } else if (p2_residence_address.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 2 Residence Address")
            } else if (p_photo2 == null) {
                displayResponseMessage("Select a valid Partner 2 Passport Photo")
            } else if (p_aadhar_pic2 == null) {
                displayResponseMessage("Select a valid Partner 2 Aadhaar Photo")
            } else if (p2_sign == null) {
                displayResponseMessage("Select a valid Partner 2 Signature Photo")
            } else if (p3_fname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 3 First Name")
            } else if (p3_fathername.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 3 Father's Name")
            } else if (p3_dob.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 3 Date of Birth")
            } else if (p3_residence_address.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 3 Residence Address")
            } else if (p_photo3 == null) {
                displayResponseMessage("Select a valid Partner 3 Passport Photo")
            } else if (p_aadhar_pic3 == null) {
                displayResponseMessage("Select a valid Partner 3 Aadhaar Photo")
            } else if (p3_sign == null) {
                displayResponseMessage("Select a valid Partner 3 Signature Photo")
            } else if (p4_fname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 4 First Name")
            } else if (p4_fathername.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 4 Father's Name")
            } else if (p4_dob.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 4 Date of Birth")
            } else if (p4_residence_address.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 4 Residence Address")
            } else if (p_photo4 == null) {
                displayResponseMessage("Select a valid Partner 4 Passport Photo")
            } else if (p_aadhar_pic4 == null) {
                displayResponseMessage("Select a valid Partner 4 Aadhaar Photo")
            } else if (p4_sign == null) {
                displayResponseMessage("Select a valid Partner 4 Signature Photo")
            } else if (p5_fname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 5 First Name")
            } else if (p5_fathername.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 5 Father's Name")
            } else if (p5_dob.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 5 Date of Birth")
            } else if (p5_residence_address.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 5 Residence Address")
            } else if (p_photo5 == null) {
                displayResponseMessage("Select a valid Partner 5 Passport Photo")
            } else if (p_aadhar_pic5 == null) {
                displayResponseMessage("Select a valid Partner 5 Aadhaar Photo")
            } else if (p5_sign == null) {
                displayResponseMessage("Select a valid Partner 5 Signature Photo")
            } else if (p6_fname.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 6 First Name")
            } else if (p6_fathername.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 6 Father's Name")
            } else if (p6_dob.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 6 Date of Birth")
            } else if (p6_residence_address.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Partner 6 Residence Address")
            } else if (p_photo6 == null) {
                displayResponseMessage("Select a valid Partner 6 Passport Photo")
            } else if (p_aadhar_pic6 == null) {
                displayResponseMessage("Select a valid Partner 6 Aadhaar Photo")
            } else if (p6_sign == null) {
                displayResponseMessage("Select a valid Partner 6 Signature Photo")
            } else {
                displayDialogLoading("Please wait")
                fintechAPI.uploadFormPartnershipFirm(
                    c_name = c_name.toRequestBody(),
                    c_num = c_num.toRequestBody(),
                    cmnpy_name = cmnpy_name.toRequestBody(),
                    cus_email = cus_email.toRequestBody(),
                    x_state = x_state.toRequestBody(),
                    cus_address = cus_address.toRequestBody(),
                    name_firm = name_firm.toRequestBody(),
                    register_address = register_address.toRequestBody(),
                    date_registration = date_registration.toRequestBody(),
                    ratio = ratio.toRequestBody(),
                    object_firm = object_firm.toRequestBody(),

                    // Partner 1
                    p_fname = p_fname.toRequestBody(),
                    p_fathername = p_fathername.toRequestBody(),
                    p_dob = p_dob.toRequestBody(),
                    p_residence_address = p_residence_address.toRequestBody(),
                    p_photo = getParted(context, p_photo, "p_photo"),
                    p_aadhar_pic = getParted(context, p_aadhar_pic, "p_aadhar_pic"),
                    p_sign = getParted(context, p_sign, "p_sign"),

                    // Partner 2
                    p2_fname = p2_fname.toRequestBody(),
                    p2_fathername = p2_fathername.toRequestBody(),
                    p2_dob = p2_dob.toRequestBody(),
                    p2_residence_address = p2_residence_address.toRequestBody(),
                    p_photo2 = getParted(context, p_photo2, "p_photo2"),
                    p_aadhar_pic2 = getParted(context, p_aadhar_pic2, "p_aadhar_pic2"),
                    p2_sign = getParted(context, p2_sign, "p2_sign"),

                    // Partner 3
                    p3_fname = p3_fname.toRequestBody(),
                    p3_fathername = p3_fathername.toRequestBody(),
                    p3_dob = p3_dob.toRequestBody(),
                    p3_residence_address = p3_residence_address.toRequestBody(),
                    p_photo3 = getParted(context, p_photo3, "p_photo3"),
                    p_aadhar_pic3 = getParted(context, p_aadhar_pic3, "p_aadhar_pic3"),
                    p3_sign = getParted(context, p3_sign, "p3_sign"),


                    // Partner 4
                    p4_fname = p4_fname.toRequestBody(),
                    p4_fathername = p4_fathername.toRequestBody(),
                    p4_dob = p4_dob.toRequestBody(),
                    p4_residence_address = p4_residence_address.toRequestBody(),
                    p_photo4 = getParted(context, p_photo4, "p_photo4"),
                    p_aadhar_pic4 = getParted(context, p_aadhar_pic4, "p_aadhar_pic4"),
                    p4_sign = getParted(context, p4_sign, "p4_sign"),


                    // Partner 5
                    p5_fname = p5_fname.toRequestBody(),
                    p5_fathername = p5_fathername.toRequestBody(),
                    p5_dob = p5_dob.toRequestBody(),
                    p5_residence_address = p5_residence_address.toRequestBody(),
                    p_photo5 = getParted(context, p_photo5, "p_photo5"),
                    p_aadhar_pic5 = getParted(context, p_aadhar_pic5, "p_aadhar_pic5"),
                    p5_sign = getParted(context, p5_sign, "p5_sign"),


                    // Partner 5
                    p6_fname = p6_fname.toRequestBody(),
                    p6_fathername = p6_fathername.toRequestBody(),
                    p6_dob = p6_dob.toRequestBody(),
                    p6_residence_address = p6_residence_address.toRequestBody(),
                    p_photo6 = getParted(context, p_photo6, "p_photo6"),
                    p_aadhar_pic6 = getParted(context, p_aadhar_pic6, "p_aadhar_pic6"),
                    p6_sign = getParted(context, p6_sign, "p6_sign"),

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