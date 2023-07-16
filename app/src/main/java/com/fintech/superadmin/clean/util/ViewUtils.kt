package com.fintech.superadmin.clean.util

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Telephony
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.fintech.superadmin.R
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.clean.data.remote.dto.reward.ScratchCardData
import com.fintech.superadmin.data.network.responses.RegularResponse
import com.fintech.superadmin.databinding.ScratchDialogBinding
import com.fintech.superadmin.deer_listener.Receiver
import dev.skymansandy.scratchcardlayout.listener.ScratchListener
import dev.skymansandy.scratchcardlayout.ui.ScratchCardLayout
import java.net.URLEncoder

object ViewUtils {


    fun Context.shareWithAll(shortLink: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString())
        intent.type = "text/plain"
        startActivity(intent)
    }

    fun Context.shareWithMessage(shortLink: String, address: String? = null) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString())
        address?.let {

            intent.putExtra("address", it);
        }
        intent.type = "text/plain"
        intent.setPackage(Telephony.Sms.getDefaultSmsPackage(applicationContext))
        startActivity(intent)
    }

    @SuppressLint("IntentReset")
    fun Context.sendSms(to: String, message: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this)
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$to"))
            intent.putExtra("sms_body", message)
            if (defaultSmsPackageName != null) {
                intent.setPackage(defaultSmsPackageName)
            }
            startActivity(intent)
        } else {
            val smsUri = Uri.parse("tel:$to")
            val intent = Intent(Intent.ACTION_VIEW, smsUri)
            intent.putExtra("address", to)
            intent.putExtra("sms_body", message)
            intent.type = "vnd.android-dir/mms-sms"
            startActivity(intent)
        }
    }

    fun Context.shareWithWhatsApp(to: String, message: String) {

        val packageManager: PackageManager = packageManager
        val i = Intent(Intent.ACTION_VIEW)
        try {
            val url =
                "https://api.whatsapp.com/send?phone=" + URLEncoder.encode(
                    to,
                    "UTF-8"
                ) + "&text=" + URLEncoder.encode(
                    message,
                    "UTF-8"
                )
            i.setPackage("com.whatsapp")
            i.data = Uri.parse(url)
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            val viewIntent = Intent(
                "android.intent.action.VIEW",
                Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp&hl=en_IN&gl=US")
            )
            startActivity(viewIntent)
        }
    }


    fun Context.shareWithPackage(shortLink: String, packageName: String) {
        try {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString())
            intent.type = "text/plain"
            intent.setPackage(packageName)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            val viewIntent = Intent(
                "android.intent.action.VIEW",
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName&hl=en_IN&gl=US")
            )
            startActivity(viewIntent)
        }
    }

    fun Context.copyReferralLink(referral: String) {
        val clipboard = getSystemService(ComponentActivity.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied", referral)
        clipboard.setPrimaryClip(clip)
        showToast("Copied")
    }

    fun Context.createLink(custid: String, action: (link: String) -> Unit) {
        action.invoke("https://google.com")
        /**
        val sharelinktext = "https://metavisionpay.page.link/?" +
                "link=http://${packageName}/myrefer.php?custid=" + custid +
                "&apn=" + packageName +
                "&st=" + "My Refer Link" +
                "&sd=" + "Registration" +
                "&si=" + "https://panel.metavisionpay.com/Backend/assets/Reseller_docs/Logo/metaversion.png"
        val shortLinkTask = Firebase.dynamicLinks.shortLinkAsync {
            longLink = Uri.parse(sharelinktext)
        }.addOnSuccessListener { (shortLink, flowChartLink) ->
            action.invoke(shortLink.toString())
        }.addOnFailureListener {
            showToast(it.message)
        }**/
    }

    /**
    fun Context.createLink(custid: String, action: (link: String) -> Unit) {

    val sharelinktext = "https://mttone.page.link/?" +
    "link=http://www.mytycoonrecharge.com/myrefer.php?custid=" + custid +
    "&apn=" + packageName +
    "&st=" + "My Refer Link" +
    "&sd=" + "Registration" +
    "&si=" + "https://www.mytycoonrecharge.com/assets/images/Mytycoonrecharge.png"
    action.invoke(sharelinktext.toString())

    /**
    val shortLinkTask = Firebase.dynamicLinks.shortLinkAsync {
    longLink = Uri.parse(sharelinktext)
    }.addOnSuccessListener { (shortLink, flowChartLink) ->
    action.invoke(shortLink.toString())
    }.addOnFailureListener {
    ViewUtils.showToast(applicationContext, it.message)
    }**/
    }
     **/


    fun Context.showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    fun showCardDialog(
        context: Context,
        scratchCardData: ScratchCardData,
        api: FintechAPI,
        receiver: Receiver<Boolean>
    ) {
        val scratchRevealed = booleanArrayOf(false)
        val alert = AlertDialog.Builder(
            context, R.style.DialogThemeFull
        )
        val binding: ScratchDialogBinding =
            ScratchDialogBinding.inflate(LayoutInflater.from(context))
        alert.setView(binding.root)
        val alertDialog2 = alert.create()

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(alertDialog2.window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        alertDialog2.setCanceledOnTouchOutside(false)
        alertDialog2.window?.attributes = lp
        alertDialog2.show()
        binding.navigationIdea.visibility = View.INVISIBLE
        binding.cancel.setOnClickListener { view -> alertDialog2.dismiss() }
        binding.scratchCard.setScratchListener(object : ScratchListener {
            override fun onScratchStarted() {}

            @SuppressLint("SetTextI18n")
            override fun onScratchProgress(scratchCardLayout: ScratchCardLayout, i: Int) {
                if (i > 30 && !scratchRevealed[0]) {
                    scratchCardLayout.revealScratch()
                    binding.cardData = scratchCardData
                    receiver.getData(true)
                    scratchRevealed[0] = true

                    binding.textView20.text = "You won a gift card for\n${
                        scratchCardData.tRANSACTIONTYPE?.lowercase()
                            ?.replaceFirstChar { it.uppercase() } ?: ""
                    }"
                    binding.textView20.isVisible = true
                    scratchTheCard(api, scratchCardData.iD ?: "dssd")
                }
            }

            override fun onScratchComplete() {}
        })
    }

    fun scratchTheCard(api: FintechAPI, coupon_id: String) {
        api.scratchTheCardData(coupon_id)
            .subscribe(
                { result: RegularResponse? -> }
            ) { error: Throwable? -> }
    }

}