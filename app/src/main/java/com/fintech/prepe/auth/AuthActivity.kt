package com.fintech.prepe.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.fintech.prepe.activities.HomeActivity
import com.fintech.prepe.clean.common.BaseComponentAct
import com.fintech.prepe.auth.component.LoginScreen
import com.fintech.prepe.deer_listener.Receiver
import com.fintech.prepe.ui.theme.YespayTheme
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