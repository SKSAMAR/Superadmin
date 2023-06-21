package com.fintech.superadmin.flight.presentation.travellers

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.fintech.superadmin.flight.data.remote.FlightApi
import com.fintech.superadmin.flight.data.remote.dto.airSearch.FlightsItem
import com.fintech.superadmin.flight.data.remote.dto.passenger.PassengerInfoDto
import com.fintech.superadmin.flight.data.remote.dto.seatMap.SeatDetailsItem
import com.fintech.superadmin.flight.data.remote.dto.seatMap.SeatSegmentsItem
import com.fintech.superadmin.flight.data.remote.dto.seatMap.getSeatBorderColor
import com.fintech.superadmin.flight.data.remote.dto.seatMap.getSeatColor
import com.fintech.superadmin.flight.data.remote.dto.ssr.SSRDetailsItem
import com.fintech.superadmin.flight.data.remote.dto.ssr.SsrDto
import com.fintech.superadmin.flight.domain.model.TravellerModel
import com.fintech.superadmin.flight.domain.model.TravellerPassportModel
import com.fintech.superadmin.flight.domain.model.toTravellerSendData
import com.fintech.superadmin.flight.domain.state.SharedState
import com.fintech.superadmin.flight.domain.state.SharedState.adult
import com.fintech.superadmin.flight.domain.state.SharedState.child
import com.fintech.superadmin.flight.domain.state.SharedState.infant
import com.fintech.superadmin.flight.presentation.airSearch.component.PreviewConfirmContent
import com.fintech.superadmin.flight.presentation.common.BaseViewModel
import com.fintech.superadmin.flight.presentation.common.ContentComposableDialog
import com.fintech.superadmin.flight.presentation.common.ContentComposableDialogBig
import com.fintech.superadmin.flight.presentation.common.ContentLargeDialog
import com.fintech.superadmin.flight.presentation.travellers.component.PassengerDialogContent
import com.fintech.superadmin.flight.presentation.travellers.component.SSRSelectionRadio
import com.fintech.superadmin.flight.util.Accessable
import com.fintech.superadmin.flight.util.common.ViewUtils.showToast
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class TravellersViewModel
@Inject constructor(
    private val api: FlightApi
) : BaseViewModel<List<PassengerInfoDto>>() {

    var flight: FlightsItem? = null
    var searchKey: String? = null
    var ssrOptionsList = mutableSetOf<String?>()
    var ssrOptions: SsrDto? = null
    val adultTravellers = ArrayList<TravellerModel>()
    val childTravellers = ArrayList<TravellerModel>()
    val infantTravellers = ArrayList<TravellerModel>()
    var nationalities = emptyList<String>()
    var fetchedSeats: List<SeatSegmentsItem?>? = null
    val seatsSelected: MutableMap<Int, SeatDetailsItem> = mutableStateMapOf()
    var seatSelectionDialog by mutableStateOf(false)
    var seatBoxDialog by mutableStateOf(false)


    init {
        initalize()
        ssrOptionsList.add("SEAT")
        ssrOptionsList.add("BAGGAGE")
        ssrOptionsList.add("BAGOUTFIRST")
        ssrOptionsList.add("FASTFORWARD")
        ssrOptionsList.add("MEALS")
    }

    private fun initalize() {
        nationalities = getNationality()
        var passportInfo: TravellerPassportModel? = null
        var isInternation = false
        SharedState.multiCityTripList.forEach {
            it.fromAirPortDto.value?.cOUNTRYCODE?.trim()?.let {
                if (it.uppercase() != "IN") {
                    isInternation = true
                }
            }
            it.toAirPortDto.value?.cOUNTRYCODE?.trim()?.let {
                if (it.uppercase() != "IN") {
                    isInternation = true
                }
            }
        }

        if (isInternation) {
            passportInfo = TravellerPassportModel()
        }

        for (i in 0 until adult) {
            adultTravellers.add(
                TravellerModel(
                    id = i + 1,
                    typeFor = "Adult",
                    internationalBased = isInternation,
                    passportInfo = passportInfo
                )
            )
        }

        for (i in 0 until child) {
            childTravellers.add(
                TravellerModel(
                    id = adultTravellers.size + i + 1,
                    typeFor = "Child",
                    internationalBased = isInternation,
                    passportInfo = passportInfo
                )
            )
        }

        for (i in 0 until infant) {
            infantTravellers.add(
                TravellerModel(
                    id = childTravellers.size + adultTravellers.size + i + 1,
                    typeFor = "Infant",
                    internationalBased = isInternation,
                    passportInfo = passportInfo
                )
            )
        }
    }


    fun getSSR() {
        val set = mutableSetOf<String?>()
        displayDialogLoading("Please.. wait")
        api.getSSR(
            fareid = flight?.fares?.firstOrNull()?.fareId ?: "",
            fkey = flight?.flightKey ?: "",
            skey = searchKey ?: ""
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                dismissDialog()
                ssrOptions = it
                if (it.responseHeader?.errorCode == "0000" && it.responseHeader.errorDesc == "SUCCESS") {
                    it.sSRFlightDetails?.firstOrNull()?.sSRDetails?.forEach { ssrDetailsItems ->
                        val contains = ssrOptionsList.any {
                            it?.contains(
                                ssrDetailsItems?.sSRTypeName?.trim()?.uppercase() ?: "sdfsdfdsf",
                                true
                            ) == true
                        }
                        if (contains) {
                            set.add(ssrDetailsItems?.sSRTypeName?.trim()?.uppercase())
                        }
                    }
                    if (set.isEmpty()) {
                        set.add("SEAT")
                    }
                    ssrOptionsList = set
                } else {
                    //displayDialogFailure(it.toString())
                }
            }, {
                displayDialogFailure(it.localizedMessage ?: "Some Error")
            })
    }


    fun displayPassengersList(
        passengerSelected: (PassengerInfoDto) -> Unit
    ) {
        _state.value.content = {
            ContentComposableDialog(onDismissRequest = {
                _state.value.baseDialogVisible.value = false
            }, onAccept = {
                _state.value.baseDialogVisible.value = false
            }, onCancel = {
                _state.value.baseDialogVisible.value = false
            }, content = {
                PassengerDialogContent {
                    _state.value.baseDialogVisible.value = false
                    passengerSelected.invoke(it)
                }
            })
        }
        _state.value.baseDialogVisible.value = true
    }

    fun getPassengerNationality(
        nationalityGet: (String) -> Unit
    ) {
        _state.value.content = {
            ContentComposableDialog(onDismissRequest = {
                _state.value.baseDialogVisible.value = false
            }, onAccept = {
                _state.value.baseDialogVisible.value = false
            }, onCancel = {
                _state.value.baseDialogVisible.value = false
            }, content = {

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(nationalities) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    _state.value.baseDialogVisible.value = false
                                    nationalityGet.invoke(it)
                                }
                        ) {
                            Text(
                                text = it,
                                fontSize = 12.textSdp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(vertical = 4.sdp),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            })
        }
        _state.value.baseDialogVisible.value = true
    }


    fun displaySSListList(
        title: String,
        onReselect: (SSRDetailsItem?) -> Unit,
        isSelected: (SSRDetailsItem?) -> Boolean
    ) {
        _state.value.content = {
            ContentComposableDialog(onDismissRequest = {
                _state.value.baseDialogVisible.value = false
            }, onAccept = {
                _state.value.baseDialogVisible.value = false
            }, onCancel = {
                _state.value.baseDialogVisible.value = false
            }, content = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.sdp)
                ) {
                    item {
                        Text(text = title, fontSize = 12.textSdp, fontWeight = FontWeight.SemiBold)
                    }
                    ssrOptions?.sSRFlightDetails?.firstOrNull()?.sSRDetails?.let { list ->
                        items(list) { details ->
                            if (details?.sSRTypeName?.trim()?.uppercase()
                                    ?.contains(title, true) == true
                            ) {
                                SSRSelectionRadio(
                                    ssrDetails = details,
                                    onReselect = onReselect,
                                    isSelected = isSelected
                                )
                            }
                        }
                    }
                }
            })
        }
        _state.value.baseDialogVisible.value = true
    }


    private fun displaySeatsList(
        context: Context,
        seatSegmentsItem: List<SeatSegmentsItem?>,
        travellerModel: TravellerModel
    ) {
        _state.value.content = {
            ContentLargeDialog(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.sdp, vertical = 10.sdp),
                onDismissRequest = {
                    _state.value.baseDialogVisible.value = false
                }, onAccept = {
                    _state.value.baseDialogVisible.value = false
                }, onCancel = {
                    _state.value.baseDialogVisible.value = false
                }, content = {
                    seatSegmentsItem.firstOrNull()?.seatRow?.let { seatRows ->
                        LazyColumn(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(seatRows) {
                                Row(
                                    modifier = Modifier
                                        .padding(5.sdp)
                                        .weight(weight = 1f),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    it?.seatDetails?.let {

                                        it.forEach { seatDetails ->
                                            if (seatDetails?.sSRStatus != 0) {
                                                seatContent(
                                                    context = context,
                                                    travellerModel = travellerModel,
                                                    seatDetails = seatDetails
                                                )
                                            } else {
                                                Spacer(
                                                    modifier = Modifier
                                                        .padding(horizontal = 5.sdp)
                                                        .weight(weight = 1f)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                })
        }
        _state.value.baseDialogVisible.value = true
    }

    fun selectSeatsCallValidate(): Boolean {
        adultTravellers.forEach { traveller ->
            if (traveller.title.value?.title?.trim().isNullOrEmpty()) {
                displayFailureMessage("Select a valid Title")
                return false
            } else if (traveller.fname.value.trim().isEmpty()) {
                displayFailureMessage("Provide a valid first name")
                return false
            } else if (traveller.lname.value.trim().isEmpty()) {
                displayFailureMessage("Provide a valid last name")
                return false
            } else if (traveller.typeFor.contains("infant", true) && traveller.dob.value.trim()
                    .isEmpty()
            ) {
                displayFailureMessage("Provide a valid Date of Birth")
                return false
            } else if (traveller.internationalBased && traveller.dob.value.trim().isEmpty()) {
                displayFailureMessage("Provide a valid Date of Birth")
                return false
            } else if (traveller.internationalBased && traveller.passportInfo?.passportNumber?.value?.trim()
                    .isNullOrEmpty()
            ) {
                displayFailureMessage("Provide a valid Passport Number for ${traveller.fname.value} ${traveller.lname.value}")
                return false
            } else if (traveller.internationalBased && traveller.passportInfo?.issuingCountry?.value?.trim()
                    .isNullOrEmpty()
            ) {
                displayFailureMessage("Provide a valid Passport Issuing Country for ${traveller.fname.value} ${traveller.lname.value}")
                return false
            } else if (traveller.internationalBased && traveller.passportInfo?.dateOfExpiry?.value?.trim()
                    .isNullOrEmpty()
            ) {
                displayFailureMessage("Provide a valid Passport Date of Expiry for ${traveller.fname.value} ${traveller.lname.value}")
                return false
            } else if (traveller.internationalBased && traveller.passportInfo?.nationality?.value?.trim()
                    .isNullOrEmpty()
            ) {
                displayFailureMessage("Provide a valid Passport Nationality for ${traveller.fname.value} ${traveller.lname.value}")
                return false
            }

        }
        infantTravellers.forEach { traveller ->
            if (traveller.title.value?.title?.trim().isNullOrEmpty()) {
                displayFailureMessage("Select a valid Title")
                return false
            } else if (traveller.fname.value.trim().isEmpty()) {
                displayFailureMessage("Provide a valid first name")
                return false
            } else if (traveller.lname.value.trim().isEmpty()) {
                displayFailureMessage("Provide a valid last name")
                return false
            } else if (traveller.typeFor.contains("infant", true) && traveller.dob.value.trim()
                    .isEmpty()
            ) {
                displayFailureMessage("Provide a valid Date of Birth")
                return false
            } else if (traveller.internationalBased && traveller.dob.value.trim().isEmpty()) {
                displayFailureMessage("Provide a valid Date of Birth")
                return false
            } else if (traveller.internationalBased && traveller.passportInfo?.passportNumber?.value?.trim()
                    .isNullOrEmpty()
            ) {
                displayFailureMessage("Provide a valid Passport Number for ${traveller.fname.value} ${traveller.lname.value}")
                return false
            } else if (traveller.internationalBased && traveller.passportInfo?.dateOfExpiry?.value?.trim()
                    .isNullOrEmpty()
            ) {
                displayFailureMessage("Provide a valid Passport Date of Expiry for ${traveller.fname.value} ${traveller.lname.value}")
                return false
            } else if (traveller.internationalBased && traveller.passportInfo?.nationality?.value?.trim()
                    .isNullOrEmpty()
            ) {
                displayFailureMessage("Provide a valid Passport Nationality for ${traveller.fname.value} ${traveller.lname.value}")
                return false
            } else if (traveller.internationalBased && traveller.passportInfo?.issuingCountry?.value?.trim()
                    .isNullOrEmpty()
            ) {
                displayFailureMessage("Provide a valid Passport Issuing country for ${traveller.fname.value} ${traveller.lname.value}")
                return false
            }

        }
        childTravellers.forEach { traveller ->
            if (traveller.title.value?.title?.trim().isNullOrEmpty()) {
                displayFailureMessage("Select a valid Title")
                return false
            } else if (traveller.fname.value.trim().isEmpty()) {
                displayFailureMessage("Provide a valid first name")
                return false
            } else if (traveller.lname.value.trim().isEmpty()) {
                displayFailureMessage("Provide a valid last name")
                return false
            } else if (traveller.typeFor.contains("infant", true) && traveller.dob.value.trim()
                    .isEmpty()
            ) {
                displayFailureMessage("Provide a valid Date of Birth")
                return false
            } else if (traveller.internationalBased && traveller.dob.value.trim().isEmpty()) {
                displayFailureMessage("Provide a valid Date of Birth")
                return false
            } else if (traveller.internationalBased && traveller.passportInfo?.passportNumber?.value?.trim()
                    .isNullOrEmpty()
            ) {
                displayFailureMessage("Provide a valid Passport Number for ${traveller.fname.value} ${traveller.lname.value}")
                return false
            } else if (traveller.internationalBased && traveller.passportInfo?.dateOfExpiry?.value?.trim()
                    .isNullOrEmpty()
            ) {
                displayFailureMessage("Provide a valid Passport Date of Expiry for ${traveller.fname.value} ${traveller.lname.value}")
                return false
            } else if (traveller.internationalBased && traveller.passportInfo?.nationality?.value?.trim()
                    .isNullOrEmpty()
            ) {
                displayFailureMessage("Provide a valid Passport Nationality for ${traveller.fname.value} ${traveller.lname.value}")
                return false
            } else if (traveller.internationalBased && traveller.passportInfo?.issuingCountry?.value?.trim()
                    .isNullOrEmpty()
            ) {
                displayFailureMessage("Provide a valid Passport Issuing country for ${traveller.fname.value} ${traveller.lname.value}")
                return false
            }

        }
        return true
    }

    fun selectSeatsCall(context: Context, travellerModel: TravellerModel) {
        if (Accessable.isAccessable()) {
            if (fetchedSeats != null) {
                fetchedSeats?.let {
                    displaySeatsList(
                        context = context,
                        seatSegmentsItem = it,
                        travellerModel = travellerModel
                    )
                }

            } else {
                val allPassengers = ArrayList<TravellerModel>()
                allPassengers.addAll(adultTravellers)
                allPassengers.addAll(infantTravellers)
                allPassengers.addAll(childTravellers)
                val info = allPassengers.map { s -> s.toTravellerSendData() }
                val json = Gson().toJson(info)
                /**
                val intent = Intent(context, SeatSelectionActivity::class.java)
                intent.putExtra(Constant.FlightsItem, flight)
                intent.putExtra(Constant.SearchKey, searchKey)
                intent.putExtra(Constant.PassengerJson, json)
                context.startActivity(intent)
                 **/
                displayDialogLoading("Please wait..")
                api.getSeats(
                    fareid = flight?.fares?.firstOrNull()?.fareId ?: "",
                    fkey = flight?.flightKey ?: "",
                    skey = searchKey ?: "",
                    seatData = json
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        dismissDialog()
                        if (it.responseHeader?.errorCode == "0000" && it.responseHeader.errorDesc == "SUCCESS") {
                            it.airSeatMaps?.firstOrNull()?.seatSegments?.let {
                                fetchedSeats = it
                                displaySeatsList(
                                    context = context,
                                    seatSegmentsItem = it,
                                    travellerModel = travellerModel
                                )
                            }
                        } else {
                            displayDialogFailure(it.responseHeader?.errorDesc ?: "Some Error")
                        }
                    }, {
                        displayDialogFailure(it.localizedMessage ?: "Some Error")
                    })

            }

            /***/
        }
    }

    private fun getNationality(): List<String> {
        val list = ArrayList<String>()
        list.add("Afghanistan")//Afghanistan
        list.add("Albania")//Albania
        list.add("Algeria")//Algeria
        list.add("Andorra")//Andorra
        list.add("Angola")//Angola
        list.add("Antigua and Barbuda")//Antigua and Barbuda
        list.add("Argentina")//Argentina
        list.add("Armenia")//Armenia
        list.add("Australia")//Australia
        list.add("Austria")//Austria
        list.add("Azerbaijan")//Azerbaijan
        list.add("The Bahamas")//The Bahamas
        list.add("Bahrain")//Bahrain
        list.add("Bangladesh")//Bangladesh
        list.add("Barbados")//Barbados
        list.add("Belarus")//Belarus
        list.add("Belgium")//Belgium
        list.add("Belize")//Belize
        list.add("Benin")//Benin
        list.add("Bhutan")//Bhutan
        list.add("Bolivia")//Bolivia
        list.add("Bosnia and Herzegovina")//Bosnia and Herzegovina
        list.add("Botswana")//Botswana
        list.add("Brazil")//Brazil
        list.add("Brunei")//Brunei
        list.add("Bulgaria")//Bulgaria
        list.add("Burkina Faso")//Burkina Faso
        list.add("Burundi")//Burundi
        list.add("Cabo Verde")//Cabo Verde
        list.add("Cambodia")//Cambodia
        list.add("Cameroon")//Cameroon
        list.add("Canada")//Canada
        list.add("Central African Republic")//Central African Republic
        list.add("Chad")//Chad
        list.add("Chile")//Chile
        list.add("China")//China
        list.add("Colombia")//Colombia
        list.add("Comoros")//Comoros
        list.add("Congo, Democratic Republic of the")//Congo, Democratic Republic of the
        list.add("Congo, Republic of the")//Congo, Republic of the
        list.add("Costa Rica")//Costa Rica
        list.add("Côte d’Ivoire")//Côte d’Ivoire
        list.add("Croatia")//Croatia
        list.add("Cuba")//Cuba
        list.add("Cyprus")//Cyprus
        list.add("Czech Republic")//Czech Republic
        list.add("Denmark")//Denmark
        list.add("Djibouti")//Djibouti
        list.add("Dominica")//Dominica
        list.add("Dominican Republic")//Dominican Republic
        list.add("East Timor (Timor-Leste)")//East Timor (Timor-Leste)
        list.add("Ecuador")//Ecuador
        list.add("Egypt")//Egypt
        list.add("El Salvador")//El Salvador
        list.add("Equatorial Guinea")//Equatorial Guinea
        list.add("Eritrea")//Eritrea
        list.add("Estonia")//Estonia
        list.add("Eswatini")//Eswatini
        list.add("Ethiopia")//Ethiopia
        list.add("Fiji")//Fiji
        list.add("Finland")//Finland
        list.add("France")//France
        list.add("Gabon")//Gabon
        list.add("The Gambia")//The Gambia
        list.add("Georgia")//Georgia
        list.add("Germany")//Germany
        list.add("Ghana")//Ghana
        list.add("Greece")//Greece
        list.add("Grenada")//Grenada
        list.add("Guatemala")//Guatemala
        list.add("Guinea")//Guinea
        list.add("Guinea-Bissau")//Guinea-Bissau
        list.add("Guyana")//Guyana
        list.add("Haiti")//Haiti
        list.add("Honduras")//Honduras
        list.add("Hungary")//Hungary
        list.add("Iceland")//Iceland
        list.add("India")//India
        list.add("Indonesia")//Indonesia
        list.add("Iran")//Iran
        list.add("Iraq")//Iraq
        list.add("Ireland")//Ireland
        list.add("Israel")//Israel
        list.add("Italy")//Italy
        list.add("Jamaica")//Jamaica
        list.add("Japan")//Japan
        list.add("Jordan")//Jordan
        list.add("Kazakhstan")//Kazakhstan
        list.add("Kenya")//Kenya
        list.add("Kiribati")//Kiribati
        list.add("Korea, North")//Korea, North
        list.add("Korea, South")//Korea, South
        list.add("Kosovo")//Kosovo
        list.add("Kuwait")//Kuwait
        list.add("Kyrgyzstan")//Kyrgyzstan
        list.add("Laos")//Laos
        list.add("Latvia")//Latvia
        list.add("Lebanon")//Lebanon
        list.add("Lesotho")//Lesotho
        list.add("Liberia")//Liberia
        list.add("Libya")//Libya
        list.add("Liechtenstein")//Liechtenstein
        list.add("Lithuania")//Lithuania
        list.add("Luxembourg")//Luxembourg
        list.add("Madagascar")//Madagascar
        list.add("Malawi")//Malawi
        list.add("Malaysia")//Malaysia
        list.add("Maldives")//Maldives
        list.add("Mali")//Mali
        list.add("Malta")//Malta
        list.add("Marshall Islands")//Marshall Islands
        list.add("Mauritania")//Mauritania
        list.add("Mauritius")//Mauritius
        list.add("Mexico")//Mexico
        list.add("Micronesia, Federated States of")//Micronesia, Federated States of
        list.add("Moldova")//Moldova
        list.add("Monaco")//Monaco
        list.add("Mongolia")//Mongolia
        list.add("Montenegro")//Montenegro
        list.add("Morocco")//Morocco
        list.add("Mozambique")//Mozambique
        list.add("Myanmar (Burma)")//Myanmar (Burma)
        list.add("Namibia")//Namibia
        list.add("Nauru")//Nauru
        list.add("Nepal")//Nepal
        list.add("Netherlands")//Netherlands
        list.add("New Zealand")//New Zealand
        list.add("Nicaragua")//Nicaragua
        list.add("Niger")//Niger
        list.add("Nigeria")//Nigeria
        list.add("North Macedonia")//North Macedonia
        list.add("Norway")//Norway
        list.add("Oman")//Oman
        list.add("Pakistan")//Pakistan
        list.add("Palau")//Palau
        list.add("Panama")//Panama
        list.add("Papua New Guinea")//Papua New Guinea
        list.add("Paraguay")//Paraguay
        list.add("Peru")//Peru
        list.add("Philippines")//Philippines
        list.add("Poland")//Poland
        list.add("Portugal")//Portugal
        list.add("Qatar")//Qatar
        list.add("Romania")//Romania
        list.add("Russia")//Russia
        list.add("Rwanda")//Rwanda
        list.add("Saint Kitts and Nevis")//Saint Kitts and Nevis
        list.add("Saint Lucia")//Saint Lucia
        list.add("Saint Vincent and the Grenadines")//Saint Vincent and the Grenadines
        list.add("Samoa")//Samoa
        list.add("San Marino")//San Marino
        list.add("Sao Tome and Principe")//Sao Tome and Principe
        list.add("Saudi Arabia")//Saudi Arabia
        list.add("Senegal")//Senegal
        list.add("Serbia")//Serbia
        list.add("Seychelles")//Seychelles
        list.add("Sierra Leone")//Sierra Leone
        list.add("Singapore")//Singapore
        list.add("Slovakia")//Slovakia
        list.add("Slovenia")//Slovenia
        list.add("Solomon Islands")//Solomon Islands
        list.add("Somalia")//Somalia
        list.add("South Africa")//South Africa
        list.add("Spain")//Spain
        list.add("Sri Lanka")//Sri Lanka
        list.add("Sudan")//Sudan
        list.add("Sudan, South")//Sudan, South
        list.add("Suriname")//Suriname
        list.add("Sweden")//Sweden
        list.add("Switzerland")//Switzerland
        list.add("Syria")//Syria
        list.add("Taiwan")//Taiwan
        list.add("Tajikistan")//Tajikistan
        list.add("Tanzania")//Tanzania
        list.add("Thailand")//Thailand
        list.add("Togo")//Togo
        list.add("Tonga")//Tonga
        list.add("Trinidad and Tobago")//Trinidad and Tobago
        list.add("Tunisia")//Tunisia
        list.add("Turkey")//Turkey
        list.add("Turkmenistan")//Turkmenistan
        list.add("Tuvalu")//Tuvalu
        list.add("Uganda")//Uganda
        list.add("Ukraine")//Ukraine
        list.add("United Arab Emirates")//United Arab Emirates
        list.add("United Kingdom")//United Kingdom
        list.add("United States")//United States
        list.add("Uruguay")//Uruguay
        list.add("Uzbekistan")//Uzbekistan
        list.add("Vanuatu")//Vanuatu
        list.add("Vatican City")//Vatican City
        list.add("Venezuela")//Venezuela
        list.add("Vietnam")//Vietnam
        list.add("Yemen")//Yemen
        list.add("Zambia")//Zambia
        list.add("Zimbabwe")//Zimbabwe
        return list
    }

    private fun selectThisSeat(
        context: Context,
        modelTraveller: TravellerModel,
        seatDetailsItem: SeatDetailsItem,
    ) {
        val isSelectedByElse =
            seatsSelected.any { (it.value == seatDetailsItem && it.key != modelTraveller.id) }
        if (isSelectedByElse) {
            context.showToast("You can't reselect the same seat twice")
        } else {
            seatsSelected[modelTraveller.id] = seatDetailsItem
            modelTraveller.seatDetailsItem.value = seatDetailsItem
        }
    }

    @Composable
    private fun seatContent(
        context: Context = LocalContext.current,
        travellerModel: TravellerModel,
        seatDetails: SeatDetailsItem?
    ) {

        if (seatsSelected[travellerModel.id] == seatDetails) {
            Box(
                modifier = Modifier
                    .size(36.sdp)
                    .padding(3.sdp)
                    .background(color = Color.Green)
                    .border(width = 1.sdp, color = seatDetails.getSeatBorderColor())
                    .clickable {
                        seatDetails?.let {
                            selectThisSeat(
                                context = context,
                                modelTraveller = travellerModel,
                                seatDetailsItem = seatDetails
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = seatDetails?.sSRTypeName ?: "",
                    fontSize = 9.textSdp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .size(36.sdp)
                    .padding(3.sdp)
                    .background(color = seatDetails.getSeatColor())
                    .border(width = 1.sdp, color = seatDetails.getSeatBorderColor())
                    .clickable {
                        seatDetails?.let {
                            selectThisSeat(
                                context = context,
                                modelTraveller = travellerModel,
                                seatDetailsItem = seatDetails
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = seatDetails?.sSRTypeName ?: "",
                    fontSize = 9.textSdp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

    fun showPreview(context: Context) {
        if (Accessable.isAccessable()) {
            if (adultTravellers.any { it.seatDetailsItem.value == null } || childTravellers.any { it.seatDetailsItem.value == null }) {
                displayDialogFailure("Select Seats for all the Passengers")
            } else {
                flight?.let {
                    showPreview(context = context, flightsItem = it)
                }
            }
        }
    }

    private fun showPreview(context: Context, flightsItem: FlightsItem) {
        seatSelectionDialog = false
        seatBoxDialog = false
        _state.value.content = {
            ContentComposableDialogBig(onDismissRequest = {
                _state.value.baseDialogVisible.value = false
            }, onAccept = {
                _state.value.baseDialogVisible.value = false
            }, onCancel = {
                _state.value.baseDialogVisible.value = false
            }, content = {
                PreviewConfirmContent(
                    item = flightsItem,
                    adults = adultTravellers,
                    child = childTravellers,
                    infant = infantTravellers,
                    onFlightsItemSelect = {
                        _state.value.baseDialogVisible.value = false
                    }
                )
            })
        }
        _state.value.baseDialogVisible.value = true
    }
}