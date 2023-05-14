package com.fintech.superadmin.activities.transactionDetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
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
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.data.DetailedDto
import com.fintech.superadmin.data.db.AppDatabase
import com.fintech.superadmin.data.db.entities.User
import com.fintech.superadmin.data.network.MinistatementItem
import com.fintech.superadmin.data.network.responses.AnalyticsResponseModel
import com.fintech.superadmin.data_model.DynamicData
import com.fintech.superadmin.ui.theme.YespayTheme
import com.fintech.superadmin.util.captureScreen
import com.fintech.superadmin.util.shareScreen
import com.fintech.superadmin.util.toBitmap
import org.json.JSONException
import org.json.JSONObject

class TransactionDetailsReceiptActivity : ComponentActivity() {

    var response: DetailedDto? = null
    lateinit var transactionType: String
    var regular: AnalyticsResponseModel? = null
    var user: User? = null
    var dynamicList = ArrayList<DynamicData>()
    var dynamicMap: HashMap<String, Any?> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializers()
        setContent {
            YespayTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    Receipt()
                }
            }
        }
    }

    private fun initializers() {
        user =
            AppDatabase.getAppDatabase(this@TransactionDetailsReceiptActivity).userDao.regularUser
        response = intent.getSerializableExtra("response") as DetailedDto?
        regular = intent.getSerializableExtra("regular") as AnalyticsResponseModel?
        initiateResponses()
    }


    @Preview(showBackground = true)
    @Composable
    private fun Receipt() {
        var scrollView = LocalView.current
//        val currentTime = LocalTime.now()
//        val formatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
//        val formattedTime = currentTime.format(formatter)
        Column {
            TopAppBar(title = {
                Text(text = "${response?.reportTitle ?: regular?.transactionType ?: ""} ")
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
                        scrollView = LocalView.current
                    }
                    response?.let { response ->
                        item {
                            Spacer(modifier = Modifier.height(20.sdp))
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = response.reportTitle,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.textSdp,
                                    color = MaterialTheme.colors.onSurface.copy(.45f)
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.sdp))
                        }
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                AsyncImage(
                                    modifier = Modifier.size(65.sdp),
                                    model = if (regular?.status?.contains(
                                            "success",
                                            true
                                        ) == true
                                    ) {
                                        R.drawable.aeps_check
                                    } else if (regular?.status?.contains(
                                            "pending",
                                            true
                                        ) == true || regular?.status?.contains(
                                            "process",
                                            true
                                        ) == true
                                    ) {
                                        R.drawable.clock
                                    } else {
                                        R.drawable.aeps_cancel
                                    },
                                    contentDescription = "response"
                                )
                            }
                        }

                        items(dynamicList) {

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

                        item {
                            Spacer(modifier = Modifier.height(5.sdp))
                        }
                        user?.partner_id?.let {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "Agent ID: ",
                                        textAlign = TextAlign.Start,
                                        fontSize = 10.textSdp
                                    )
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = it,
                                        textAlign = TextAlign.End,
                                        fontSize = 10.textSdp
                                    )
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.sdp))
                        }
                        user?.address?.let {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "BC Location: ",
                                        textAlign = TextAlign.Start,
                                        fontSize = 10.textSdp
                                    )
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = it,
                                        textAlign = TextAlign.End,
                                        fontSize = 10.textSdp

                                    )
                                }

                            }

                        }

                    }

                    item {
                        Spacer(modifier = Modifier.height(10.sdp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            OutlinedButton(
                                shape = RoundedCornerShape(12.sdp),
                                onClick = {
                                    onBackPressedDispatcher.onBackPressed()
                                }
                            ) {
                                Text(text = "Done")
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(90.sdp))
                    }

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box(
                        modifier = Modifier
                            .size(55.sdp)
                            .clip(shape = RoundedCornerShape(topEnd = 85.sdp))
                            .background(
                                color = MaterialTheme.colors.primary.copy(.45f),
                                shape = RoundedCornerShape(topEnd = 85.sdp)
                            )
                            .clickable {
                                scrollView.rootView.toBitmap(this@TransactionDetailsReceiptActivity) {
                                    captureScreen(it)
                                }
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(end = 18.sdp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Receipt,
                                contentDescription = "receipt",
                                tint = MaterialTheme.colors.surface
                            )
                            Text(
                                text = "Download\nReceipt",
                                fontSize = 7.textSdp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.surface
                            )
                        }
                    }


                    Box(
                        modifier = Modifier
                            .size(55.sdp)
                            .clip(shape = RoundedCornerShape(topStart = 85.sdp))
                            .background(
                                color = MaterialTheme.colors.primary.copy(.45f),
                                shape = RoundedCornerShape(topStart = 85.sdp)
                            )
                            .clickable {
                                scrollView.rootView.toBitmap(this@TransactionDetailsReceiptActivity) {
                                    shareScreen(it)
                                }
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 18.sdp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "receipt",
                                tint = MaterialTheme.colors.surface
                            )
                            Text(
                                text = "Download\nReceipt",
                                fontSize = 7.textSdp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.surface
                            )
                        }
                    }
                }

            }
        }
    }


    private fun initiateResponses() {
        response?.let { response ->

            response.transData?.let { transData ->
                try {
                    val json = JSONObject(response.transData)
                    val keys: Iterator<String> = json.keys()
                    while (keys.hasNext()) {
                        val key = keys.next()
                        val value: Any? = json.get(key)
                        val setKey = key.trim().replace("_", " ").uppercase()
                        if (!dynamicMap.contains(setKey) && !value?.toString().isNullOrEmpty()) {
                            dynamicMap[setKey] = value
                            dynamicList.add(DynamicData(setKey, value))
                        }
                        println("$key: $value")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            response.reportData?.let { reportData ->
                try {
                    val json = JSONObject(response.reportData)
                    val keys: Iterator<String> = json.keys()
                    while (keys.hasNext()) {
                        val key = keys.next()
                        val value: Any? = json.get(key)
                        val setKey = key.trim().replace("_", " ").uppercase()
                        if (!dynamicMap.contains(setKey) && !value?.toString().isNullOrEmpty()) {
                            dynamicMap[setKey] = value
                            dynamicList.add(DynamicData(setKey, value))
                        }
                        println("$key: $value")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            response.commData?.let { commData ->
                try {
                    val json = JSONObject(response.commData)
                    val keys: Iterator<String> = json.keys()
                    while (keys.hasNext()) {
                        val key = keys.next()
                        val value: Any? = json.get(key)
                        val setKey = key.trim().replace("_", " ").uppercase()
                        if (!dynamicMap.contains(setKey) && !value?.toString().isNullOrEmpty()) {
                            dynamicMap[setKey] = value
                            dynamicList.add(DynamicData(setKey, value))
                        }
                        println("$key: $value")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }
}