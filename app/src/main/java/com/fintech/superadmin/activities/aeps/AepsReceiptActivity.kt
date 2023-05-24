package com.fintech.superadmin.activities.aeps

import android.content.Intent
import android.os.Build
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
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.data.db.AppDatabase
import com.fintech.superadmin.data.db.entities.User
import com.fintech.superadmin.data.network.AePSDto
import com.fintech.superadmin.data.network.MinistatementItem
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import com.fintech.superadmin.util.Constant
import com.fintech.superadmin.util.captureScreen
import com.fintech.superadmin.util.shareScreen
import com.fintech.superadmin.util.toBitmap
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.text.SimpleDateFormat;
import java.util.Date;

class AepsReceiptActivity : BaseComponentAct() {

    var response: AePSDto? = null
    lateinit var transactionType: String
    var user: User? = null
    var bankName: String? = null

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
        user = AppDatabase.getAppDatabase(this@AepsReceiptActivity).userDao.regularUser
        response = intent.getSerializableExtra("response") as AePSDto?
        transactionType = intent.getStringExtra("transactionType") ?: ""
        bankName = intent.getStringExtra("bankName")
    }


    @Preview(showBackground = true)
    @Composable
    private fun Receipt() {
        var scrollView = LocalView.current
        val currentTime: String
        val formatter: Any

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val time = LocalTime.now()
            currentTime = time.format(DateTimeFormatter.ofPattern("hh:mm:ss a"))
            formatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
        } else {
            val time = Date()
            val dateFormat = SimpleDateFormat("hh:mm:ss a")
            currentTime = dateFormat.format(time)
            formatter = dateFormat
        }
        val formattedTime = currentTime.format(formatter)
        Column {
            TopAppBar(title = {
                Text(text = "AePS Receipt")
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
                                    text = typeToName(),
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
                                    model = if (response.status == true && response.responseCode == 1) R.drawable.aeps_check else R.drawable.aeps_cancel,
                                    contentDescription = "response"
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
                                Text(
                                    text = "Don't pay any charges for this transaction",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 11.textSdp,
                                    color = MaterialTheme.colors.primaryVariant
                                )
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(5.sdp))
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "Date: ",
                                    textAlign = TextAlign.Start,
                                    fontSize = 10.textSdp
                                )
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = response.datetime?.toString()?.substringBefore(" ")
                                            ?: LocalDate.now().toString(),
                                        textAlign = TextAlign.End,
                                        fontSize = 10.textSdp
                                    )
                                } else {
                                    val calendar = Calendar.getInstance()
                                    val year = calendar.get(Calendar.YEAR)
                                    val month =
                                        calendar.get(Calendar.MONTH) + 1  // Note: Calendar.MONTH is zero-based
                                    val day = calendar.get(Calendar.DAY_OF_MONTH)

                                    val currentDate = "$year-$month-$day"

                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = response.datetime?.toString()?.substringBefore(" ")
                                            ?: currentDate,
                                        textAlign = TextAlign.End,
                                        fontSize = 10.textSdp
                                    )
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.sdp))
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "Time: ",
                                    textAlign = TextAlign.Start,
                                    fontSize = 10.textSdp
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = response.datetime?.toString()?.substringAfter(" ")
                                        ?: formattedTime,
                                    textAlign = TextAlign.End,
                                    fontSize = 10.textSdp
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.sdp))
                        }

                        bankName?.let {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "Bank Name: ",
                                        textAlign = TextAlign.Start,
                                        fontSize = 10.textSdp
                                    )
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = bankName?:"",
                                        textAlign = TextAlign.End,
                                        fontSize = 10.textSdp
                                    )
                                }
                            }
                            item {
                                Spacer(modifier = Modifier.height(5.sdp))
                            }
                        }

                        response.name?.let {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "BC Name: ",
                                        textAlign = TextAlign.Start,
                                        fontSize = 10.textSdp
                                    )
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = it.toString(),
                                        textAlign = TextAlign.End,
                                        fontSize = 10.textSdp
                                    )
                                }
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
                        item {
                            Spacer(modifier = Modifier.height(5.sdp))
                        }
                        response.mobile?.let {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "Mobile: ",
                                        textAlign = TextAlign.Start,
                                        fontSize = 10.textSdp
                                    )
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = it.toString(),
                                        textAlign = TextAlign.End,
                                        fontSize = 10.textSdp
                                    )
                                }

                            }

                        }
                        item {
                            Spacer(modifier = Modifier.height(5.sdp))
                        }
                        response.clientrefno?.let {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "Client Ref: ",
                                        textAlign = TextAlign.Start,
                                        fontSize = 10.textSdp
                                    )
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = it.toString(),
                                        textAlign = TextAlign.End,
                                        fontSize = 10.textSdp

                                    )
                                }

                            }

                        }
                        item {
                            Spacer(modifier = Modifier.height(5.sdp))
                        }
                        response.lastAadhar?.let {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "Aadhaar Number: ",
                                        textAlign = TextAlign.Start,
                                        fontSize = 10.textSdp
                                    )
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "**** **** $it",
                                        textAlign = TextAlign.End,
                                        fontSize = 10.textSdp

                                    )
                                }

                            }

                        }
                        item {
                            Spacer(modifier = Modifier.height(5.sdp))
                        }
                        response.ackno?.let {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "Acknowledge Number: ",
                                        textAlign = TextAlign.Start,
                                        fontSize = 10.textSdp
                                    )
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = it.toString(),
                                        textAlign = TextAlign.End,
                                        fontSize = 10.textSdp

                                    )
                                }

                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.sdp))
                        }
                        response.bankrrn?.let {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "Bank RRN: ",
                                        textAlign = TextAlign.Start,
                                        fontSize = 10.textSdp
                                    )
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = it.toString(),
                                        textAlign = TextAlign.End,
                                        fontSize = 10.textSdp

                                    )
                                }

                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.sdp))
                        }
                        response.bankiin?.let {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "Bank IIN: ",
                                        textAlign = TextAlign.Start,
                                        fontSize = 10.textSdp
                                    )
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = it.toString(),
                                        textAlign = TextAlign.End,
                                        fontSize = 10.textSdp

                                    )
                                }

                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.sdp))
                        }
                        response.amount?.let {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "Transaction Amount: ",
                                        textAlign = TextAlign.Start,
                                        fontSize = 10.textSdp
                                    )
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = stringResource(id = R.string.rupee_sign) + it.toString(),
                                        textAlign = TextAlign.End,
                                        fontSize = 10.textSdp
                                    )
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.sdp))
                        }
                        response.balanceamount?.let {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "Account Balance",
                                        textAlign = TextAlign.Start,
                                        fontSize = 10.textSdp
                                    )
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = stringResource(id = R.string.rupee_sign) + it.toString(),
                                        textAlign = TextAlign.End,
                                        fontSize = 10.textSdp
                                    )
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.sdp))
                        }
                        response.message?.let {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "Transaction Status: ",
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
                        response.ministatement?.let { it ->
                            if (it.isNotEmpty()) {
                                item {
                                    Spacer(modifier = Modifier.height(20.sdp))
                                }
                                item {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Mini Statement",
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 11.textSdp,
                                            color = MaterialTheme.colors.primaryVariant
                                        )
                                    }
                                }
                                item {
                                    Spacer(modifier = Modifier.height(5.sdp))
                                }
                                items(it) { data ->
                                    MiniStatementInfo(data)
                                }
                            }
                        }

                        response.infoList?.let { it ->
                            if (it.isNotEmpty()) {
                                item {
                                    Spacer(modifier = Modifier.height(20.sdp))
                                }
                                item {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Mini Statement",
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 11.textSdp,
                                            color = MaterialTheme.colors.primaryVariant
                                        )
                                    }
                                }
                                item {
                                    Spacer(modifier = Modifier.height(5.sdp))
                                }
                                items(it) { data ->
                                    MiniStatementInfoAdditional(data)
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
                                    Constant.toReset = true
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
                                scrollView.rootView.toBitmap(this@AepsReceiptActivity) {
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
                                scrollView.rootView.toBitmap(this@AepsReceiptActivity) {
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

    @Composable
    private fun MiniStatementInfo(data: MinistatementItem?) {
        data?.let {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Date: ",
                            textAlign = TextAlign.Start,
                            fontSize = 10.textSdp
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = it.date?.toString() ?: "",
                            textAlign = TextAlign.End,
                            fontSize = 10.textSdp
                        )
                    }

                }


                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                ) {

                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Amount: ",
                            textAlign = TextAlign.Start,
                            fontSize = 10.textSdp
                        )
                        Text(

                            modifier = Modifier.weight(1f),
                            text = (stringResource(id = R.string.rupee_sign) + it.amount?.toString()),
                            textAlign = TextAlign.End,
                            fontSize = 10.textSdp
                        )
                    }
                }



                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Transaction Type: ",
                            textAlign = TextAlign.Start,
                            fontSize = 10.textSdp
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = it.txnType?.toString() ?: "",
                            textAlign = TextAlign.End,
                            fontSize = 10.textSdp
                        )
                    }
                }

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Narration: ",
                            textAlign = TextAlign.Start,
                            fontSize = 10.textSdp
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = it.narration?.toString() ?: "",
                            textAlign = TextAlign.End,
                            fontSize = 10.textSdp
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 10.sdp))

            }
        }
    }


    @Composable
    private fun MiniStatementInfoAdditional(data: String?) {
        data?.let {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = data,
                            textAlign = TextAlign.Center,
                            fontSize = 10.textSdp
                        )
                    }

                }

                Spacer(modifier = Modifier.padding(vertical = 4.sdp))

            }
        }
    }


    private fun typeToName(): String {
        when (transactionType.trim().lowercase()) {
            "cw" -> {
                return "CASH WITHDRAWAL"
            }

            "be" -> {
                return "BALANCE ENQUIRY"
            }

            "m" -> {
                return "AADHAAR PAY"
            }

            "ms" -> {
                return "MINI STATEMENT"
            }

            else -> {
                return ""
            }
        }
    }
}