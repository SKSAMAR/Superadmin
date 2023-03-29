package com.fintech.prepe.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fintech.prepe.data.network.APIServices
import com.fintech.prepe.data.qrPayement.UPICredential
import com.fintech.prepe.util.Accessable
import com.fintech.prepe.util.DisplayMessageUtil
import com.fintech.prepe.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UPIViewModel
@Inject constructor( private val apiServices: APIServices): ViewModel() {

    var upiCredential: UPICredential? = null
    val amount = mutableStateOf("")
    val message = mutableStateOf("")

    fun makeUpiPayment(context: Context){
        if(Accessable.isAccessable()){
            upiCredential?.upi?.let {
                if (amount.value.isNotEmpty()){
                    NetworkUtil.getNetworkResult(apiServices.makeUpiTransaction(upiCredential!!.upi, upiCredential!!.name?:"", amount.value), context){
                        if(it.response_code ==1){
                            amount.value = ""
                            DisplayMessageUtil.success(context, it.message)
                        }
                        else{
                            DisplayMessageUtil.error(context, it.message)
                        }
                    }
                }
            }
        }
    }
}