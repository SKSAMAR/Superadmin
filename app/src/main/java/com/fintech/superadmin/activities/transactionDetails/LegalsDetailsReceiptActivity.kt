package com.fintech.superadmin.activities.transactionDetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.fintech.superadmin.R
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.data.DetailedDto
import com.fintech.superadmin.data.db.AppDatabase
import com.fintech.superadmin.data.db.entities.User
import com.fintech.superadmin.data.network.MinistatementItem
import com.fintech.superadmin.data.network.responses.AnalyticsResponseModel
import com.fintech.superadmin.data_model.DynamicData
import com.fintech.superadmin.flight.presentation.common.DateDialogSys.showDateDialog
import com.fintech.superadmin.flight.presentation.home.component.toSpecificDepartDates
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import com.fintech.superadmin.util.captureScreen
import com.fintech.superadmin.util.shareScreen
import com.fintech.superadmin.util.toBitmap
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject

@AndroidEntryPoint
class LegalsDetailsReceiptActivity : BaseComponentAct() {

    var info: String? = null

    private val viewModel by viewModels<LegalViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializers()
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    Receipt()
                }
            }
        }
    }

    private fun initializers() {
        info = intent.getStringExtra("info")
        initiateResponses("")
    }


    @Composable
    private fun Receipt() {
        LaunchedEffect(key1 = viewModel.search) {
            viewModel.getLegalServices(info = info ?: "")
        }

        Column {
            TopAppBar(title = {
                Text(text = info?.replace("_", " ")?.uppercase() ?: "")
            }, navigationIcon = {
                IconButton(onClick = {
                    onBackPressedDispatcher.onBackPressed()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "arrow_back"
                    )
                }
            })
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 15.sdp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(15.sdp))
                    }

                    item {

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    indication = null,
                                    interactionSource = MutableInteractionSource()
                                ) {
                                    showDateDialog {
                                        toSpecificDepartDates(it, "yyyy-mm-dd") {
                                            viewModel.search = it
                                        }
                                    }
                                },
                            enabled = false,
                            value = viewModel.search,
                            onValueChange = {

                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Search by Date")
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(disabledTextColor = MaterialTheme.colors.onSurface),
                            shape = RoundedCornerShape(18.sdp)
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(10.sdp))
                    }

                    item {
                        BasicScreenState(state = viewModel.state) {

                        }
                    }

                    viewModel.state.value.receivedResponse?.let {
                        items(it) {
                            val list = initiateResponses(it)
                            CardData(list)
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(90.sdp))
                    }

                }

            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun CardData(dynamicList: ArrayList<DynamicData>) {
        val smallModifier = Modifier
            .fillMaxWidth()
            .height(120.sdp)
            .padding(10.sdp)
        val bigModifier = Modifier
            .fillMaxWidth()
            .padding(10.sdp)

        var selectedModifier by remember { mutableStateOf(smallModifier) }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.sdp),
            onClick = {
                selectedModifier = if (selectedModifier == smallModifier) {
                    bigModifier
                } else {
                    smallModifier
                }
            }
        ) {
            Column(
                modifier = selectedModifier
            ) {
                dynamicList.forEach {
                    if (it.value != null && !it.value?.toString()
                            .isNullOrEmpty() && it.value?.toString()
                            ?.equals("") == false && it.value?.toString()
                            ?.equals("null") == false
                    ) {


                        Spacer(modifier = Modifier.height(5.sdp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "${it.key ?: ""}: ",
                                textAlign = TextAlign.Start,
                                fontSize = 10.textSdp
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = it.value?.toString() ?: "",
                                textAlign = TextAlign.End,
                                fontSize = 10.textSdp

                            )
                        }

                        Spacer(modifier = Modifier.height(5.sdp))
                    }
                }
            }
        }
    }


    private fun initiateResponses(response: Any?): ArrayList<DynamicData> {
        val dynamicList = ArrayList<DynamicData>()
        val dynamicMap: HashMap<String, Any?> = HashMap()
        response?.let { transData ->
            try {
                val data = Gson().toJson(transData)
                val json = JSONObject(data)
                val keys: Iterator<String> = json.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    val value: Any = json.get(key)
                    val setKey = key.trim().replace("_", " ").uppercase()
                    if (!dynamicMap.contains(setKey) && value.toString().isNotEmpty()) {
                        dynamicMap[setKey] = value
                        if (key != "ID" && key != "MAIN_OWNER_ID" && key != "MAIN_OWNER" && key != "OWNER_ID" && key != "USER_ID"){
                            dynamicList.add(DynamicData(setKey, value))
                        }
                    }
                    println("$key: $value")
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return dynamicList
    }
}