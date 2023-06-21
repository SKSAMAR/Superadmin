package com.fintech.superadmin.flight.presentation.travellers.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fintech.superadmin.flight.data.remote.FlightApi
import com.fintech.superadmin.flight.data.remote.dto.passenger.PassengerInfoDto
import com.fintech.superadmin.flight.domain.ScreenState
import com.fintech.superadmin.flight.presentation.common.BaseViewModel
import com.fintech.superadmin.flight.presentation.common.BasicScreenState
import com.fintech.superadmin.flight.presentation.common.BasicSearchView
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class PassengerDialogViewModel
@Inject constructor(private val api: FlightApi) : BaseViewModel<List<PassengerInfoDto>>() {

    init {
        searchPassenger("", "")
    }

    fun searchPassenger(fname: String, lname: String) {
        api.getPassengers(fname = fname, lname = lname)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _state.value = ScreenState(receivedResponse = it.receivableData)
            }, {
                it.printStackTrace()
            })
    }
}


@Composable
fun PassengerDialogContent(
    viewModel: PassengerDialogViewModel = viewModel(),
    passengerSelected: (PassengerInfoDto) -> Unit
) {
    var search by remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = search) {
        viewModel.searchPassenger(fname = search, lname = search)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
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
                    hint = "Search for Passenger",
                    value = search,
                    onValueChange = { search = it },
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
    }
    BasicScreenState(state = viewModel.state) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            viewModel.state.value.receivedResponse?.let {
                items(it) { passenger ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.sdp)
                            .clickable {
                                passengerSelected(passenger)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 5.sdp),
                            imageVector = Icons.Default.Person,
                            contentDescription = "person"
                        )
                        Column {
                            Text(
                                text = "${passenger.fNAME ?: ""} ${passenger.lNAME ?: ""}",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 10.textSdp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = passenger.dOB ?: "",
                                fontSize = 8.textSdp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}