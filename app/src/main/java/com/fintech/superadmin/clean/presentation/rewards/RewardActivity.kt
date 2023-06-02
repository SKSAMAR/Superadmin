package com.fintech.superadmin.clean.presentation.rewards

import com.fintech.superadmin.R
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.presentation.common.ScreenAnimation
import com.fintech.superadmin.clean.presentation.rewards.component.RewardCard
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RewardActivity : BaseComponentAct() {

    val viewModel by viewModels<MyRewardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    RewardContent()
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Composable
    private fun RewardContent() {
        val listState = rememberLazyGridState()
        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Rewards")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back",
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.HelpOutline,
                                contentDescription = "help",
                            )
                        }
                    }
                )
            }
        ) {
            BasicScreenState(state = viewModel.state) {
                LazyVerticalGrid(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.sdp),
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    viewModel.state.value.receivedResponse?.let {

                        item(span = {
                            GridItemSpan(3)
                        }) {
                            Spacer(modifier = Modifier.height(25.sdp))
                        }

                        items(it) {
                            RewardCard(it) {
                                viewModel.scratchTheCard(model = it, context = this@RewardActivity)
                            }
                        }

                        item(span = {
                            GridItemSpan(3)
                        }) {
                            Spacer(modifier = Modifier.height(25.sdp))
                        }
                    }
                }
            }

            if (viewModel.isFetchingMore) {
                ScreenAnimation(
                    resources = R.raw.loading,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

}