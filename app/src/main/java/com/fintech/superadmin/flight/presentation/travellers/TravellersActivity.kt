package com.fintech.superadmin.flight.presentation.travellers

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.fintech.superadmin.flight.presentation.common.BaseScaffold
import com.fintech.superadmin.flight.presentation.common.BasicScreenState
import com.fintech.superadmin.flight.presentation.travellers.component.SeatSelectionItem
import com.fintech.superadmin.flight.presentation.travellers.component.TravelInfo
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import com.fintech.superadmin.flight.util.common.Constant
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp
import com.fintech.superadmin.ui.theme.BackgroundGrey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TravellersActivity : ComponentActivity() {

    val viewModel by viewModels<TravellersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initilizers(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colorScheme.surface) {
                    TravellersContent()
                }
            }
        }
    }


    @OptIn(
        ExperimentalMaterial3Api::class,
        ExperimentalComposeUiApi::class
    )
    @Composable
    private fun TravellersContent(
        context: Context = LocalContext.current
    ) {
        val scope = rememberCoroutineScope()
        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Seats Selection")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onBackPressedDispatcher.onBackPressed()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack, contentDescription = "arrow"
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "back"
                            )
                        }
                    },
                )
            },
            state = viewModel.state,
        ) {
            BasicScreenState(state = viewModel.state) {
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = BackgroundGrey)
                    ) {
                        items(viewModel.adultTravellers) {
                            TravelInfo(traveller = it)
                        }

                        items(viewModel.infantTravellers) {
                            TravelInfo(traveller = it)
                        }

                        items(viewModel.childTravellers) {
                            TravelInfo(traveller = it)
                        }
                        
                        item { 
                            Spacer(modifier = Modifier.height(120.sdp))
                        }
                    }

                    Box(
                        modifier = Modifier
                            .padding(vertical = 20.sdp, horizontal = 15.sdp)
                            .fillMaxWidth()
                            .background(
                                shape = RoundedCornerShape(6.sdp),
                                color = MaterialTheme.colorScheme.primary
                            )
                            .clip(RoundedCornerShape(6.sdp))
                            .align(Alignment.BottomCenter)
                            .clickable {
                                scope.launch {
                                    if (viewModel.selectSeatsCallValidate()) {
                                        viewModel.seatSelectionDialog = !viewModel.seatSelectionDialog
                                    }
                                }
                            }, contentAlignment = Alignment.Center
                    ) {

                        Text(
                            modifier = Modifier.padding(vertical = 5.sdp),
                            text = "Select Seats",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.textSdp
                        )

                    }



                    if (viewModel.seatSelectionDialog) {

                        androidx.compose.material.AlertDialog(
                            modifier = Modifier
                                .background(color = MaterialTheme.colorScheme.surface)
                                .padding(vertical = 15.sdp),
                            onDismissRequest = {
                                viewModel.seatSelectionDialog = false
                            },
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            properties = DialogProperties(
                                dismissOnBackPress = true,
                                dismissOnClickOutside = true,
                                usePlatformDefaultWidth = false,
                                decorFitsSystemWindows = true,
                                securePolicy = SecureFlagPolicy.SecureOff
                            ),
                            title = null,
                            text = null,
                            buttons = {
                                Column(
                                    modifier = Modifier.padding(10.sdp)
                                ){
                                    Text("Seat Choose")
                                    Column {
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f)
                                        ) {
                                            items(viewModel.adultTravellers) {
                                                SeatSelectionItem(traveller = it)
                                            }
                                            items(viewModel.childTravellers) {
                                                SeatSelectionItem(traveller = it)
                                            }
                                        }

                                        OutlinedButton(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 15.sdp, horizontal = 20.sdp),
                                            onClick = {
                                                viewModel.showPreview(context = context)
                                            }
                                        ) {
                                            Text(
                                                text = "View Preview",
                                                modifier = Modifier.padding(vertical = 5.sdp)
                                            )
                                        }
                                    }
                                }

                            }
                        )
                    }


                }
            }
        }
    }

    private fun initilizers(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModel.flight = intent.getParcelableExtra(Constant.FlightsItem)
            viewModel.searchKey = intent.getStringExtra(Constant.SearchKey)
            viewModel.getSSR()
        }
    }

}