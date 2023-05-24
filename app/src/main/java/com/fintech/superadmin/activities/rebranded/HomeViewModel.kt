package com.fintech.superadmin.activities.rebranded

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.data.db.dao.UserDao
import com.fintech.superadmin.data.db.entities.User
import com.fintech.superadmin.data.db.entities.UserProfile
import com.fintech.superadmin.data.network.APIServices
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(private val api: APIServices, val dao: UserDao) : BaseViewModel<User>() {

    var user : State<User?> = mutableStateOf(null)
    var userProfile : State<UserProfile?> = mutableStateOf(null)







}