package com.fintech.superadmin.flight.presentation.airSearch

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import com.fintech.superadmin.flight.domain.state.SharedState.multiCityTripList
import com.fintech.superadmin.flight.presentation.airSearch.component.CustomCheckBox
import com.fintech.superadmin.flight.presentation.airSearch.component.SingleTripScreen
import com.fintech.superadmin.flight.presentation.airlines.AirlinesViewModel
import com.fintech.superadmin.flight.presentation.common.BaseScaffold
import com.fintech.superadmin.flight.presentation.common.BaseScaffoldWithBottomSheet
import com.fintech.superadmin.flight.presentation.common.BasicScreenState
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AirSearchActivity : ComponentActivity() {

    val viewModel by viewModels<AirSearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colorScheme.surface) {
                    AirSearchContent()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    @Composable
    fun AirSearchContent() {
        val scaffoldState = rememberBottomSheetScaffoldState()
        val scope = rememberCoroutineScope()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LaunchedEffect(key1 = viewModel.selection.value) {
                viewModel.searchFlight()
            }
        } else {
            LaunchedEffect(key1 = true) {
                viewModel.searchFlight()
            }
        }
        LaunchedEffect(viewModel.changesToFilter){
            viewModel.state.value.receivedResponse?.let {
                viewModel.doTheFilteration()
            }
        }

        BaseScaffoldWithBottomSheet(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "arrow_back"
                            )
                        }
                    },
                    title = {
                        Column {
                            multiCityTripList.firstOrNull()?.let {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = it.fromAirPortDto.value?.aIRPORTCODE ?: "",
                                        fontSize = 12.textSdp,
                                    )
                                    Icon(
                                        modifier = Modifier.size(width = 32.sdp, height = 12.sdp),
                                        imageVector = Icons.Default.ArrowForward,
                                        contentDescription = "Arrow"
                                    )
                                    Text(
                                        text = it.toAirPortDto.value?.aIRPORTCODE ?: "",
                                        fontSize = 12.textSdp,
                                    )
                                }

                                Text(
                                    text = buildAnnotatedString {
                                        append(it.departDateModel.value?.englishData ?: "")

                                        it.arrivalDateModel.value?.englishData?.let {
                                            append(" | $it")
                                        }
                                        viewModel.state.value.receivedResponse?.tripDetails?.firstOrNull()?.flights?.size?.let { size ->
                                            append(" | $size Flights")
                                        }
                                    },
                                    fontSize = 10.textSdp
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                viewModel.state.value.receivedResponse?.let {
                                    scope.launch {
                                        scaffoldState.drawerState.open()
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Menu,
                                contentDescription = "menu"
                            )
                        }
                    }
                )
            },
            state = viewModel.state,
            bottomSheetScaffoldState = scaffoldState,
            drawerContent = {
                FilterDrawerContent()
            },
            sheetContent = {}
        ) {
            SingleTripScreen()
        }
    }


    @Composable
    private fun FilterDrawerContent() {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.sdp)
        ) {
            item {
                Text(
                    modifier = Modifier.padding(vertical = 2.sdp),
                    text = "Fare",
                    fontSize = 8.textSdp,
                    fontWeight = FontWeight.Bold
                )
            }
            item {
                CustomCheckBox(
                    isChecked = viewModel.commissionDisplay,
                    title = "Commission",
                    onCheckChanges = {
                        viewModel.commissionDisplay = !viewModel.commissionDisplay
                    }
                )
                CustomCheckBox(
                    isChecked = viewModel.netFareDisplay,
                    title = "Net Fare",
                    onCheckChanges = {
                        viewModel.netFareDisplay = !viewModel.netFareDisplay
                    }
                )
            }

            item {
                Text(
                    modifier = Modifier.padding(vertical = 2.sdp),
                    text = "Number of Stops",
                    fontSize = 8.textSdp,
                    fontWeight = FontWeight.Bold
                )
            }
            item {

                CustomCheckBox(
                    isChecked = viewModel.nonStop,
                    title = "Non Stop",
                    count = viewModel.nonStopCount,
                    onCheckChanges = {
                        viewModel.nonStop = !viewModel.nonStop
                        viewModel.changesToFilter = !viewModel.changesToFilter
                    }
                )

                CustomCheckBox(
                    isChecked = viewModel.oneStop,
                    title = "1 Stop",
                    count = viewModel.oneStopCount,
                    onCheckChanges = {
                        viewModel.oneStop = !viewModel.oneStop
                        viewModel.changesToFilter = !viewModel.changesToFilter
                    }
                )
                CustomCheckBox(
                    isChecked = viewModel.twoStop,
                    title = "2 Stop",
                    count = viewModel.twoStopCount,
                    onCheckChanges = {
                        viewModel.twoStop = !viewModel.twoStop
                        viewModel.changesToFilter = !viewModel.changesToFilter
                    }
                )

                CustomCheckBox(
                    isChecked = viewModel.threeStop,
                    title = "3 Stop",
                    count = viewModel.threeStopCount,
                    onCheckChanges = {
                        viewModel.threeStop = !viewModel.threeStop
                        viewModel.changesToFilter = !viewModel.changesToFilter
                    }
                )
            }

            item {
                Text(
                    modifier = Modifier.padding(vertical = 2.sdp),
                    text = "Airlines",
                    fontSize = 8.textSdp,
                    fontWeight = FontWeight.Bold
                )
            }

            items(viewModel.availableAirlines.values.toList()) {
                CustomCheckBox(
                    isChecked = it.seatBoxDialog.value,
                    title = it.title,
                    count = it.count,
                    onCheckChanges = {
                        it.seatBoxDialog.value = !it.seatBoxDialog.value
                        viewModel.changesToFilter = !viewModel.changesToFilter
                    }
                )
            }


            item {
                Text(
                    modifier = Modifier.padding(vertical = 2.sdp),
                    text = "Refundable",
                    fontSize = 8.textSdp,
                    fontWeight = FontWeight.Bold
                )
            }
            item {
                CustomCheckBox(
                    isChecked = viewModel.refundable,
                    title = "Refundable",
                    count = viewModel.refundableCount,
                    onCheckChanges = {
                        viewModel.refundable = !viewModel.refundable
                        viewModel.changesToFilter = !viewModel.changesToFilter
                    }
                )
            }


        }
    }

}