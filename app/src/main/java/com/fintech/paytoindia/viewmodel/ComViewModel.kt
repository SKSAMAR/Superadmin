package com.fintech.paytoindia.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.fintech.paytoindia.adapters.CommissionAdapter
import com.fintech.paytoindia.data.network.APIServices
import com.fintech.paytoindia.databinding.ActivityMyComissionBinding
import com.fintech.paytoindia.util.Accessable
import com.fintech.paytoindia.util.DisplayMessageUtil
import com.fintech.paytoindia.util.NetworkUtil
import com.fintech.paytoindia.util.ViewUtils
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