package com.fintech.superadmin.clean.presentation.browsePlan

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.clean.data.remote.dto.browsePlan.BrowsePlanDto
import com.fintech.superadmin.clean.data.remote.dto.browsePlan.DynamicBrowsePlan
import com.fintech.superadmin.clean.data.remote.dto.browsePlan.Plan
import com.fintech.superadmin.clean.domain.model.ScreenState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class BrowsePlanViewModel
@Inject constructor(private val api: FintechAPI) : BaseViewModel<ArrayList<DynamicBrowsePlan?>>() {


    var selectedMobile = ""
    var selectedOperatorCode = ""
    var search by mutableStateOf("")
    var tags by mutableStateOf("")

    var cOMBObyTag by mutableStateOf("")
    var fULLTTTag by mutableStateOf("")
    var gTag by mutableStateOf("")
    var g4GTag by mutableStateOf("")
    var rATECUTTERTag by mutableStateOf("")
    var romaingTag by mutableStateOf("")
    var sMSTag by mutableStateOf("")
    var tOPUPTag by mutableStateOf("")


    @SuppressLint("CheckResult")
    fun fetchBrowsePlans() {
        _state.value = ScreenState(isLoading = true)
        api.browsePlanAdmin("browsePlan", selectedMobile, selectedOperatorCode)
            .subscribe({
                if (it.status && it.response_code == 1) {
                    if (it.receivableData == null) {

                        _state.value = ScreenState(error = it.message ?: "No Operator Found")
                    } else {
                        it.receivableData?.let {
                            val dynamicBrowsePlanList = ArrayList<DynamicBrowsePlan?>()
                            val gson = Gson().toJson(it)

                            try {
                                val json = JSONObject(gson)
                                val keys: Iterator<String> = json.keys()
                                while (keys.hasNext()) {
                                    val key = keys.next()
                                    val value: Any? = json.get(key)
                                    val setKey = key.trim().replace("_", " ").uppercase()
                                    println("CHECK == $setKey: $value")
                                    if (value!=null && value.toString().isNotEmpty() && value.toString()!="null"){
                                        var list: ArrayList<Plan?>? = null
                                        try{
                                            val listType = object : TypeToken<List<Plan>>() {}.type
                                            list = Gson().fromJson(value.toString(), listType)

                                        }catch (e: Exception){
                                            println("EXCEPTION == $setKey: value")
                                            e.printStackTrace()
                                        }
                                        val dynamicBrowsePlan = DynamicBrowsePlan(title = setKey, planList = list)
                                        dynamicBrowsePlanList.add(dynamicBrowsePlan)
                                    }
                                }
                            } catch (e: JSONException) {
                                println("EXCEPTION == JSONException Error")
                                e.printStackTrace()
                            }
                            _state.value = ScreenState(receivedResponse = dynamicBrowsePlanList)

                        }
                    }
                } else {
                    _state.value = ScreenState(error = it.message ?: "No Operator Found")
                }
            }, {
                _state.value = ScreenState(error = it.localizedMessage ?: "Some error")
            })

    }


}