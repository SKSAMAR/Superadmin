package com.fintech.superadmin.flight.presentation.airports

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.fintech.superadmin.flight.presentation.common.BasicScreenState
import com.fintech.superadmin.flight.presentation.common.BasicSearchView
import com.fintech.superadmin.flight.util.common.Constant
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp
import dagger.hilt.android.AndroidEntryPoint
import com.fintech.superadmin.ui.theme.SuperAdminTheme as SuperAdminTheme1

@AndroidEntryPoint
class AirportsActivity : ComponentActivity() {

    var preSelected = ""
    val viewModel by viewModels<AirportsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializer()
        setContent {
            SuperAdminTheme1 {
                Surface(color = MaterialTheme.colorScheme.surface) {
                    AirportsContent()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AirportsContent() {
        LaunchedEffect(viewModel.search) {
            viewModel.getAirports()
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Select Your Airport")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "arrow"
                            )
                        }
                    }
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
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
                                if (it?.iD != preSelected) {
                                    it?.let {
                                        AirportData(
                                            data = it,
                                            //modifier = Modifier.padding(vertical = 6.sdp)
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

    private fun initializer() {
        preSelected = intent.getStringExtra(Constant.SelectedAirPort) ?: ""
    }

    @Composable
    private fun AirportData(
        data: AirPortDto,
    ) {

        Box(
            modifier = Modifier
                .padding(horizontal = 3.sdp, vertical = 4.sdp)
                .fillMaxWidth()
                .border(width = .5.dp, color = Color.Gray, shape = RoundedCornerShape(5.sdp))
                .clickable {
                    val intentData = Intent()
                    intentData.putExtra(Constant.AirPort, data)
                    setResult(RESULT_OK, intentData)
                    finish()
                }.clip(shape = RoundedCornerShape(5.sdp))
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 10.sdp, horizontal = 8.sdp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {

                    Text(
                        text = buildAnnotatedString {
                            if (!data.cITY.isNullOrEmpty()){
                                append(data.cITY)
                            }
                            if (!data.cOUNTRY.isNullOrEmpty()){
                                append(", "+data.cOUNTRY)
                            }
                        },
                        fontSize = 11.textSdp,
                        fontWeight = FontWeight.SemiBold,
                        
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray
                    )

                    Text(
                        text = data.aIRPORT ?: "",
                        fontSize = 9.textSdp,
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
                        fontSize = 8.textSdp,
                        
                        modifier = Modifier.padding(vertical = 3.sdp, horizontal = 12.sdp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

            }
        }
    }


    /**
    @Composable
    private fun AirportData(
        data: AirPortDto,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    val intentData = Intent()
                    intentData.putExtra(Constant.AirPort, data)
                    setResult(RESULT_OK, intentData)
                    finish()
                },
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = buildAnnotatedString {
                         if (!data.cITY.isNullOrEmpty()){
                            append(data.cITY)
                         }
                        if (!data.cOUNTRY.isNullOrEmpty()){
                            append(", "+data.cOUNTRY)
                        }
                    },
                    fontSize = 11.textSdp,
                    fontWeight = FontWeight.SemiBold,
                    
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = data.aIRPORT ?: "",
                    fontSize = 7.textSdp,
                    fontWeight = FontWeight.SemiBold,
                    
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
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
                        color = MaterialTheme.colorScheme.onSurface.copy(0.35f),
                        shape = RoundedCornerShape(10.sdp)
                    )
                    .clip(shape = RoundedCornerShape(8.sdp))
            ) {
                Text(
                    text = data.aIRPORTCODE ?: "",
                    fontSize = 8.textSdp,
                    
                    modifier = Modifier.padding(vertical = 3.sdp, horizontal = 8.sdp)
                )
            }

        }
    }
    **/
}