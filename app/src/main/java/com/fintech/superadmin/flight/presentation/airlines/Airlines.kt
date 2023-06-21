package com.fintech.superadmin.flight.presentation.airlines

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fintech.superadmin.flight.data.remote.dto.AirlineDto
import com.fintech.superadmin.flight.domain.state.selectedAirLines
import com.fintech.superadmin.flight.presentation.airlines.AirlinesViewModel
import com.fintech.superadmin.flight.presentation.common.BasicScreenState
import com.fintech.superadmin.flight.presentation.common.BasicSearchView
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp


@Composable
fun AirlinesDialogContent(
    viewModel: AirlinesViewModel = viewModel(),
    onReset: () -> Unit
) {
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
                hint = "Search for Airlines",
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
    Spacer(modifier = Modifier.height(8.sdp))
    Column {
        Column(modifier = Modifier.weight(1f)) {
            AirlinesContent()
        }
        if (selectedAirLines.isNotEmpty()) {
            Spacer(modifier = Modifier.height(5.sdp))
            Text(
                modifier = Modifier.padding(end = 10.sdp).clickable {
                        selectedAirLines.clear()
                        onReset()
                    }.align(Alignment.End),
                text = "Reset",
                fontSize = 12.textSdp
            )
        }
    }
}


@Composable
fun AirlinesContent(
    viewModel: AirlinesViewModel = viewModel()
) {
    BasicScreenState(state = viewModel.state) {
        viewModel.state.value.receivedResponse?.let { list ->
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(list) {
                    AirlineData(data = it)
                }
            }
        }
    }
}

@Composable
private fun AirlineData(
    data: AirlineDto,
    modifier: Modifier = Modifier,
) {
    var isSelectedAirLine by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isSelectedAirLine) {
        isSelectedAirLine = selectedAirLines.contains(data.iD ?: "")
    }


    Box(
        modifier = Modifier
            .padding(horizontal = 10.sdp, vertical = 4.sdp)
            .fillMaxWidth()
            .border(
                width = .5.dp,
                color = if (isSelectedAirLine) MaterialTheme.colorScheme.primary else Color.Gray,
                shape = RoundedCornerShape(5.sdp)
            )
            .clickable {
                if (selectedAirLines.contains(data.iD ?: "")) {
                    selectedAirLines.remove(data.iD ?: "")
                    isSelectedAirLine = false
                } else {
                    selectedAirLines[data.iD ?: ""] = data
                    isSelectedAirLine = true
                }
            }
            .clip(shape = RoundedCornerShape(5.sdp))
    ) {
        Row(
            modifier = Modifier
                .padding(10.sdp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier.padding(end = 5.sdp),
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "done",
                    tint = if (isSelectedAirLine) MaterialTheme.colorScheme.primary else Color.Gray
                )

                Text(
                    text = data.airlinesName ?: "",
                    fontSize = 11.textSdp,
                    fontWeight = FontWeight.SemiBold,
                    
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = if (isSelectedAirLine) MaterialTheme.colorScheme.primary else Color.Gray
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
                        color = if (isSelectedAirLine) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                            0.35f
                        ),
                        shape = RoundedCornerShape(10.sdp)
                    )
                    .clip(shape = RoundedCornerShape(8.sdp))
            ) {
                Text(
                    text = data.airlineCode ?: "",
                    fontSize = 8.textSdp,
                    modifier = Modifier.padding(vertical = 3.sdp, horizontal = 12.sdp),
                    color = if (isSelectedAirLine) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }

        }
    }
}