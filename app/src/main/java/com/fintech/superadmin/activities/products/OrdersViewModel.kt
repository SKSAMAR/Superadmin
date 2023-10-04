package com.fintech.superadmin.activities.products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.domain.model.ScreenState
import com.fintech.superadmin.data.db.dao.UserDao
import com.fintech.superadmin.data.db.entities.User
import com.fintech.superadmin.data.dto.OrderDto
import com.fintech.superadmin.data.network.APIServices
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel
@Inject constructor(
    private val api: APIServices,
    val dao: UserDao
) : BaseViewModel<List<OrderDto>>() {

    var user: User? = dao.regularUser
    var search by mutableStateOf("")

    fun getOrdersLists() {
        _state.value = ScreenState(isLoading = true)
        api.getOrdersList(search)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.response_code == 1) {
                    _state.value = ScreenState(receivedResponse = it.receivableData)
                } else {
                    _state.value = ScreenState(error = it.message ?: "Failure")
                }
            }, {
                _state.value = ScreenState(error = it.message ?: "Failure")
            })
    }

}