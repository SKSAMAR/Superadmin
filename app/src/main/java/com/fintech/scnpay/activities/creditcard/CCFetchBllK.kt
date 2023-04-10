package com.fintech.scnpay.activities.creditcard

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.fintech.scnpay.R
import com.fintech.scnpay.activities.common.BaseActivity
import com.fintech.scnpay.databinding.ActivityCcfetchBillBinding
import com.fintech.scnpay.listeners.ResetListener
import com.fintech.scnpay.listeners.VisibilityListener
import com.fintech.scnpay.util.Accessable
import com.fintech.scnpay.util.ViewUtils
import com.fintech.scnpay.viewmodel.MobileRechargeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CCFetchBillK : BaseActivity(), ResetListener, VisibilityListener {
    val viewModel by viewModels<MobileRechargeViewModel>()
    lateinit var  binding: ActivityCcfetchBillBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCcfetchBillBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val actionBar = supportActionBar
        actionBar?.title = "Credit Card"
        binding.ccViewModel = viewModel
        viewModel.resetListener = this
        viewModel.visibilityListener = this
    }

    override fun resetRequiredData(result: Boolean) {
        binding.ccAmount.setText("")
        binding.ccMobile.setText("")
        binding.ccName.setText("")
        binding.ccNumber.setText("")
        binding.otp.setText("")
        binding.otpLayout.visibility = View.GONE
        binding.sendOtp.visibility = View.VISIBLE
        binding.payMoney.visibility = View.GONE
        binding.ccAmount.isCursorVisible = true
        binding.ccAmount.isFocusableInTouchMode = true
        binding.ccAmount.isFocusable = true
        binding.ccAmount.isClickable = true
        binding.ccNumber.isCursorVisible = true
        binding.ccNumber.isFocusableInTouchMode = true
        binding.ccNumber.isFocusable = true
        binding.ccNumber.isClickable = true
        binding.ccMobile.isCursorVisible = true
        binding.ccMobile.isFocusableInTouchMode = true
        binding.ccMobile.isFocusable = true
        binding.ccMobile.isClickable = true
        binding.cardType.isCursorVisible = true
        binding.cardType.isFocusableInTouchMode = true
        binding.cardType.isFocusable = true
        binding.cardType.setClickable(true)
        binding.ccName.isCursorVisible = true
        binding.ccName.isFocusableInTouchMode = true
        binding.ccName.isFocusable = true
        binding.ccName.isClickable = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) { // todo: goto back activity from here
            onBackPressed()
            finish()
            return true
        }

        if (item.itemId == R.id.refundButtonCC) { // todo: goto back activity from here
            doSetupRefund()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.refund_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun startVisibility() {
        binding.otpLayout.visibility = View.VISIBLE
        binding.sendOtp.visibility = View.GONE
        binding.payMoney.visibility = View.VISIBLE
        binding.ccAmount.isCursorVisible = false
        binding.ccAmount.isFocusableInTouchMode = false
        binding.ccAmount.isFocusable = false
        binding.ccAmount.isClickable = false
        binding.ccNumber.isCursorVisible = false
        binding.ccNumber.isFocusableInTouchMode = false
        binding.ccNumber.isFocusable = false
        binding.ccNumber.isClickable = false
        binding.ccMobile.isCursorVisible = false
        binding.ccMobile.isFocusableInTouchMode = false
        binding.ccMobile.isFocusable = false
        binding.ccMobile.isClickable = false
        binding.cardType.isCursorVisible = false
        binding.cardType.isFocusableInTouchMode = false
        binding.cardType.isFocusable = false
        binding.cardType.isClickable = false
        binding.ccName.isCursorVisible = false
        binding.ccName.isFocusableInTouchMode = false
        binding.ccName.isFocusable = false
        binding.ccName.isClickable = false
        ViewUtils.setFocusable(binding.otp, this@CCFetchBillK)
    }

    private fun doSetupRefund() {
        if(Accessable.isAccessable()){
            startActivity(Intent(this@CCFetchBillK, CCRefundActivity::class.java))
        }
    }
}