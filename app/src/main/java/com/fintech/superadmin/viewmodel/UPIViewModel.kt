package com.fintech.superadmin.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fintech.superadmin.data.network.APIServices
import com.fintech.superadmin.data.qrPayement.UPICredential
import com.fintech.superadmin.util.Accessable
import com.fintech.superadmin.util.DisplayMessageUtil
import com.fintech.superadmin.util.NetworkUtil
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