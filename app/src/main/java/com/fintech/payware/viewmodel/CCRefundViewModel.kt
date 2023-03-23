package com.fintech.payware.viewmodel

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.fintech.payware.data.dto.CreditCardHistory
import com.fintech.payware.data.network.APIServices
import com.fintech.payware.data.network.responses.RegularResponse
import com.fintech.payware.databinding.OtpScreenLayoutBinding
import com.fintech.payware.util.Accessable
import com.fintech.payware.util.DisplayMessageUtil
import com.fintech.payware.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CCRefundViewModel 
@Inject constructor(private val apiServices: APIServices): ViewModel() {

    var historyList by mutableStateOf<List<CreditCardHistory>?>(null)

    init {
        getHistory(null)
    }


    private fun getHistory(context: Context?){
        NetworkUtil.getNetworkResult(apiServices.ccHistory("historyCC"), context){
            if (it.status){
                historyList = it.receivableData
            }
        }
    }

    fun sendOTPForRefundCreditCard(context: Context, reference: String) {
        if (Accessable.isAccessable()) {
            NetworkUtil.getNetworkResult(
                apiServices.resendOtpForRefund(
                    reference, "resendOTP"
                ), context
            ) { result: RegularResponse ->
                if (result.status) {
                    otpPassingDesign(context, reference)
                } else {
                    DisplayMessageUtil.error(context, result.message)
                }
            }
        }
    }

    private fun otpPassingDesign(context: Context, refrence: String) {
        val dialog = Dialog(context)
        val binding: OtpScreenLayoutBinding =
            OtpScreenLayoutBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.setCanceledOnTouchOutside(false)
        binding.cancelLayout.setOnClickListener { v -> dialog.dismiss() }
        binding.verifyOtp.setOnClickListener { v ->
            val otp: String =
                Objects.requireNonNull(binding.enterOtpDmt.getText()).toString()
            if (otp.isEmpty()) {
                DisplayMessageUtil.error(context, "Provide OTP")
            } else {
                creditCardRefundNow(dialog, context, refrence, otp)
            }
        }
    }

    private fun creditCardRefundNow(
        dialog: Dialog,
        context: Context,
        refrence: String,
        otp: String,
    ) {
        NetworkUtil.getNetworkResult(
            apiServices.ccForRefund(
                otp, refrence, "refundTxn"
            ), context
        ) { result: RegularResponse ->
            if (result.status) {
                dialog.dismiss()
                DisplayMessageUtil.success(context, result.message)
                getHistory(null)
            } else {
                DisplayMessageUtil.error(context, result.message)
            }
        }
    }
    
}
