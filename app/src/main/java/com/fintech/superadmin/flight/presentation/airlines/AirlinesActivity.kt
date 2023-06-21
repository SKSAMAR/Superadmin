package com.fintech.superadmin.flight.presentation.airlines

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.fintech.superadmin.flight.presentation.common.BasicSearchView
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AirlinesActivity : ComponentActivity() {

    val viewModel by viewModels<AirlinesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colorScheme.surface) {
                    AirlinesScreenContent()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AirlinesScreenContent() {
        LaunchedEffect(key1 = viewModel.search) {
            viewModel.getAirlines()
        }

        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(text = "Select Your Airlines")
                }, navigationIcon = {
                    IconButton(onClick = {
                        onBackPressedDispatcher.onBackPressed()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = "arrow"
                        )
                    }
                })
            },
        ) {
            Column(
                modifier = Modifier.padding(it)
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
                            hint = "Search for Airlines",
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
                Spacer(modifier = Modifier.height(8.sdp))
                com.fintech.superadmin.flight.presentation.airlines.AirlinesContent()
            }
        }
    }
}