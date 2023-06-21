package com.fintech.superadmin.flight.presentation.airports.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fintech.superadmin.flight.data.remote.dto.AirPortDto
import com.fintech.superadmin.flight.presentation.airports.AirportsViewModel
import com.fintech.superadmin.flight.presentation.common.BasicScreenState
import com.fintech.superadmin.flight.presentation.common.BasicSearchView
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp

@Composable
fun AirportDialogContent(
    viewModel: AirportsViewModel,
    onSelectedAirport: (AirPortDto) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(5.sdp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(.9f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                BasicSearchView(
                    hint = "Search for Airports",
                    value = viewModel.search,
                    onValueChange = { viewModel.search = it },
                    maxLength = 100,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.None
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Spacer(modifier = Modifier.height(5.sdp))
        BasicScreenState(
            modifier = Modifier.fillMaxSize(),
            state = viewModel.state
        ) {
            viewModel.state.value.receivedResponse?.let { list ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.sdp)
                ) {
                    items(list) {
                        it?.let {
                            AirportData(
                                data = it,
                                onSelectedAirport = {
                                    onSelectedAirport(it)
                                }
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
private fun AirportData(
    data: AirPortDto,
    onSelectedAirport: (AirPortDto) -> Unit
) {

    Box(
        modifier = Modifier
            .padding(horizontal = 1.sdp, vertical = 4.sdp)
            .fillMaxWidth()
            .border(width = .5.dp, color = Color.Gray, shape = RoundedCornerShape(5.sdp))
            .clickable {
                onSelectedAirport(data)
            }
            .clip(shape = RoundedCornerShape(5.sdp))
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.sdp, horizontal = 8.sdp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {

                Text(
                    text = buildAnnotatedString {
                        if (!data.cITY.isNullOrEmpty()) {
                            append(data.cITY)
                        }
                        if (!data.cOUNTRY.isNullOrEmpty()) {
                            append(", " + data.cOUNTRY)
                        }
                    },
                    fontSize = 8.textSdp,
                    fontWeight = FontWeight.SemiBold,
                    
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray
                )

                Text(
                    text = data.aIRPORT ?: "",
                    fontSize = 7.textSdp,
                    fontWeight = FontWeight.SemiBold,
                    
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray
                )
            }
            Box(
                modifier = Modifier
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(8.sdp)
                    )
                    .border(
                        width = 1.sdp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(10.sdp)
                    )
                    .clip(shape = RoundedCornerShape(8.sdp))
            ) {
                Text(
                    text = data.aIRPORTCODE ?: "",
                    fontSize = 7.textSdp,
                    
                    modifier = Modifier.padding(vertical = 2.sdp, horizontal = 8.sdp),
                    color = MaterialTheme.colorScheme.primary
                )
            }

        }
    }
}