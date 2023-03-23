package com.fintech.webspidysoftware.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.fintech.webspidysoftware.adapters.CommissionAdapter
import com.fintech.webspidysoftware.data.network.APIServices
import com.fintech.webspidysoftware.databinding.ActivityMyComissionBinding
import com.fintech.webspidysoftware.util.Accessable
import com.fintech.webspidysoftware.util.DisplayMessageUtil
import com.fintech.webspidysoftware.util.NetworkUtil
import com.fintech.webspidysoftware.util.ViewUtils
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