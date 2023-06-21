package com.fintech.superadmin.flight.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.fintech.superadmin.flight.domain.state.SharedState.adult
import com.fintech.superadmin.flight.domain.state.SharedState.child
import com.fintech.superadmin.flight.domain.state.SharedState.infant
import com.fintech.superadmin.flight.domain.state.SharedState.selectedSeatClass
import com.fintech.superadmin.flight.domain.state.SharedState.selectedTripValue
import com.fintech.superadmin.flight.domain.state.SharedState.tripTypeList
import com.fintech.superadmin.flight.domain.state.seatClassesList
import com.fintech.superadmin.flight.presentation.airlines.AirlinesViewModel
import com.fintech.superadmin.flight.presentation.airports.AirportsViewModel
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightHomeActivity : ComponentActivity() {

    private val airlinesViewModel by viewModels<AirlinesViewModel>()
    private val airportsViewModel by viewModels<AirportsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firstInit(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colorScheme.surface) {
                    HomeScreen(
                        airlinesViewModel = airlinesViewModel,
                        airportsViewModel = airportsViewModel,
                        onBackPressedDispatcher = onBackPressedDispatcher
                    )
                }
            }
        }
    }


    private fun firstInit(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            selectedTripValue = tripTypeList.first()
            selectedSeatClass = seatClassesList.first()
            adult = 1
            child = 0
            infant = 0
        }
    }

}