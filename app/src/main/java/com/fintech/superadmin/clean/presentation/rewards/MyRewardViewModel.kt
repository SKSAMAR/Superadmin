package com.fintech.superadmin.clean.presentation.rewards

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.clean.data.remote.dto.reward.ScratchCardModel
import com.fintech.superadmin.clean.data.remote.dto.reward.toScratchCardData
import com.fintech.superadmin.clean.data.remote.dto.reward.toScratchCardModel
import com.fintech.superadmin.clean.domain.model.ScreenState
import com.fintech.superadmin.clean.util.ViewUtils
import com.fintech.superadmin.util.Accessable
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MyRewardViewModel
@Inject constructor(private val api: FintechAPI) : BaseViewModel<MutableList<ScratchCardModel>>() {

    var isFetchingMore by mutableStateOf(false)
    private var indexing = 0

    init {
        getRewardCards()
    }


    @SuppressLint("CheckResult")
    fun getRewardCards(
        transType: String = "ALL",
        initialLoad: Boolean = true,
        onError: (error: String) -> Unit = {}
    ) {
        if (initialLoad) {
            _state.value = ScreenState(isLoading = true)
        }
        api.getScratchCardData(indexing = indexing.toString(), trans_type = transType)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                val list = result.map { s -> s.toScratchCardModel() }.toMutableList()
                if (initialLoad) {
                    _state.value = ScreenState(receivedResponse = list)
                } else {
                    _state.value.receivedResponse?.let {
                        it.addAll(list)
                    }
                }
                indexing++

            }, {
                if (initialLoad) {
                    _state.value = ScreenState(error = it.localizedMessage ?: "Some error")
                } else {
                    onError(it.localizedMessage ?: "Some error")
                }
            })
    }

    fun scratchTheCard(
        model: ScratchCardModel,
        context: Context
    ){
        if (Accessable.isAccessable()){
            ViewUtils.showCardDialog(context = context, scratchCardData = model.toScratchCardData(), api = api){
                model.sCRATCHSTATUS.value = 1
            }
        }
    }

}