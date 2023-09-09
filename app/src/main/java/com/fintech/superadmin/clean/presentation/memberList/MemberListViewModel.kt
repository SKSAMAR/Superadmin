package com.fintech.superadmin.clean.presentation.memberList

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.dto.addMember.MemberDataDto
import com.fintech.superadmin.clean.domain.model.ScreenState
import com.fintech.superadmin.clean.presentation.addMember.UserTypeSystem
import com.fintech.superadmin.data.db.entities.User
import com.fintech.superadmin.data.network.APIServices
import com.fintech.superadmin.util.DisplayMessageUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class MemberListViewModel
@Inject constructor(private val apiServices: APIServices) : BaseViewModel<List<MemberDataDto>>() {

    var search by mutableStateOf("")
    var selectedUserType by mutableStateOf<UserTypeSystem?>(null)
    var user: User? = null

    @SuppressLint("CheckResult")
    fun getMembersList() {
        _state.value = ScreenState(isLoading = true)
        apiServices.getMembersList(selectedUserType?.type?:"", search.trim(), "membersList")
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status == true) {
                    _state.value = ScreenState(receivedResponse = it.receivableData)
                } else {
                    _state.value = ScreenState(error = it.message ?: "Some Error")
                }
            }, {
                _state.value = ScreenState(error = it.message ?: "Some Error")
            }
            )
    }

    @SuppressLint("CheckResult")
    fun changeMemberStatus(context: Context, status: String, memberDataDto: MemberDataDto) {
        DisplayMessageUtil.loading(context)
        apiServices.changeMemberStatus(status, memberDataDto.iD ?: "", "memberStatus")
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                DisplayMessageUtil.dismissDialog()
                if (it.status == true) {
                    DisplayMessageUtil.success(context, it.message)
                    getMembersList()
                } else {
                    DisplayMessageUtil.error(context, it.message)
                }
            }, {
                DisplayMessageUtil.error(context, it.message ?: "Some Error")
            })
    }


    fun addableList(): List<UserTypeSystem> {
        try {
            val aList = ArrayList<UserTypeSystem>()
            if (user?.userstatus == "3") {
                aList.add(UserTypeSystem("Distributor", "2"))
                aList.add(UserTypeSystem("Retailer", "1"))
            }
            if (user?.userstatus == "2") {
                aList.add(UserTypeSystem("Retailer", "1"))
            }
            return aList
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }


}