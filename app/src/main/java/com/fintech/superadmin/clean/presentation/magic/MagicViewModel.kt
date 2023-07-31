package com.fintech.superadmin.clean.presentation.magic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.util.Accessable
import com.fintech.superadmin.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MagicViewModel @Inject constructor(private val api: FintechAPI) : BaseViewModel<String>() {

    var amount by mutableStateOf("")

    fun withdraw() {
        if (Accessable.isAccessable()) {
            if (amount.trim().isNotEmpty() && amount.isDigitsOnly()) {
                if (amount.trim().toInt() > 0) {
                    displayDialogSuccess("Please wait")
                    NetworkUtil.getNetworkResult(
                        api.magicWalletWithdraw(amount.trim()),
                        null
                    ) { result ->
                        dismissDialog()
                        if (result.status) {
                            amount = ""
                            displayDialogSuccess(result.message)
                        } else {
                            displayResponseMessage(result.message)
                        }
                    }
                } else {
                    displayResponseMessage("Enter a valid amount")
                }
            } else {
                displayResponseMessage("Enter a valid amount")
            }
        }
    }

}