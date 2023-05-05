package com.fintech.superadmin.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.view.LayoutInflater
import android.view.WindowManager
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AlertDialog
import com.fintech.superadmin.data.db.entities.User
import com.fintech.superadmin.data.network.responses.QRResponse
import com.fintech.superadmin.databinding.DisplayMobilepayQrBinding
import com.fintech.superadmin.databinding.DisplayQrResponseBinding
import com.google.zxing.WriterException
import java.util.*

fun displayQr(qrResponse: QRResponse?, context: Context) {
        val alert = AlertDialog.Builder(context)
        val binding: DisplayQrResponseBinding =
            DisplayQrResponseBinding.inflate(LayoutInflater.from(context))
        alert.setView(binding.root)
        MyAlertUtils.alertDialog = alert.create()
        MyAlertUtils.alertDialog.setCanceledOnTouchOutside(false)
        MyAlertUtils.alertDialog.show()
        binding.cancel.setOnClickListener { DisplayMessageUtil.dismissDialog() }
        qrResponse?.let {
            val cleanImage = qrResponse.qr_image.replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,", "")
            val decodedString: ByteArray = Base64.getDecoder().decode(cleanImage)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            binding.qrImage.setImageBitmap(decodedByte)
            binding.upiId.text = "UPI ID: ${qrResponse.vpa.upi}"
        }
}


@SuppressLint("SetTextI18n")
fun displayMobilePayQr(user: User, context: Context) {
    val alert = AlertDialog.Builder(context)
    val binding: DisplayMobilepayQrBinding = DisplayMobilepayQrBinding.inflate(LayoutInflater.from(context))
    val url = "${context.packageName}?mode=scanPay&mobile=${user.mobile.trim()}&id=${user.id}&name=$${user.name}"
    alert.setView(binding.root)
    MyAlertUtils.alertDialog = alert.create()
    MyAlertUtils.alertDialog.setCanceledOnTouchOutside(false)
    MyAlertUtils.alertDialog.show()
    binding.cancel.setOnClickListener { DisplayMessageUtil.dismissDialog() }
    url.let {
        val manager: WindowManager =  MyAlertUtils.alertDialog?.window?.windowManager as WindowManager
        val display = manager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val width = point.x
        val height = point.y
        var dimen = if (width < height) width else height
        dimen = dimen * 3 / 4
        val bitmap: Bitmap
        val qrgEncoder = QRGEncoder(url, null, QRGContents.Type.TEXT, dimen)
        try {
            bitmap = qrgEncoder.encodeAsBitmap()
            binding.qrImage.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            ViewUtils.showToast(context, e.toString())
        }
    }
}