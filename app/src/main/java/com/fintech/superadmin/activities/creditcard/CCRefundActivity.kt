package com.fintech.superadmin.activities.creditcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.data.dto.CreditCardHistory
import com.fintech.superadmin.ui.theme.YespayTheme
import com.fintech.superadmin.viewmodel.CCRefundViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CCRefundActivity : ComponentActivity() {

    val viewModel by viewModels<CCRefundViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YespayTheme(darkTheme = false) {
                RefundScreenSystem()
            }
        }
    }


    @Composable
    private fun RefundScreenSystem() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Credit Card")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onBackPressedDispatcher.onBackPressed()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    }
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                if (viewModel.historyList == null || viewModel.historyList?.isEmpty() == true){
                    item {
                        Text(text = "No Record", Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
                    }
                }
                else{
                    items(viewModel.historyList?: emptyList()) { hist->
                        PaymentCard(hist)
                    }
                }
            }
        }
    }

    @Composable
    private fun PaymentCard(history: CreditCardHistory) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.sdp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.sdp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Amount: ${history.aMOUNT}", fontSize = 8.textSdp)
                Text(text = "Status: ${history.sTATUS}", fontSize = 8.textSdp)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.sdp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Card No: ${history.cARDNUM}", fontSize = 8.textSdp)
                Text(text = "Card Type: ${history.cARDTYPE}", fontSize = 8.textSdp)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.sdp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Reference id: ${history.rEFFRENCEID}", fontSize = 8.textSdp)
                Button(
                    modifier = Modifier.padding(0.dp),
                    onClick = {
                        viewModel.sendOTPForRefundCreditCard(context = this@CCRefundActivity, reference = history.rEFFRENCEID?:"sfdfsd")
                    }
                ) {
                    Text(text = "Refund", fontSize = 8.textSdp)
                }
            }

            Divider()
        }
    }

}