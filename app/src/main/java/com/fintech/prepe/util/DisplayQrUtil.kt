package com.fintech.prepe.util

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.fintech.prepe.data.network.responses.QRResponse
import com.fintech.prepe.databinding.DisplayQrResponseBinding
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