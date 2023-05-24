package com.fintech.superadmin.activities.addfunds

import android.os.Bundle
import androidx.activity.viewModels
import com.cashfree.pg.api.CFPaymentGatewayService
import com.cashfree.pg.core.api.CFSession
import com.cashfree.pg.core.api.CFTheme
import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback
import com.cashfree.pg.core.api.exception.CFException
import com.cashfree.pg.core.api.utils.AnalyticsUtil
import com.cashfree.pg.core.api.utils.CFErrorResponse
import com.cashfree.pg.ui.api.CFDropCheckoutPayment
import com.cashfree.pg.ui.api.CFPaymentComponent
import com.cashfree.pg.ui.api.upi.intent.CFIntentTheme
import com.cashfree.pg.ui.api.upi.intent.CFUPIIntentCheckout
import com.cashfree.pg.ui.api.upi.intent.CFUPIIntentCheckoutPayment
import com.fintech.superadmin.R
import com.fintech.superadmin.activities.common.BaseActivity
import com.fintech.superadmin.data.cashfree.CashFreeLatestGateway
import com.fintech.superadmin.databinding.ActivityCashAddBinding
import com.fintech.superadmin.util.DisplayMessageUtil
import com.fintech.superadmin.util.MyAlertUtils
import com.fintech.superadmin.viewmodel.CashAddViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CashFreeAddWalletActivity : BaseActivity(), CFCheckoutResponseCallback {

    lateinit var binding: ActivityCashAddBinding
    val viewModel by viewModels<CashAddViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCashAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.let {
            it.title = "Desired Amount"
        }
        setListeners()
        makeThePayment()
        initialize()
    }


    private fun setListeners() {
        binding.one.setOnClickListener { v ->
            val value =
                binding.amountbalance.text.toString() + binding.one.text.toString()
            binding.amountbalance.setText(value)
        }
        binding.two.setOnClickListener { v ->
            val value =
                binding.amountbalance.text.toString() + binding.two.text.toString()
            binding.amountbalance.setText(value)
        }
        binding.three.setOnClickListener { v ->
            val value =
                binding.amountbalance.text.toString() + binding.three.text.toString()
            binding.amountbalance.setText(value)
        }
        binding.four.setOnClickListener { v ->
            val value =
                binding.amountbalance.text.toString() + binding.four.text.toString()
            binding.amountbalance.setText(value)
        }
        binding.five.setOnClickListener { v ->
            val value =
                binding.amountbalance.text.toString() + binding.five.text.toString()
            binding.amountbalance.setText(value)
        }
        binding.six.setOnClickListener { v ->
            val value =
                binding.amountbalance.text.toString() + binding.six.text.toString()
            binding.amountbalance.setText(value)
        }
        binding.seven.setOnClickListener { v ->
            val value =
                binding.amountbalance.text.toString() + binding.seven.text.toString()
            binding.amountbalance.setText(value)
        }
        binding.eight.setOnClickListener { v ->
            val value =
                binding.amountbalance.text.toString() + binding.eight.text.toString()
            binding.amountbalance.setText(value)
        }
        binding.nine.setOnClickListener { v ->
            val value =
                binding.amountbalance.text.toString() + binding.nine.text.toString()
            binding.amountbalance.setText(value)
        }
        binding.zeo.setOnClickListener { v ->
            if (binding.amountbalance.text.toString().isNotEmpty()) {
                val value =
                    binding.amountbalance.text.toString() + binding.zeo.text.toString()
                binding.amountbalance.setText(value)
            }
        }
        binding.erase.setOnClickListener { v ->
            val builder = StringBuilder()
            for (i in 0 until binding.amountbalance.text.toString().length - 1) {
                builder.append(binding.amountbalance.text.toString().last())
            }
            binding.amountbalance.setText(builder.toString())
        }
    }

    private fun makeThePayment() {
        binding.onRequestMoneyButton.setOnClickListener {
            if (binding.amountbalance.text.toString().isEmpty()) {
                MyAlertUtils.showAlertDialog(
                    this@CashFreeAddWalletActivity,
                    "Warning",
                    "Provide Valid Amount",
                    R.drawable.warning
                )
            } else {
                val amount = binding.amountbalance.text.toString()
                viewModel.doTheAddWallet(amount, this@CashFreeAddWalletActivity) {
                    paymentInitiate(it)
                }
            }
        }
    }


    private fun initialize() {
        try {
            CFPaymentGatewayService.initialize(applicationContext) // Application Context.
            AnalyticsUtil.sendPaymentEventsToBackend() // required for error reporting.
            CFPaymentGatewayService.getInstance().setCheckoutCallback(object :
                CFCheckoutResponseCallback {
                override fun onPaymentVerify(orderID: String) {
                    binding.amountbalance.setText("")
                }

                override fun onPaymentFailure(cfErrorResponse: CFErrorResponse, orderID: String) {
                    DisplayMessageUtil.error(
                        this@CashFreeAddWalletActivity,
                        "" + cfErrorResponse.message
                    )
                }
            })
        } catch (e: CFException) {
            DisplayMessageUtil.error(this@CashFreeAddWalletActivity, e.message)
            e.printStackTrace()
        }
    }


    private fun paymentInitiate(createResponse: CashFreeLatestGateway) {
        try {
            val cfSession = CFSession.CFSessionBuilder()
                .setEnvironment(CFSession.Environment.PRODUCTION)
                .setPaymentSessionID(createResponse.paymentSessionId)
                .setOrderId(createResponse.orderId)
                .build()
            val cfTheme = CFIntentTheme.CFIntentThemeBuilder()
                .setPrimaryTextColor("#000000")
                .setBackgroundColor("#FFFFFF")
                .build()
            val cfupiIntentCheckout =
                CFUPIIntentCheckout.CFUPIIntentBuilder() // Use either the enum or the application package names to order the UPI apps as per your needed
                    // Remove both if you want to use the default order which cashfree provides based on the popularity
                    // NOTE - only one is needed setOrder or setOrderUsingPackageName
                    //                                        .setOrderUsingPackageName(Arrays.asList("com.dreamplug.androidapp", "in.org.npci.upiapp"))
                    //                                        .setOrder(Arrays.asList(CFUPIIntentCheckout.CFUPIApps.BHIM, CFUPIIntentCheckout.CFUPIApps.PHONEPE))
                    .build()
            val cfupiIntentCheckoutPayment = CFUPIIntentCheckoutPayment.CFUPIIntentPaymentBuilder()
                .setSession(cfSession)
                .setCfUPIIntentCheckout(cfupiIntentCheckout)
                .setCfIntentTheme(cfTheme)
                .build()
            val gatewayService = CFPaymentGatewayService.getInstance()
            gatewayService.doPayment(this@CashFreeAddWalletActivity, cfupiIntentCheckoutPayment)
        } catch (exception: CFException) {
            exception.printStackTrace()
        }
    }

    override fun onPaymentVerify(orderID: String) {
        binding.amountbalance.setText("")
        DisplayMessageUtil.success(this@CashFreeAddWalletActivity, "Transaction Initiated")
    }

    override fun onPaymentFailure(cFErrorResponse: CFErrorResponse, orderID: String) {
        DisplayMessageUtil.error(
            this@CashFreeAddWalletActivity,
            cFErrorResponse.message ?: "Some Error"
        )
    }

}