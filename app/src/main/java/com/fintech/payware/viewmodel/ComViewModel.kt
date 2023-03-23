package com.fintech.payware.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.fintech.payware.adapters.CommissionAdapter
import com.fintech.payware.data.network.APIServices
import com.fintech.payware.databinding.ActivityMyComissionBinding
import com.fintech.payware.util.Accessable
import com.fintech.payware.util.DisplayMessageUtil
import com.fintech.payware.util.NetworkUtil
import com.fintech.payware.util.ViewUtils
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