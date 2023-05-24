package com.fintech.superadmin.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.fintech.superadmin.data.cashfree.CashFreeLatestGateway
import com.fintech.superadmin.data.network.APIServices
import com.fintech.superadmin.util.Accessable
import com.fintech.superadmin.util.DisplayMessageUtil
import com.fintech.superadmin.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CashAddViewModel
@Inject constructor(
    private val apiServices: APIServices
): ViewModel() {

    fun doTheAddWallet(amount: String, context: Context, action: (CashFreeLatestGateway) -> Unit) {
        if (Accessable.isAccessable()) {
            NetworkUtil.getNetworkResult(apiServices.addWallet(amount, "addWallet"), context) {
                if (it.status) {
                    action.invoke(it.receivableData)
                } else {
                    DisplayMessageUtil.error(context, it.message)
                }
            }
        }
    }

}