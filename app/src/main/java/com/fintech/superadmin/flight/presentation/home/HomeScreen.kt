package com.fintech.superadmin.flight.presentation.home

import android.content.Context
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fintech.superadmin.flight.domain.model.TripModel
import com.fintech.superadmin.flight.domain.state.SharedState.adult
import com.fintech.superadmin.flight.domain.state.SharedState.child
import com.fintech.superadmin.flight.domain.state.SharedState.infant
import com.fintech.superadmin.flight.domain.state.SharedState.multiCityTripList
import com.fintech.superadmin.flight.domain.state.SharedState.selectedSeatClass
import com.fintech.superadmin.flight.domain.state.SharedState.selectedTripValue
import com.fintech.superadmin.flight.domain.state.SharedState.tripTypeList
import com.fintech.superadmin.flight.domain.state.selectedAirLines
import com.fintech.superadmin.flight.presentation.airlines.AirlinesViewModel
import com.fintech.superadmin.flight.presentation.airports.AirportsViewModel
import com.fintech.superadmin.flight.presentation.common.BaseScaffoldWithBottomSheet
import com.fintech.superadmin.flight.presentation.common.TripTypeCard
import com.fintech.superadmin.flight.presentation.home.component.AddPassengerSheet
import com.fintech.superadmin.flight.presentation.home.component.DrawerHeader
import com.fintech.superadmin.flight.presentation.home.component.LocationsDestinations
import com.fintech.superadmin.flight.presentation.home.component.LocationsDestinationsRoundTrip
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    airlinesViewModel: AirlinesViewModel = viewModel(),
    airportsViewModel: AirportsViewModel = viewModel(),
    viewModel: HomeViewModel = viewModel(),
    context: Context = LocalContext.current,
    onBackPressedDispatcher: OnBackPressedDispatcher = OnBackPressedDispatcher()
) {

    LaunchedEffect(key1 = airlinesViewModel.search){
        airlinesViewModel.getAirlines()
    }

    LaunchedEffect(key1 = airportsViewModel.search){
        airportsViewModel.getAirports()
    }


    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    BaseScaffoldWithBottomSheet(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Flight")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Toggle drawer"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onBackPressedDispatcher.onBackPressed()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "back"
                        )
                    }
                },
            )
        },
        state = viewModel.state,
        bottomSheetScaffoldState = scaffoldState,
        drawerGesturesEnabled = true,
        drawerContent = {
            DrawerHeader()
            /**
            DrawerBody(
            items = listOf(
            MenuItem(
            id = "home",
            title = "Home",
            contentDescription = "Go to home screen",
            icon = Icons.Default.Home
            ),
            MenuItem(
            id = "settings",
            title = "Settings",
            contentDescription = "Go to settings screen",
            icon = Icons.Default.Settings
            ),
            MenuItem(
            id = "help",
            title = "Help",
            contentDescription = "Get help",
            icon = Icons.Default.Info
            ),
            ),
            onItemClick = {
            println("Clicked on ${it.title}")
            }
            )
             **/
        },
        sheetContent = {
            AddPassengerSheet(bottomSheetScaffoldState = scaffoldState)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 15.sdp, vertical = 5.sdp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.sdp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(0.05f),
                        shape = RoundedCornerShape(15.sdp)
                    )
                    .clip(RoundedCornerShape(15.sdp))
            ) {
                val isSelectedItem: (TripModel) -> Boolean = { selectedTripValue == it }
                val onChangeState: (TripModel) -> Unit = { selectedTripValue = it }
                 tripTypeList.forEach {
                    TripTypeCard(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 1.sdp),
                        tripModel = it,
                        onChangeState = onChangeState,
                        isSelectedItem = isSelectedItem
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.sdp))
            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.sdp)
                ) {

                    when (selectedTripValue.id) {
                        0 -> {
                              multiCityTripList.firstOrNull()?.let {
                                LocationsDestinations(
                                    fromAirPortDto = it.fromAirPortDto,
                                    toAirPortDto = it.toAirPortDto,
                                    departDateModel = it.departDateModel,
                                    fromSelected = {
                                        viewModel.setSavedFromAirPort()
                                    },
                                    toSelected = {
                                        viewModel.setSavedToAirPort()
                                    }
                                )
                            }
                        }
                        1 -> {
                            multiCityTripList.firstOrNull()?.let {
                                LocationsDestinationsRoundTrip(
                                    fromAirPortDto = it.fromAirPortDto,
                                    toAirPortDto = it.toAirPortDto,
                                    departDateModel = it.departDateModel,
                                    arrivalDateModel = it.arrivalDateModel
                                )
                            }
                        }
                        2 -> {
                            for (i in 0 until multiCityTripList.size) {
                                multiCityTripList[i].apply {
                                    LocationsDestinations(
                                        fromAirPortDto = fromAirPortDto,
                                        toAirPortDto = toAirPortDto,
                                        departDateModel = departDateModel
                                    )
                                    if (i == 1 && i == multiCityTripList.size - 1) {
                                        Row(
                                            modifier = Modifier.padding(vertical = 5.sdp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            OutlinedButton(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(end = 5.sdp),
                                                onClick = {
                                                    viewModel.addMore()
                                                }
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Add,
                                                        contentDescription = "Add"
                                                    )
                                                    Text(text = " ADD MORE", fontSize = 10.textSdp)
                                                }
                                            }
                                        }
                                    }
                                    if (i == 2 && i == multiCityTripList.size - 1) {
                                        Row(
                                            modifier = Modifier.padding(vertical = 5.sdp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            OutlinedButton(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(end = 5.sdp),
                                                onClick = {
                                                    viewModel.addMore()
                                                }
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Add,
                                                        contentDescription = "Add"
                                                    )
                                                    Text(text = " ADD MORE", fontSize = 10.textSdp)
                                                }
                                            }
                                            OutlinedButton(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(start = 5.sdp),
                                                onClick = {
                                                    viewModel.remove()
                                                }
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Delete,
                                                        contentDescription = "Delete"
                                                    )
                                                    Text(text = "Remove", fontSize = 10.textSdp)
                                                }
                                            }
                                        }
                                    }
                                    if (i > 2) {
                                        Row(
                                            modifier = Modifier.padding(vertical = 5.sdp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            OutlinedButton(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(start = 5.sdp),
                                                onClick = {
                                                    viewModel.remove()
                                                }
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Delete,
                                                        contentDescription = "Delete"
                                                    )
                                                    Text(text = "Remove", fontSize = 10.textSdp)
                                                }
                                            }
                                        }
                                    }


                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(vertical = 5.sdp)
                            .clickable {
                                scope.launch {
                                    if (scaffoldState.bottomSheetState.isCollapsed) {
                                        scaffoldState.bottomSheetState.expand()
                                    } else {
                                        scaffoldState.bottomSheetState.collapse()
                                    }
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "PASSENGER(S)",
                                fontWeight = FontWeight.Light,
                                fontSize = 8.textSdp,
                                modifier = Modifier.padding(vertical = 1.sdp)
                            )
                            Text(
                                text = buildAnnotatedString {
                                    append("${adult} Adult")
                                    if (child > 0) {
                                        append(", ${child} Child")
                                    }
                                    if (infant > 0) {
                                        append(", ${infant} Infant")
                                    }
                                    append(" - ${selectedSeatClass.title}")
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.textSdp,
                                modifier = Modifier.padding(vertical = 1.sdp)
                            )
                            Divider(modifier = Modifier.padding(top = 2.sdp))
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(vertical = 5.sdp)
                            .clickable {
                                viewModel.displayAirlinesList(airlinesViewModel = airlinesViewModel)
                                //context.startActivity(Intent(context, AirlinesActivity::class.java))
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Airline(S)",
                                fontWeight = FontWeight.Light,
                                fontSize = 8.textSdp,
                                modifier = Modifier.padding(vertical = 1.sdp)
                            )
                            LazyRow(
                            ) {
                                if (selectedAirLines.isEmpty()) {
                                    item{
                                        Text(
                                            text = "ALL",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.textSdp,
                                            modifier = Modifier.padding(vertical = 1.sdp)
                                        )
                                    }
                                } else {
                                    val list = selectedAirLines.toList()
                                    items(list) {
                                        Text(
                                            text = buildAnnotatedString {
                                                append(it.second.airlinesName?:"")
                                                if (list.lastOrNull()?.first != it.first) {
                                                    append(", ")
                                                }
                                            },
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.textSdp,
                                            modifier = Modifier.padding(vertical = 1.sdp)
                                        )
                                    }
                                }
                            }
                            Divider(modifier = Modifier.padding(top = 2.sdp))
                        }
                    }

                    if (selectedTripValue.id == 0) {
                        Row(
                            modifier = Modifier.padding(vertical = 2.sdp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            androidx.compose.material.Switch(
                                checked = viewModel.notStopOnly,
                                onCheckedChange = {
                                    viewModel.notStopOnly = it
                                }
                            )
                            Text(
                                text = "Non Stop Only",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 10.textSdp,
                                modifier = Modifier.padding(vertical = 1.sdp)
                            )
                        }
                    }

                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.sdp),
                        onClick = {
                            viewModel.searchFlight(context = context)
                        }
                    ) {
                        Text(text = "Search Flight", modifier = Modifier.padding(vertical = 5.sdp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.sdp))
        }
    }
}