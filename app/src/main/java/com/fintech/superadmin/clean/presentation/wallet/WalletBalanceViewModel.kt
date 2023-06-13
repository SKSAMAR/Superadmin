package com.fintech.superadmin.clean.presentation.wallet

import androidx.lifecycle.LiveData
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.domain.model.ScreenState
import com.fintech.superadmin.data.db.dao.UserDao
import com.fintech.superadmin.data.db.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletBalanceViewModel @Inject constructor(dao: UserDao): BaseViewModel<LiveData<User>>() {
    init {
        _state.value = ScreenState(receivedResponse = dao.user)
    }
}