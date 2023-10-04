package com.fintech.superadmin.activities.products

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.domain.model.ScreenState
import com.fintech.superadmin.data.db.dao.UserDao
import com.fintech.superadmin.data.db.entities.User
import com.fintech.superadmin.data.dto.ProductDto
import com.fintech.superadmin.data.network.APIServices
import com.fintech.superadmin.data.repositories.UserRepository
import com.fintech.superadmin.flight.util.Accessable
import com.fintech.superadmin.util.DisplayMessageUtil
import com.fintech.superadmin.util.ViewUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class ProductViewModel
@Inject constructor(
    private val api: APIServices,
    private val userRepository: UserRepository,
    val dao: UserDao
) : BaseViewModel<List<ProductDto>>() {

    var circleList = ArrayList<String>()
    var user: User? = dao.regularUser
    var selectedMember by mutableStateOf<ProductDto?>(null)
    var fullName by mutableStateOf(user?.name ?: "")
    var address by mutableStateOf(user?.address ?: "")
    var landmark by mutableStateOf(user?.dist ?: "")
    var city by mutableStateOf(user?.city ?: "")
    var homeState by mutableStateOf(user?.state ?: "")
    var pincode by mutableStateOf(user?.pin ?: "")
    var tpin by mutableStateOf("")

    init {
        circleInitializer()
    }


    var search by mutableStateOf("")

    fun getProductLists() {
        _state.value = ScreenState(isLoading = true)
        api.getProductsList(search)
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

    fun buyProduct(productDto: ProductDto, context: Context) {
        if (Accessable.isAccessable()) {
            if (fullName.trim().isEmpty()) {
                DisplayMessageUtil.error(context, "Enter a valid name.")
            } else if (address.trim().isEmpty()) {
                DisplayMessageUtil.error(context, "Enter a valid address.")
            } else if (landmark.trim().isEmpty()) {
                DisplayMessageUtil.error(context, "Enter a valid landmark.")
            } else if (city.trim().isEmpty()) {
                DisplayMessageUtil.error(context, "Enter a valid city.")
            } else if (homeState.trim().isEmpty()) {
                DisplayMessageUtil.error(context, "Enter a valid State.")
            } else if (pincode.trim().isEmpty()) {
                DisplayMessageUtil.error(context, "Enter a valid pincode.")
            } else if (tpin.trim().isEmpty()) {
                DisplayMessageUtil.error(context, "Enter a valid M-PIN.")
            } else {
                DisplayMessageUtil.loading(context)
                api.orderProduct(productDto.iD?:"", fullName.trim(), address.trim(), landmark.trim(), city.trim(), homeState.trim(), pincode.trim(), tpin.trim(), 1)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.response_code == 1) {
                            selectedMember = null
                            DisplayMessageUtil.showSuccess(context, it.message ?: "Success") {
                                userRepository.refreshUserData(context)
                            }
                        } else {
                            DisplayMessageUtil.error(context, it.message ?: "Failure")
                        }
                    }, {
                        DisplayMessageUtil.error(context, it.message ?: "Success")
                    })
            }
        }
    }


    private fun circleInitializer() {
        circleList.add("Andhra Pradesh Telangana")
        circleList.add("Assam")
        circleList.add("Bihar Jharkhand")
        circleList.add("Chennai")
        circleList.add("Delhi NCR")
        circleList.add("Gujarat")
        circleList.add("Haryana")
        circleList.add("Himachal Pradesh")
        circleList.add("Jammu Kashmir")
        circleList.add("Karnataka")
        circleList.add("Kerala")
        circleList.add("Kolkata")
        circleList.add("Madhya Pradesh Chhattisgarh")
        circleList.add("Maharashtra Goa")
        circleList.add("Mumbai")
        circleList.add("North East")
        circleList.add("Orissa")
        circleList.add("Punjab")
        circleList.add("Rajasthan")
        circleList.add("Tamil Nadu")
        circleList.add("UP East")
        circleList.add("UP West")
        circleList.add("West Bengal")
    }

    fun changeCircle(context: Context) {
        ViewUtils.onSpinnerViewBring(
            "Select State", context, circleList
        ) { selected: String ->
            homeState = selected
        }
    }

}