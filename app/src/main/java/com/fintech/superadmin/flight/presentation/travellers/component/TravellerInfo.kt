package com.fintech.superadmin.flight.presentation.travellers.component

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.RadioButtonChecked
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fintech.superadmin.R
import com.fintech.superadmin.flight.data.remote.dto.ssr.SSRDetailsItem
import com.fintech.superadmin.flight.data.remote.dto.ssr.SsrDto
import com.fintech.superadmin.flight.domain.model.TravellerModel
import com.fintech.superadmin.flight.domain.model.nameListTitle
import com.fintech.superadmin.flight.domain.model.titleToNameTitle
import com.fintech.superadmin.flight.presentation.common.ChipCard
import com.fintech.superadmin.flight.presentation.common.CommonTextField
import com.fintech.superadmin.flight.presentation.common.DateDialogSys.showAdjustableDialog
import com.fintech.superadmin.flight.presentation.home.component.toSpecificDepartDates
import com.fintech.superadmin.flight.presentation.travellers.TravellersViewModel
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun TravelInfo(
    viewModel: TravellersViewModel = viewModel(),
    traveller: TravellerModel,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    var isSSRExpanded by remember {
        mutableStateOf(false)
    }


    Column {
        androidx.compose.material.Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(5.sdp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.sdp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Traveller",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 10.textSdp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = traveller.typeFor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 10.textSdp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                FlowRow(
                    modifier = Modifier.padding(vertical = 2.sdp),
                    mainAxisSpacing = 5.sdp,
                    crossAxisSpacing = 5.sdp
                ) {
                    nameListTitle.forEach { titles ->
                        ChipCard(text = titles.title, onChangeState = {
                            traveller.title.value = titles
                        }, isSelectedItem = { traveller.title.value == titles })
                    }
                }

                CommonTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    value = traveller.fname.value,
                    onValueChange = {
                        traveller.fname.value = it
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Characters,
                        imeAction = ImeAction.Next
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                viewModel.displayPassengersList {
                                    traveller.fname.value = it.fNAME ?: ""
                                    traveller.lname.value = it.lNAME ?: ""
                                    traveller.dob.value = it.dOB ?: ""
                                    it.tYPE?.let {
                                        traveller.title.value = it.titleToNameTitle()
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "person"
                            )
                        }
                    },
                    placeholderText = "First Name"
                )

                CommonTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    value = traveller.lname.value,
                    onValueChange = {
                        traveller.lname.value = it
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Characters,
                        imeAction = ImeAction.Next
                    ),
                    placeholderText = "Last Name"
                )

                CommonTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                        .clickable {
                            context.showAdjustableDialog(
                                //minDate = System.currentTimeMillis()
                            ) {
                                toSpecificDepartDates(it) {
                                    traveller.dob.value = it.flightDOB ?: ""
                                }
                            }
                        },
                    value = traveller.dob.value,
                    onValueChange = {

                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Characters,
                        imeAction = ImeAction.Done
                    ),
                    placeholderText = "Date of Birth", enabled = false
                )
                if (traveller.internationalBased) {


                    CommonTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.sdp),
                        value = traveller.passportInfo?.passportNumber?.value ?: "",
                        onValueChange = {
                            traveller.passportInfo?.passportNumber?.value = it
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Characters,
                            imeAction = ImeAction.Next
                        ),
                        placeholderText = "Passport Number"
                    )

                    CommonTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.sdp),
                        value = traveller.passportInfo?.issuingCountry?.value ?: "",
                        onValueChange = {
                            traveller.passportInfo?.issuingCountry?.value = it
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Characters,
                            imeAction = ImeAction.Next
                        ),
                        placeholderText = "Issuing Country"
                    )

                    CommonTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.sdp)
                            .clickable {
                                context.showAdjustableDialog {
                                    toSpecificDepartDates(it) {
                                        traveller.passportInfo?.dateOfExpiry?.value =
                                            it.flightDOB ?: ""
                                    }
                                }
                            },
                        value = traveller.passportInfo?.dateOfExpiry?.value ?: "",
                        onValueChange = {
                            traveller.passportInfo?.dateOfExpiry?.value = it
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Characters,
                            imeAction = ImeAction.Done
                        ),
                        placeholderText = "Date of Expiry", enabled = false
                    )

                    CommonTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.sdp)
                            .clickable {
                                viewModel.getPassengerNationality {
                                    traveller.passportInfo?.nationality?.value = it
                                }
                            },
                        value = traveller.passportInfo?.nationality?.value ?: "",
                        onValueChange = {
                            traveller.passportInfo?.nationality?.value = it
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Characters,
                            imeAction = ImeAction.Done
                        ),
                        placeholderText = "Nationality",
                        enabled = false
                    )


                }
                if (traveller.paxType() != 2) {
                    Row {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    isSSRExpanded = !isSSRExpanded
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Select SSR",
                                fontSize = 9.textSdp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.width(3.sdp))
                            Icon(
                                modifier = Modifier.size(14.sdp),
                                imageVector = if (isSSRExpanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                                contentDescription = "arrow"
                            )
                        }

                    }
                    Column {
                        AnimatedVisibility(isSSRExpanded) {
                            Column {
                                FlowRow(
                                    modifier = Modifier.padding(vertical = 2.sdp),
                                    mainAxisSpacing = 5.sdp,
                                    crossAxisSpacing = 5.sdp
                                ) {
                                    viewModel.ssrOptionsList.forEach { title ->
                                        title?.let {
                                            if (it.contains("Seat", true)) {
                                                Box(
                                                    modifier = Modifier
                                                        .background(
                                                            color = MaterialTheme.colorScheme.primary,
                                                            shape = RoundedCornerShape(3.sdp)
                                                        )
                                                        .clip(shape = RoundedCornerShape(3.sdp))
                                                        .clickable {

                                                        }
                                                ) {
                                                    Text(
                                                        text = it,
                                                        modifier = Modifier.padding(
                                                            horizontal = 5.sdp,
                                                            vertical = 3.sdp
                                                        ),
                                                        color = Color.White,
                                                        fontSize = 8.textSdp
                                                    )
                                                }
                                            } else {
                                                if (authorised(
                                                        paxType = traveller.paxType(),
                                                        ssrType = it,
                                                        ssrs = viewModel.ssrOptions
                                                    )
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .background(
                                                                color = MaterialTheme.colorScheme.primary,
                                                                shape = RoundedCornerShape(3.sdp)
                                                            )
                                                            .clip(shape = RoundedCornerShape(3.sdp))
                                                            .clickable {
                                                                viewModel.displaySSListList(
                                                                    title = it,
                                                                    onReselect = {
                                                                        it?.let {
                                                                            traveller.sSRDetails[title] =
                                                                                it
                                                                        }
                                                                    },
                                                                    isSelected = {
                                                                        traveller.sSRDetails[title] == it
                                                                    }
                                                                )
                                                            }
                                                    ) {
                                                        Text(
                                                            text = it,
                                                            modifier = Modifier.padding(
                                                                horizontal = 5.sdp,
                                                                vertical = 3.sdp
                                                            ),
                                                            color = Color.White,
                                                            fontSize = 8.textSdp
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
            }
        }
    }
}

private fun authorised(paxType: Int, ssrType: String, ssrs: SsrDto?): Boolean {
    val isAuthorised = ssrs?.sSRFlightDetails?.firstOrNull()?.sSRDetails?.any { it ->
        (ssrType.contains(
            it?.sSRTypeName?.trim()?.uppercase() ?: "sdfs",
            true
        ) && it?.applicablePaxTypes?.any { it == paxType } == true)
    } ?: false
    return isAuthorised
}


@Composable
fun SSRSelectionRadio(
    ssrDetails: SSRDetailsItem?,
    onReselect: (SSRDetailsItem?) -> Unit,
    isSelected: (SSRDetailsItem?) -> Boolean
) {
    Column {
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
                        onReselect(ssrDetails)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.padding(end = 10.sdp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        modifier = Modifier.size(12.sdp),
                        imageVector = if (isSelected(ssrDetails)) Icons.Outlined.RadioButtonChecked else Icons.Outlined.RadioButtonUnchecked,
                        contentDescription = "Radio",
                        tint = if (isSelected(ssrDetails)) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                    Spacer(modifier = Modifier.width(5.sdp))
                    Text(
                        text = stringResource(id = R.string.rupee_sign) + "${ssrDetails?.totalAmount ?: ""}",
                        fontSize = 10.textSdp
                    )
                }

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = ssrDetails?.sSRTypeDesc ?: "",
                    fontSize = 10.textSdp,
                    textAlign = TextAlign.End
                )
            }

        }
    }
}