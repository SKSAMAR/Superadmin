package com.fintech.superadmin.flight.presentation.airSearch.component

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fintech.superadmin.flight.domain.state.SharedState.multiCityTripList
import com.fintech.superadmin.flight.presentation.airSearch.AirSearchViewModel
import com.fintech.superadmin.flight.util.common.ViewUtils.displayText
import com.fintech.superadmin.flight.util.common.ViewUtils.toDateModel
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun SingleTripScreen(
    viewModel: AirSearchViewModel = viewModel(),
    context: Context = LocalContext.current
) {
    Column {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarCompose()
        }

        viewModel.state.value.receivedResponse?.tripDetails?.firstOrNull()?.flights?.let { flight ->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                if (flight.isNotEmpty()) {
                    items(viewModel.flights) {
                        AirFlightData(it, onFlightsItemSelect = {
                            viewModel.selectFlight(context = context, flightsItem = it)
                        })
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarCompose(
    viewModel: AirSearchViewModel = viewModel()
) {
    val state = rememberWeekCalendarState(
        startDate = viewModel.startDate,
        endDate = viewModel.endDate,
        firstVisibleWeekDate = viewModel.currentDate,
    )
    LaunchedEffect(key1 = true) {
        viewModel.selection.value?.let {
            state.animateScrollToWeek(it)
        }
    }

    WeekCalendar(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(vertical = 4.sdp),
        state = state,
        calendarScrollPaged = false,
        dayContent = { day ->
            if (day.date >= LocalDate.now()) {

                Day(day.date, selected = viewModel.selection.value == day.date) {
                    multiCityTripList.firstOrNull()?.departDateModel?.value = it.toDateModel()
                    viewModel.selection.value = it

                    Log.d(
                        "CHECK_CURRENT_1",
                        "SELECTED == ${viewModel.selection.value}  inTop === ${multiCityTripList.firstOrNull()?.departDateModel?.value}"
                    )
                }
            }
        },
    )
}

@RequiresApi(Build.VERSION_CODES.O)
private val dateFormatter = DateTimeFormatter.ofPattern("dd")

@RequiresApi(Build.VERSION_CODES.O)
private val yearFormatter = DateTimeFormatter.ofPattern("yy")

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Day(
    date: LocalDate,
    selected: Boolean = false,
    onClick: (LocalDate) -> Unit = {},
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.sdp
    Box(
        modifier = Modifier
            .width(screenWidth / 9)
            .padding(1.sdp)
            .clip(RoundedCornerShape(5.sdp))
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .border(
                shape = RoundedCornerShape(5.sdp),
                width = if (selected) 1.sdp else 0.sdp,
                color = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
            )
            .wrapContentHeight()
            .clickable { onClick(date) },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 1.sdp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(1.sdp),
        ) {
            Text(
                text = date.month.displayText(),
                fontSize = 7.textSdp,
                fontWeight = FontWeight.Normal,
            )
            androidx.compose.material.Text(
                text = dateFormatter.format(date),
                fontSize = 9.textSdp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = date.dayOfWeek.displayText(),
                fontSize = 7.textSdp,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}
