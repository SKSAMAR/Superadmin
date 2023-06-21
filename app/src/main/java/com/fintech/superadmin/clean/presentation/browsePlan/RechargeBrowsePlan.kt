package com.fintech.superadmin.clean.presentation.browsePlan

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.presentation.browsePlan.component.BrowsePlanScreen
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RechargeBrowsePlan : BaseComponentAct() {

    val viewModel by viewModels<BrowsePlanViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializer()
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    RechargeScreenContent()
                }
            }
        }
    }

    @Composable
    fun RechargeScreenContent(
        viewModel: BrowsePlanViewModel = viewModel(),
    ) {
        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Browse Plan")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onBackPressedDispatcher.onBackPressed()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "arrow_back",
                                tint = MaterialTheme.colors.surface
                            )
                        }
                    }
                )
            }
        ) {
            BasicScreenState(state = viewModel.state) {
                BrowsePlanScreen {
                    val intentData = Intent()
                    intentData.putExtra("Price", it.toString())
                    setResult(RESULT_OK, intentData)
                    finish()
                }
            }
        }
    }

    private fun initializer() {
        viewModel.selectedMobile = intent.getStringExtra("mobile") ?: ""
        viewModel.selectedOperatorCode = intent.getStringExtra("selectedOperatorCode") ?: ""
        viewModel.fetchBrowsePlans()
    }

}