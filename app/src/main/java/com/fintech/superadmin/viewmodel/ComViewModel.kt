package com.fintech.superadmin.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.fintech.superadmin.adapters.CommissionAdapter
import com.fintech.superadmin.data.network.APIServices
import com.fintech.superadmin.databinding.ActivityMyComissionBinding
import com.fintech.superadmin.util.Accessable
import com.fintech.superadmin.util.DisplayMessageUtil
import com.fintech.superadmin.util.NetworkUtil
import com.fintech.superadmin.util.ViewUtils
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