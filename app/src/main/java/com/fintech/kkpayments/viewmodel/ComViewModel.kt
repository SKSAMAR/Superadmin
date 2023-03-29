package com.fintech.kkpayments.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.fintech.kkpayments.adapters.CommissionAdapter
import com.fintech.kkpayments.data.network.APIServices
import com.fintech.kkpayments.databinding.ActivityMyComissionBinding
import com.fintech.kkpayments.util.Accessable
import com.fintech.kkpayments.util.DisplayMessageUtil
import com.fintech.kkpayments.util.NetworkUtil
import com.fintech.kkpayments.util.ViewUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ComViewModel
@Inject constructor(private val apiServices: APIServices): ViewModel() {

    lateinit var binding: ActivityMyComissionBinding
    lateinit var adapter: CommissionAdapter


    fun  getComPlans(type: String = "AEPS_COMM", context: Context){
        if(Accessable.isAccessable()){
            adapter.resetAll()
            NetworkUtil.getNetworkResult( apiServices.getComPlans(type), null){
                DisplayMessageUtil.dismissDialog()
                if(it.isEmpty()){
                    ViewUtils.showToast(context, "No Commission is set for you")
                    adapter.addAll(it)
                }
                else{
                    adapter.addAll(it)
                }
            }
        }
    }

}