package com.fintech.superadmin.activities.rebranded

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.fintech.superadmin.R
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RebrandedHome : BaseComponentAct() {

    val viewModel by viewModels<HomeViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(
                    color = MaterialTheme.colors.surface
                ) {
                    HomeContent()
                }
            }
        }
    }

    @Composable
    private fun HomeContent() {
        viewModel.user = viewModel.dao.user.observeAsState(initial = null)
        viewModel.userProfile = viewModel.dao.userProfile.observeAsState(initial = null)

        BaseScaffold(
            topBar = {
                TopAppBar {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.sdp, vertical = 4.sdp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SubcomposeAsyncImage(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colors.surface,
                                        shape = RoundedCornerShape(8.sdp)
                                    )
                                    .size(40.sdp),
                                model = viewModel.userProfile.value?.PROFILE_IMG,
                                contentDescription = "profile_image",
                                error = {
                                    AsyncImage(
                                        modifier = Modifier.size(40.sdp),
                                        model = R.drawable.ic_profile_user,
                                        contentDescription = "err_img"
                                    )
                                },
                                loading = {
                                    AsyncImage(
                                        modifier = Modifier.size(40.sdp),
                                        model = R.drawable.ic_profile_user,
                                        contentDescription = "err_img"
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.width(10.sdp))
                            Column {
                                Text(
                                    text = viewModel.user.value?.name ?: "",
                                    fontSize = 13.textSdp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                viewModel.user.value?.userstatus?.let {
                                    var title = ""
                                    when (it) {
                                        "1" -> {
                                            title = "Retailer"
                                        }

                                        "2" -> {
                                            title = "Distributor"
                                        }

                                        "3" -> {
                                            title = "Master Distributor"
                                        }

                                        "4" -> {
                                            title = "API User"
                                        }

                                        "5" -> {
                                            title = "Customer"
                                        }
                                    }
                                    Text(
                                        text = title,
                                        fontSize = 11.textSdp,
                                        color = MaterialTheme.colors.surface.copy(.65f)
                                    )
                                }

                            }
                        }


                    }
                }
            }
        ) {

        }
    }

}