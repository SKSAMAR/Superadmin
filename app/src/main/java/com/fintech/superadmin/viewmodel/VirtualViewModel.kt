package com.fintech.superadmin.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.view.WindowManager
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.dto.virtual.VirtualAccountBank
import com.fintech.superadmin.clean.domain.model.ScreenState
import com.fintech.superadmin.data.db.dao.UserDao
import com.fintech.superadmin.data.db.entities.User
import com.fintech.superadmin.data.network.APIServices
import com.fintech.superadmin.util.NetworkUtil
import com.google.zxing.WriterException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VirtualViewModel
@Inject constructor(
    private val apiServices: APIServices,
    userDao: UserDao
) : BaseViewModel<VirtualAccountBank?>() {

    var user: User? = null
    var bitmap by mutableStateOf<Bitmap?>(null)

    init {
        user = userDao.regularUser
    }


    fun getVirtualAccount(context: Context) {
        NetworkUtil.getNetworkResult(
            apiServices.startRazorQRGeneration("virtual_account_bank"),
            context
        ) {
            _state.value = ScreenState(receivedResponse = it.virtualAccountBank)
        }
    }

    fun getQrCode(windowManager: WindowManager, string: String): Bitmap? {
        val display = windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val width = point.x
        val height = point.y
        var dimen = if (width < height) width else height
        dimen = dimen * 3 / 4
        val qrgEncoder = QRGEncoder(string, null, QRGContents.Type.TEXT, dimen)
        try {
            return qrgEncoder.encodeAsBitmap()
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return null
    }

}