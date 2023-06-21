package com.fintech.superadmin.clean.presentation.browsePlan.component

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fintech.superadmin.R
import com.fintech.superadmin.activities.rechargeactivities.RechargeMyPlan
import com.fintech.superadmin.clean.presentation.browsePlan.BrowsePlanViewModel
import com.fintech.superadmin.clean.presentation.commonComponent.BasicOutlinedSearchView
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.flight.util.common.Constant
import com.fintech.superadmin.ui.theme.BackgroundCardGrey

@Composable
fun BrowsePlanScreen(
    viewModel: BrowsePlanViewModel = viewModel(),
    context: Context = LocalContext.current,
    getPlan: (Any?) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundCardGrey())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.sdp, bottom = 2.sdp),
            horizontalArrangement = Arrangement.Center
        ) {

            BasicOutlinedSearchView(
                hint = "Search",
                value = viewModel.search,
                onValueChange = { viewModel.search = it },
                maxLength = 100,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.None
                ),
                trailingIcon = {},
                modifier = Modifier.fillMaxWidth(.9f)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    modifier = Modifier.padding(vertical = 5.sdp, horizontal = 15.sdp),
                    text = "Browse Plan",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.textSdp,
                    fontWeight = FontWeight.Light
                )
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            viewModel.state.value.receivedResponse?.let { information ->
                items(information) {
                    it?.let { dynamicBrowsePlan ->

                        val result = dynamicBrowsePlan.planList?.any {
                            it?.RS?.toString()?.contains(viewModel.search) == true
                        }
                        if (result == true) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 15.sdp),
                                shape = RoundedCornerShape(
                                    topStart = 3.sdp,
                                    topEnd = 3.sdp,
                                    bottomStart = 0.dp,
                                    bottomEnd = 0.dp
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.sdp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 6.sdp, horizontal = 8.sdp),
                                    ) {
                                        Text(
                                            text = dynamicBrowsePlan.title ?: "",
                                            style = TextStyle(
                                                fontSize = 12.textSdp,
                                                color = Color.Black,
                                                fontWeight = FontWeight.SemiBold
                                            ),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    dynamicBrowsePlan.planList?.forEach {
                                        if (it?.RS?.toString()
                                                ?.contains(viewModel.search, true) == true
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        vertical = 6.sdp,
                                                        horizontal = 8.sdp
                                                    )
                                                    .clickable {
                                                        getPlan("" + it.RS)
                                                    },
                                            ) {
                                                Spacer(modifier = Modifier.height(5.sdp))
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {

                                                    Text(
                                                        text = stringResource(id = R.string.rupee_sign) + it.RS,
                                                        style = TextStyle(
                                                            fontSize = 12.textSdp,
                                                            color = Color.Black,
                                                            fontWeight = FontWeight.SemiBold
                                                        ),
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis
                                                    )

                                                    Text(
                                                        text = it.VALIDITY?.toString()
                                                            ?.toString() ?: "",
                                                        style = TextStyle(
                                                            fontSize = 12.textSdp,
                                                            color = Color.Black,
                                                            fontWeight = FontWeight.Normal
                                                        ),
                                                        maxLines = 3,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                }
                                                Text(
                                                    text = it.DESC?.toString() ?: "",
                                                    style = TextStyle(
                                                        fontSize = 8.textSdp,
                                                        color = Color.Gray,
                                                        fontWeight = FontWeight.Normal
                                                    ),
                                                    maxLines = 3,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                Spacer(modifier = Modifier.height(5.sdp))
                                            }
                                            Spacer(modifier = Modifier.height(2.sdp))
                                            Divider()
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(15.dp))
                        }
                    }
                }
            }

        }
    }
}