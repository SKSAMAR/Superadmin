package com.fintech.superadmin.activities.fingBoard

sealed class FingNav(val route: String){
    object PanSelection: FingNav("panSelection")
    object AadhaarSelection: FingNav("aadhaarSelection")
    object OtpSelection: FingNav("otpSelection")
    object CompleteKyc: FingNav("completeKyc")
}
