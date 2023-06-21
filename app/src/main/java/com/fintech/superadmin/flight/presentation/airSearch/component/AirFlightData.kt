package com.fintech.superadmin.flight.presentation.airSearch.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fintech.superadmin.R
import com.fintech.superadmin.ui.theme.ButtonYellow
import com.fintech.superadmin.ui.theme.DesGreen
import com.fintech.superadmin.ui.theme.DesRed
import com.fintech.superadmin.flight.data.remote.dto.airSearch.*
import com.fintech.superadmin.flight.domain.model.TravellerModel
import com.fintech.superadmin.flight.util.common.ViewUtils
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AirFlightData(
    item: FlightsItem?, onFlightsItemSelect: (FlightsItem) -> Unit = {}
) {
    item?.let {

        LaunchedEffect(key1 = true) {
            if (item.selectedFareItem.value == null) {
                item.selectedFareItem.value = item.fares?.firstOrNull()
            }
        }

        var isDetailedExpanded by remember {
            mutableStateOf(false)
        }

        /**
        var selectedFareItem by remember {
        mutableStateOf(item.fares?.firstOrNull())
        }**/

        val flightNumber = "${item.airlineCode} - ${
            item.flightNumbers?.substringBefore("-")?.substringBefore(",")
        }"
        androidx.compose.material.Card(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.sdp, vertical = 4.sdp),
            border = BorderStroke(width = .5.dp, color = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(3.sdp),
            onClick = {
                onFlightsItemSelect(it)
            }) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.sdp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .size(40.sdp)
                                    .clip(CircleShape),
                                model = ViewUtils.BASE_FLIGHT_IMAGE + item.segments?.firstOrNull()?.airlineName?.replace(
                                    " ", ""
                                )?.lowercase() + ViewUtils.BASE_IMAGE_TYPE,
                                contentDescription = "flight_image"
                            )
                            Text(
                                text = item.segments?.firstOrNull()?.airlineName ?: "",
                                fontSize = 10.textSdp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Column(
                            modifier = Modifier.padding(start = 8.sdp),
                            horizontalAlignment = Alignment.Start
                        ) {

                            Text(
                                text = "${item.segments?.firstOrNull()?.origin ?: ""} - ${item.segments?.lastOrNull()?.destination ?: ""}",
                                fontSize = 10.textSdp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.SemiBold,
                            )

                            Text(
                                text = "${
                                    item.segments?.firstOrNull()?.departureDateTime?.substringAfter(
                                        " "
                                    ) ?: ""
                                } - ${item.segments?.lastOrNull()?.arrivalDateTime?.substringAfter(" ") ?: ""}",
                                fontSize = 11.textSdp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = buildAnnotatedString {
                                    append("${item.getDuration()}")
                                    if (!item.segments.isNullOrEmpty() && item.segments.size > 1) {
                                        append(", ${item.segments.size - 1} Stop" + if (item.segments.size - 1 > 1) "s" else "")
                                    } else {
                                        append(", Non-Stop")
                                    }
                                    append(", Seats ${item.fares?.firstOrNull()?.seatsAvailable}")
                                },
                                fontSize = 9.textSdp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(14.sdp)
                                        .clip(CircleShape)
                                        .background(color = ButtonYellow),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = item.selectedFareItem.value?.fareDetails?.firstOrNull()?.fareClasses?.firstOrNull()?.classCode
                                            ?: "",
                                        fontSize = 8.textSdp,
                                        maxLines = 2,
                                        color = Color.White,
                                        overflow = TextOverflow.Ellipsis,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                }

                                Spacer(modifier = Modifier.width(3.sdp))
                                Box(
                                    modifier = Modifier
                                        .size(14.sdp)
                                        .clip(CircleShape)
                                        .background(color = if (item.selectedFareItem.value?.refundable == true) DesGreen else DesRed),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (item.selectedFareItem.value?.refundable == true) "R" else "N",
                                        fontSize = 8.textSdp,
                                        maxLines = 2,
                                        color = Color.White,
                                        overflow = TextOverflow.Ellipsis,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                }
                                Spacer(modifier = Modifier.width(3.sdp))
                                Text(
                                    text = flightNumber, fontSize = 10.textSdp
                                )
                            }

                        }

                    }
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = stringResource(id = R.string.rupee_sign) + item.selectedFareItem.value?.fareDetails?.firstOrNull()?.totalAmount,
                            fontSize = 12.textSdp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                        )

                        Box(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(3.sdp)
                                )
                                .clip(shape = RoundedCornerShape(3.sdp))
                                .clickable {
                                    isDetailedExpanded = !isDetailedExpanded
                                }
                        ) {
                            Text(
                                text = "Details",
                                modifier = Modifier.padding(horizontal = 5.sdp, vertical = 3.sdp),
                                color = Color.White,
                                fontSize = 8.textSdp
                            )
                        }

                    }
                }

                AnimatedVisibility(isDetailedExpanded) {

                    var isPricesExpanded by remember {
                        mutableStateOf(false)
                    }
                    var isFareRulesExpanded by remember {
                        mutableStateOf(false)
                    }
                    var isBaggageExpanded by remember {
                        mutableStateOf(false)
                    }

                    Column {
                        Spacer(modifier = Modifier.padding(vertical = 5.sdp))
                        Row {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        isPricesExpanded = !isPricesExpanded
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "Change Price",
                                    fontSize = 9.textSdp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.width(3.sdp))
                                Icon(
                                    modifier = Modifier.size(14.sdp),
                                    imageVector = if (isPricesExpanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                                    contentDescription = "arrow"
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        isFareRulesExpanded = !isFareRulesExpanded
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Fare Rules",
                                    fontSize = 9.textSdp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.width(3.sdp))
                                Icon(
                                    modifier = Modifier.size(14.sdp),
                                    imageVector = if (isFareRulesExpanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                                    contentDescription = "arrow"
                                )
                            }


                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        isBaggageExpanded = !isBaggageExpanded
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = "Baggage",
                                    fontSize = 9.textSdp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.width(3.sdp))
                                Icon(
                                    modifier = Modifier.size(14.sdp),
                                    imageVector = if (isBaggageExpanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                                    contentDescription = "arrow"
                                )
                            }

                        }
                        AnimatedVisibility(isPricesExpanded) {
                            Column {
                                Divider(modifier = Modifier.padding(vertical = 5.sdp))
                                item.fares?.forEach {
                                    it?.let {
                                        AirFlightPricePackage(
                                            faresItem = it,
                                            onReselect = {
                                                item.selectedFareItem.value = it
                                            },
                                            isSelected = {
                                                item.selectedFareItem.value == it
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        AnimatedVisibility(isBaggageExpanded) {
                            Column {
                                Divider(modifier = Modifier.padding(vertical = 5.sdp))

                                Text(
                                    text = "Baggage Summary",
                                    fontSize = 10.textSdp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "Check IN",
                                            fontSize = 10.textSdp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Text(
                                            text = item.selectedFareItem.value?.fareDetails?.firstOrNull()?.freeBaggage?.checkInBaggage
                                                ?: "",
                                            fontSize = 10.textSdp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "Hand",
                                            fontSize = 10.textSdp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Text(
                                            text = item.selectedFareItem.value?.fareDetails?.firstOrNull()?.freeBaggage?.handBaggage
                                                ?: "",
                                            fontSize = 10.textSdp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }
                        AnimatedVisibility(isFareRulesExpanded) {
                            Column {
                                Divider(modifier = Modifier.padding(vertical = 5.sdp))
                                Text(
                                    text = "Fare Rules",
                                    fontSize = 10.textSdp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(
                                        modifier = Modifier.weight(2f)
                                    ) {
                                        Text(text = "FARE BREAKUP SUMMARY", fontSize = 9.textSdp)
                                        Text(text = "Basic Amount", fontSize = 7.textSdp)
                                        Text(text = "YQ Tax", fontSize = 7.textSdp)
                                        Text(text = "Taxes & Others", fontSize = 7.textSdp)
                                        Text(text = "Total Amount", fontSize = 9.textSdp)
                                    }

                                    Column(
                                        modifier = Modifier.weight(1f),
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        Text(
                                            text = "AMOUNT ${item.selectedFareItem.value?.fareDetails?.firstOrNull()?.currencyCode}",
                                            fontSize = 9.textSdp
                                        )
                                        Text(
                                            text = stringResource(id = R.string.rupee_sign) + item.selectedFareItem.value?.fareDetails?.firstOrNull()?.basicAmount,
                                            fontSize = 7.textSdp
                                        )
                                        Text(
                                            text = stringResource(id = R.string.rupee_sign) + item.selectedFareItem.value?.fareDetails?.firstOrNull()?.yQAmount,
                                            fontSize = 7.textSdp
                                        )
                                        Text(
                                            text = stringResource(id = R.string.rupee_sign) + item.selectedFareItem.value?.fareDetails?.firstOrNull()
                                                ?.getTotalTaxes(),
                                            fontSize = 7.textSdp
                                        )
                                        Text(
                                            text = stringResource(id = R.string.rupee_sign) + item.selectedFareItem.value?.fareDetails?.firstOrNull()?.totalAmount,
                                            fontSize = 9.textSdp
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
}


@Composable
fun AirFlightConfirmContent(
    item: FlightsItem?,
    onFlightsItemSelect: (FlightsItem?) -> Unit = {}
) {
    val data = LocalConfiguration.current
    val height = data.screenHeightDp / 2

    Column(
        modifier = Modifier.heightIn(min = 80.sdp, max = height.sdp)
    ) {
        item?.let { flightItem ->
            var selectedFareItem by remember {
                mutableStateOf(item.fares?.firstOrNull()?.fareDetails?.firstOrNull())
            }
            flightItem.segments?.let { segmentItemList ->
                Column {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item {
                            Text(text = "Flight Details", fontSize = 12.textSdp)
                        }
                        items(segmentItemList) {
                            it?.let {
                                AirFlightConfirmData(it)
                            }
                        }

                        item.calculateLayovers()?.let { layover ->
                            item {
                                Text(
                                    text = "Layovers: $layover",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.textSdp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        item {
                            Row(
                                modifier = Modifier.padding(vertical = 10.sdp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 4.sdp)
                                        .border(
                                            width = 1.sdp,
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(4.sdp)
                                        )
                                        .clickable { onFlightsItemSelect(null) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Cancel",
                                        fontSize = 12.textSdp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(vertical = 8.sdp)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 4.sdp)
                                        .border(
                                            width = 1.sdp,
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(4.sdp)
                                        )
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(4.sdp)
                                        )
                                        .clickable {
                                            onFlightsItemSelect(item)
                                        }, contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Confirm",
                                        fontSize = 12.textSdp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.surface,
                                        modifier = Modifier.padding(vertical = 8.sdp)
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


@Composable
fun PreviewConfirmContent(
    item: FlightsItem?,
    adults: ArrayList<TravellerModel>,
    infant: ArrayList<TravellerModel>,
    child: ArrayList<TravellerModel>,
    onFlightsItemSelect: (FlightsItem?) -> Unit = {}
) {
    val data = LocalConfiguration.current
    val height = data.screenHeightDp / 1

    Column(
        modifier = Modifier.heightIn(min = 80.sdp, max = height.sdp)
    ) {
        item?.let { flightItem ->
            flightItem.segments?.let { segmentItemList ->
                Column {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item {
                            Text(text = "Preview", fontSize = 12.textSdp)
                        }
                        items(segmentItemList) {
                            it?.let {
                                AirFlightConfirmData(it)
                            }
                        }
                        item.calculateLayovers()?.let { layover ->
                            item {
                                Text(
                                    text = "Layovers: $layover",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.textSdp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        item {
                            Text(text = "Passengers", fontSize = 12.textSdp)
                        }

                        item {
                            androidx.compose.material.Card(
                                modifier = Modifier.fillMaxWidth(),
                                backgroundColor = androidx.compose.material.MaterialTheme.colors.onSurface
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(2.sdp)
                                ) {
                                    Text(
                                        text = "Passenger Name",
                                        modifier = Modifier.weight(3f),
                                        fontSize = 9.textSdp,
                                        color = androidx.compose.material.MaterialTheme.colors.surface
                                    )
                                    Text(
                                        text = "Gender",
                                        modifier = Modifier.weight(1f),
                                        fontSize = 9.textSdp,
                                        color = androidx.compose.material.MaterialTheme.colors.surface
                                    )
                                    Text(
                                        text = "Adult",
                                        modifier = Modifier.weight(1f),
                                        fontSize = 9.textSdp,
                                        color = androidx.compose.material.MaterialTheme.colors.surface
                                    )
                                }
                            }
                        }

                        items(adults) { adult ->
                            androidx.compose.material.Card(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(2.sdp)
                                ) {
                                    Text(
                                        text = "${adult.fname.value ?: ""} ${adult.lname.value}",
                                        modifier = Modifier.weight(3f),
                                        fontSize = 9.textSdp,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = adult.typeFor,
                                        modifier = Modifier.weight(1f),
                                        fontSize = 9.textSdp
                                    )
                                    Text(
                                        text = adult.genderName(),
                                        modifier = Modifier.weight(1f),
                                        fontSize = 9.textSdp
                                    )
                                }
                            }
                        }

                        items(infant) { adult ->
                            androidx.compose.material.Card(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(2.sdp)
                                ) {
                                    Text(
                                        text = "${adult.fname.value ?: ""} ${adult.lname.value}",
                                        modifier = Modifier.weight(3f),
                                        fontSize = 9.textSdp,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = adult.typeFor,
                                        modifier = Modifier.weight(1f),
                                        fontSize = 9.textSdp
                                    )
                                    Text(
                                        text = adult.genderName(),
                                        modifier = Modifier.weight(1f),
                                        fontSize = 9.textSdp
                                    )
                                }
                            }
                        }

                        items(child) { adult ->
                            androidx.compose.material.Card(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(2.sdp)
                                ) {
                                    Text(
                                        text = "${adult.fname.value ?: ""} ${adult.lname.value}",
                                        modifier = Modifier.weight(3f),
                                        fontSize = 9.textSdp,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = adult.typeFor,
                                        modifier = Modifier.weight(1f),
                                        fontSize = 9.textSdp
                                    )
                                    Text(
                                        text = adult.genderName(),
                                        modifier = Modifier.weight(1f),
                                        fontSize = 9.textSdp
                                    )
                                }
                            }
                        }




                        item {
                            Row(
                                modifier = Modifier.padding(vertical = 10.sdp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 4.sdp)
                                        .border(
                                            width = 1.sdp,
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(4.sdp)
                                        )
                                        .clickable { onFlightsItemSelect(null) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Cancel",
                                        fontSize = 12.textSdp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(vertical = 8.sdp)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 4.sdp)
                                        .border(
                                            width = 1.sdp,
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(4.sdp)
                                        )
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(4.sdp)
                                        )
                                        .clickable {
                                            onFlightsItemSelect(item)
                                        }, contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Confirm",
                                        fontSize = 12.textSdp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.surface,
                                        modifier = Modifier.padding(vertical = 8.sdp)
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


@Composable
fun AirFlightConfirmData(
    segmentsItem: SegmentsItem
) {
    val flightNumber = "${segmentsItem.airlineCode} - ${
        segmentsItem.flightNumber?.substringBefore("-")?.substringBefore(",")
    }"

    androidx.compose.material.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.sdp, vertical = 4.sdp),
        border = BorderStroke(
            width = .5.dp, color = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(3.sdp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.sdp, vertical = 2.sdp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Stock ${segmentsItem.airlineCode}", fontSize = 8.textSdp)
                    Text(
                        text = segmentsItem.origin ?: "",
                        fontSize = 16.textSdp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = segmentsItem.departureDateTime?.substringBefore(" ") ?: "",
                        fontSize = 8.textSdp
                    )
                    Text(
                        text = segmentsItem.departureDateTime?.substringAfter(" ") ?: "",
                        fontSize = 8.textSdp
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    AsyncImage(
                        modifier = Modifier
                            .size(30.sdp)
                            .clip(CircleShape),
                        model = ViewUtils.BASE_FLIGHT_IMAGE + segmentsItem.airlineName?.replace(
                            " ", ""
                        )?.lowercase() + ViewUtils.BASE_IMAGE_TYPE,
                        contentDescription = "flight_image"
                    )
                    Text(
                        text = segmentsItem.getDuration() ?: "",
                        fontSize = 12.textSdp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold
                    )

                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = flightNumber, fontSize = 8.textSdp
                    )
                    Text(
                        text = segmentsItem.destination ?: "",
                        fontSize = 16.textSdp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = segmentsItem.arrivalDateTime?.substringBefore(" ") ?: "",
                        fontSize = 8.textSdp
                    )
                    Text(
                        text = segmentsItem.arrivalDateTime?.substringAfter(" ") ?: "",
                        fontSize = 8.textSdp
                    )
                }

            }
        }
    }
}


@Composable
fun AirFlightPricePackage(
    faresItem: FaresItem,
    onReselect: (FaresItem) -> Unit,
    isSelected: (FaresItem) -> Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.sdp, vertical = 2.sdp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onReselect(faresItem)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier.size(12.sdp),
                    imageVector = if (isSelected(faresItem)) Icons.Outlined.RadioButtonChecked else Icons.Outlined.RadioButtonUnchecked,
                    contentDescription = "Radio",
                    tint = if (isSelected(faresItem)) MaterialTheme.colorScheme.primary else Color.Gray
                )
                Spacer(modifier = Modifier.width(5.sdp))
                Text(
                    text = stringResource(id = R.string.rupee_sign) + "${faresItem.fareDetails?.firstOrNull()?.basicAmount}",
                    fontSize = 10.textSdp
                )
            }

            Text(
                text = "${faresItem.fareDetails?.firstOrNull()?.fareClasses?.firstOrNull()?.classDesc}",
                fontSize = 10.textSdp
            )
        }

    }
}


@Composable
fun CustomCheckBox(
    isChecked: Boolean? = false,
    title: String,
    count: Int? = null,
    onCheckChanges: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.sdp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) {
                onCheckChanges()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Icon(
            imageVector = if (isChecked == true) Icons.Outlined.CheckBox else Icons.Outlined.CheckBoxOutlineBlank,
            contentDescription = "CheckBox",
            tint = if (isChecked == true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(10.sdp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 8.textSdp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            count?.let {
                Text(
                    text = it.toString(),
                    fontSize = 8.textSdp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
