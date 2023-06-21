package com.fintech.superadmin.flight.presentation.seatSelection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import com.fintech.superadmin.flight.data.remote.dto.seatMap.getSeatColor
import com.fintech.superadmin.flight.presentation.common.BasicScreenState
import com.fintech.superadmin.flight.util.common.Constant
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeatSelectionActivity : ComponentActivity() {

    val viewModel by viewModels<SeatSelectionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colorScheme.surface) {
                    SeatContent()
                }
            }
        }

    }

    @Composable
    private fun SeatContent() {
        BasicScreenState(state = viewModel.state) {
            viewModel.state.value.receivedResponse?.firstOrNull()?.seatRow?.let { seatRows ->
                LazyColumn(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(seatRows) {
                        LazyRow(
                            modifier = Modifier
                                .padding(5.sdp)
                                .weight(weight = 1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            it?.seatDetails?.let {

                                items(it) { seatDetailsItem ->
                                    if (seatDetailsItem?.sSRStatus != 0) {

                                        Box(
                                            modifier = Modifier
                                                .size(40.sdp)
                                                .padding(3.sdp)
                                                .background(color = seatDetailsItem.getSeatColor()),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = seatDetailsItem?.sSRTypeName ?: "",
                                                fontSize = 10.textSdp,
                                                fontWeight = FontWeight.SemiBold,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }


                                    } else {
                                        Spacer(
                                            modifier = Modifier
                                                .padding(horizontal = 10.sdp)
                                                .weight(weight = 1f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initialize() {
        viewModel.flight = intent.getParcelableExtra(Constant.FlightsItem)
        viewModel.searchKey = intent.getStringExtra(Constant.SearchKey)
        viewModel.passengerJson = intent.getStringExtra(Constant.PassengerJson)
        viewModel.selectSeatsCall()
    }
}