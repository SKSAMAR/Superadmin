package com.fintech.superadmin.flight.presentation.home.component

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChangeCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fintech.superadmin.flight.data.remote.dto.AirPortDto
import com.fintech.superadmin.flight.data.remote.sendDto.TripInfoItem
import com.fintech.superadmin.flight.domain.DateModel
import com.fintech.superadmin.flight.presentation.airports.AirportsActivity
import com.fintech.superadmin.flight.presentation.airports.AirportsViewModel
import com.fintech.superadmin.flight.presentation.common.DateDialogSys.showDateDialog
import com.fintech.superadmin.flight.presentation.home.HomeViewModel
import com.fintech.superadmin.flight.util.common.Constant
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LocationsDestinations(
    airportsViewModel: AirportsViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel(),
    fromAirPortDto: MutableState<AirPortDto?>,
    toAirPortDto: MutableState<AirPortDto?>,
    departDateModel: MutableState<DateModel?>,
    context: Context = LocalContext.current,
    fromSelected: ()->Unit = {},
    toSelected: ()->Unit = {},
) {

    /**
    val fromLocation = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                ComponentActivity.RESULT_OK -> {
                    data?.let { intent ->
                        fromAirPortDto.value =
                            intent.getParcelableExtra(Constant.AirPort) as AirPortDto?
                    }
                }
            }
        }

    val toLocation =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                ComponentActivity.RESULT_OK -> {
                    data?.let { intent ->
                        toAirPortDto.value =
                            intent.getParcelableExtra(Constant.AirPort) as AirPortDto?
                    }
                }
            }
        }
    **/


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.sdp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = 5.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.clickable {
                        homeViewModel.displayAirportsList(airportsViewModel = airportsViewModel){
                            fromAirPortDto.value = it
                            fromSelected()
                        }
                        /**
                        val intent = Intent(context, AirportsActivity::class.java)
                        intent.putExtra(Constant.SelectedAirPort, toAirPortDto.value?.iD ?: "")
                        fromLocation.launch(intent)
                        **/
                    }
                ) {
                    Text(
                        text = "From",
                        fontWeight = FontWeight.Light,
                        fontSize = 8.textSdp,
                        modifier = Modifier.padding(vertical = 1.sdp)
                    )
                    Text(
                        text = fromAirPortDto.value?.aIRPORTCODE ?: "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.textSdp,
                        modifier = Modifier.padding(vertical = 1.sdp)
                    )
                    Text(
                        text = fromAirPortDto.value?.aIRPORT?.replaceAfter("&", "")
                            ?: fromAirPortDto.value?.cITY ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 10.textSdp,
                        modifier = Modifier.padding(vertical = 1.sdp)
                    )

                    Divider(modifier = Modifier.padding(end = 10.sdp, top = 2.sdp))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 5.sdp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            homeViewModel.displayAirportsList(airportsViewModel = airportsViewModel){
                                toAirPortDto.value = it
                                toSelected()
                            }
                            /**
                            val intent = Intent(context, AirportsActivity::class.java)
                            intent.putExtra(Constant.SelectedAirPort, toAirPortDto.value?.iD ?: "")
                            toLocation.launch(intent)
                            **/
                        },
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "To",
                        fontWeight = FontWeight.Light,
                        fontSize = 8.textSdp,
                        modifier = Modifier.padding(vertical = 1.sdp)
                    )
                    Text(
                        text = toAirPortDto.value?.aIRPORTCODE ?: "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.textSdp,
                        modifier = Modifier.padding(vertical = 1.sdp)
                    )
                    Text(
                        text = toAirPortDto.value?.aIRPORT?.replaceAfter("&", "")
                            ?: toAirPortDto.value?.cITY ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 10.textSdp,
                        modifier = Modifier.padding(vertical = 1.sdp)
                    )
                    Divider(modifier = Modifier.padding(start = 10.sdp, top = 2.sdp))
                }
            }
        }

        IconButton(
            onClick = {
                val temp = fromAirPortDto.value
                fromAirPortDto.value = toAirPortDto.value
                toAirPortDto.value = temp
                fromSelected()
                toSelected()
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.ChangeCircle,
                contentDescription = "changes"
            )
        }
    }

    Row(
        modifier = Modifier.padding(vertical = 5.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(.5f)
                .clickable {
                    context.showDateDialog {
                        toSpecificDepartDates(it) {
                            departDateModel.value = it
                        }
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Departure",
                    fontWeight = FontWeight.Light,
                    fontSize = 8.textSdp,
                    modifier = Modifier.padding(vertical = 1.sdp)
                )
                Text(
                    text = departDateModel.value?.englishData ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.textSdp,
                    modifier = Modifier.padding(vertical = 1.sdp)
                )
                Divider(modifier = Modifier.padding(end = 10.sdp, top = 2.sdp))
            }
        }
    }
}


