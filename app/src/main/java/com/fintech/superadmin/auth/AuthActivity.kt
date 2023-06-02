package com.fintech.superadmin.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.fintech.superadmin.activities.HomeActivity
import com.fintech.superadmin.auth.component.LoginScreen
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.deer_listener.Receiver
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import com.fintech.superadmin.util.ViewUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseComponentAct(), Receiver<Boolean> {

    val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        viewModel.receiver = this
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    SetUpScreen()
                }
            }
        }
    }

    private fun init(){
        val deepLinkUri = intent.data
        if (deepLinkUri != null) {
            // Retrieve the deep link data from the Uri
            val deepLinkData = deepLinkUri.toString()
            ViewUtils.showToast(this@AuthActivity, "" + deepLinkData)
        } else {
            //ViewUtils.showToast(this@AuthActivity, "Nothing")
        }

        /**
        FirebaseApp.initializeApp(this)
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                // Get deep link from result (may be null if no link is found)
                val deepLink: Uri?
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                    assert(deepLink != null)
                    var referlink = deepLink.toString()
                    try {
                        referlink = referlink.substring(referlink.lastIndexOf("=") + 1)
                        viewModel.mobile.value = referlink
                        Log.d("REFER_MESSAGE", referlink)
                    } catch (e: Exception) {
                        Log.d("REFER_MESSAGE", e.message?:"Some Error")
                    }
                }
            }.addOnFailureListener(this) { e ->
                Log.d("REFER_MESSAGE", e.message?:"Some Error")
            }

        **/

    }

    @Composable
    fun SetUpScreen() {
        BaseScaffold(
            action = {},
        ) {
            LoginScreen(viewModel = viewModel)
        }
    }

    override fun getData(data: Boolean?) {
        data?.let {
            startActivity(Intent(this@AuthActivity, HomeActivity::class.java))
            finish()
        }
    }


}