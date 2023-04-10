package com.fintech.scnpay.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.fintech.scnpay.activities.HomeActivity
import com.fintech.scnpay.clean.common.BaseComponentAct
import com.fintech.scnpay.auth.component.LoginScreen
import com.fintech.scnpay.deer_listener.Receiver
import com.fintech.scnpay.ui.theme.YespayTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseComponentAct(), Receiver<Boolean> {

    val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.receiver = this
        setContent {
            YespayTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    SetUpScreen()
                }
            }
        }
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