@Composable
fun LocationsDestinationsRoundTrip(
    fromAirPortDto: MutableState<AirPortDto?>,
    toAirPortDto: MutableState<AirPortDto?>,
    departDateModel: MutableState<DateModel?>,
    arrivalDateModel: MutableState<DateModel?>,
    context: Context = LocalContext.current
) {

    val fromLocation =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                ComponentActivity.RESULT_OK -> {
                    data?.let { intent ->
                        fromAirPortDto.value =
                            intent.getParcelableExtra(Constant.AirPort) as AirPortDto?
                    }
                }
            }
        }

    val toLocation =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                ComponentActivity.RESULT_OK -> {
                    data?.let { intent ->
                        toAirPortDto.value =
                            intent.getParcelableExtra(Constant.AirPort) as AirPortDto?
                    }
                }
            }
        }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.sdp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = 5.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.clickable {
                        val intent = Intent(context, AirportsActivity::class.java)
                        intent.putExtra(Constant.SelectedAirPort, toAirPortDto.value?.iD ?: "")
                        fromLocation.launch(intent)
                    }
                ) {
                    Text(
                        text = "From",
                        fontWeight = FontWeight.Light,
                        fontSize = 8.textSdp,
                        modifier = Modifier.padding(vertical = 1.sdp)
                    )
                    Text(
                        text = fromAirPortDto.value?.aIRPORTCODE ?: "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.textSdp,
                        modifier = Modifier.padding(vertical = 1.sdp)
                    )
                    Text(
                        text = fromAirPortDto.value?.aIRPORT?.replaceAfter("&", "")
                            ?: fromAirPortDto.value?.cITY ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 10.textSdp,
                        modifier = Modifier.padding(vertical = 1.sdp)
                    )

                    Divider(modifier = Modifier.padding(end = 10.sdp, top = 2.sdp))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 5.sdp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(context, AirportsActivity::class.java)
                            intent.putExtra(Constant.SelectedAirPort, toAirPortDto.value?.iD ?: "")
                            toLocation.launch(intent)
                        },
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "To",
                        fontWeight = FontWeight.Light,
                        fontSize = 8.textSdp,
                        modifier = Modifier.padding(vertical = 1.sdp)
                    )
                    Text(
                        text = toAirPortDto.value?.aIRPORTCODE ?: "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.textSdp,
                        modifier = Modifier.padding(vertical = 1.sdp)
                    )
                    Text(
                        text = toAirPortDto.value?.aIRPORT?.replaceAfter("&", "")
                            ?: toAirPortDto.value?.cITY ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 10.textSdp,
                        modifier = Modifier.padding(vertical = 1.sdp)
                    )
                    Divider(modifier = Modifier.padding(start = 10.sdp, top = 2.sdp))
                }
            }
        }

        IconButton(
            onClick = {
                val temp = fromAirPortDto.value
                fromAirPortDto.value = toAirPortDto.value
                toAirPortDto.value = temp
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.ChangeCircle,
                contentDescription = "changes"
            )
        }
    }

    Row(
        modifier = Modifier.padding(vertical = 5.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable {
                    context.showDateDialog {
                        toSpecificDepartDates(it) {
                            departDateModel.value = it
                        }
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Departure",
                    fontWeight = FontWeight.Light,
                    fontSize = 8.textSdp,
                    modifier = Modifier.padding(vertical = 1.sdp)
                )
                Text(
                    text = departDateModel.value?.englishData ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.textSdp,
                    modifier = Modifier.padding(vertical = 1.sdp)
                )
                Divider(modifier = Modifier.padding(end = 10.sdp, top = 2.sdp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable {
                    context.showDateDialog(
                        minDate = arrivalDateModel.value?.millisecond ?: System.currentTimeMillis(),
                        onDateChange = {
                            toSpecificDepartDates(it) {
                                arrivalDateModel.value = it
                            }
                        })
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Arrival",
                    fontWeight = FontWeight.Light,
                    fontSize = 8.textSdp,
                    modifier = Modifier.padding(vertical = 1.sdp)
                )
                Text(
                    text = arrivalDateModel.value?.englishData ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.textSdp,
                    modifier = Modifier.padding(vertical = 1.sdp)
                )
                Divider(modifier = Modifier.padding(start = 10.sdp, top = 2.sdp))
            }
        }
    }
}


fun toSpecificDepartDates(date: Date, givenDateModel: (DateModel) -> Unit = {}) {
    val sdf = SimpleDateFormat("MM/dd/yyyy")
    val currentDate = sdf.format(date)

    //val fdb = SimpleDateFormat("yyyy-MM-dd")
    val fdb = SimpleDateFormat("MM/dd/yyyy")
    val flightDob = fdb.format(date)

    val sdf2 = SimpleDateFormat("MMM, d")
    val englishDate = sdf2.format(date)

    val sdf3 = SimpleDateFormat("EEEE")
    val day = sdf3.format(date)

    val sdf4 = SimpleDateFormat("HH:mm:ss z")
    val time = sdf4.format(date)
    val departDateModel = DateModel(
        classicDate = currentDate,
        englishData = englishDate,
        flightDOB = flightDob,
        time = time,
        day = day,
        date = date,
        millisecond = date.time
    )

    givenDateModel(
        departDateModel
    )
}


data class LocationDestinationModel(
    var fromAirPortDto: MutableState<AirPortDto?> = mutableStateOf(null),
    var toAirPortDto: MutableState<AirPortDto?> = mutableStateOf(null),
    var departDateModel: MutableState<DateModel?> = mutableStateOf(null),
    var arrivalDateModel: MutableState<DateModel?> = mutableStateOf(null)
)

fun LocationDestinationModel.toTripInfoItem(tripId: Int): TripInfoItem {
    return TripInfoItem(
        origin = fromAirPortDto.value?.aIRPORTCODE,
        destination = toAirPortDto.value?.aIRPORTCODE,
        travelDate = departDateModel.value?.classicDate,
        tripId = tripId
    )
}