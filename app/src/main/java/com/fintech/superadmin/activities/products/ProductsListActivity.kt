package com.fintech.superadmin.activities.products

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import coil.compose.SubcomposeAsyncImage
import com.fintech.superadmin.R
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.presentation.common.ScreenAnimation
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.data.dto.ProductDto
import com.fintech.superadmin.ui.theme.FarLightestGrey
import com.fintech.superadmin.ui.theme.MyGreen
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import com.fintech.superadmin.util.SearchBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsListActivity : BaseComponentAct() {

    val viewModel by viewModels<ProductViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    ProductListScreen()
                }
            }
        }
    }


    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun ProductListScreen() {
        LaunchedEffect(key1 = viewModel.search) {
            viewModel.getProductLists()
        }
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(text = "Products List")
            }, navigationIcon = {
                IconButton(
                    onClick = {
                        onBackPressedDispatcher.onBackPressed()
                    }
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                }
            }, actions = {
                IconButton(
                    onClick = {
                        startActivity(Intent(this@ProductsListActivity, OrdersListActivity::class.java))
                    }
                ) {
                    Icon(imageVector = Icons.Default.History, contentDescription = "history")
                }
            })
        }) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(color = FarLightestGrey),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(modifier = Modifier.padding(vertical = 7.dp),
                    hint = "Search Products",
                    value = viewModel.search,
                    onValueChange = { viewModel.search = it })
                BasicScreenState(state = viewModel.state) {
                    viewModel.state.value.receivedResponse?.let {
                        if (it.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(it) {
                                    ProductsData(
                                        onClick = { selected ->
                                            viewModel.selectedMember = selected
                                        }, productDto = it
                                    )
                                }
                            }
                        }

                    }
                }

                if (viewModel.selectedMember != null) {
                    AlertDialog(
                        modifier = Modifier.padding(horizontal = 10.sdp),
                        onDismissRequest = {
                            viewModel.selectedMember = null
                        },
                        properties = DialogProperties(
                            dismissOnBackPress = true, dismissOnClickOutside = true,
                            usePlatformDefaultWidth = false,
                            //decorFitsSystemWindows = true,
                            securePolicy = SecureFlagPolicy.SecureOff
                        ),
                        title = null,
                        text = null,
                        buttons = {
                            viewModel.selectedMember?.let {
                                BuyDialogContent(it)
                            }
                        },
                    )
                }
            }
        }
    }

    @Composable
    private fun ProductsData(
        modifier: Modifier = Modifier,
        productDto: ProductDto,
        onClick: (ProductDto) -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.sdp, vertical = 4.sdp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.sdp)
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.sdp)
                        .padding(vertical = 2.sdp),
                    contentScale = ContentScale.Fit,
                    model = productDto.iMAGE,
                    loading = {
                        ScreenAnimation(resources = R.raw.loading)
                    },
                    error = {
                        ScreenAnimation(resources = R.raw.not_found)
                    },
                    contentDescription = "image"
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.sdp)
                    ) {
                        Text(
                            text = "Name",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = productDto.nAME ?: "",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.sdp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Price",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${stringResource(id = R.string.rupee_sign)}  ${productDto.pRICE ?: "0"}",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                productDto.dESCRIPTION?.let {
                    if (it.trim().isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.sdp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 5.sdp)
                            ) {
                                Text(
                                    text = "Description: ",
                                    fontSize = 10.textSdp,
                                    maxLines = 1,
                                    fontWeight = FontWeight.Normal,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = productDto.dESCRIPTION,
                                    fontSize = 10.textSdp,
                                    maxLines = 1,
                                    fontWeight = FontWeight.Bold,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.sdp)
                    ) {
                        Text(
                            text = "Discount",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${stringResource(id = R.string.rupee_sign)}  ${productDto.dISCOUNT ?: "0"}",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.sdp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Courier Charge",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${stringResource(id = R.string.rupee_sign)}  ${productDto.cOURIERCHARGE ?: "0"}",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    shape = RoundedCornerShape(12.sdp),
                    onClick = {
                        onClick(productDto)
                    }
                ) {
                    Text(text = "Buy Now")
                }
            }
        }
    }


    @Composable
    private fun BuyDialogContent(
        productDto: ProductDto,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.sdp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Billing Address", fontSize = 16.textSdp)
                    Text(text = "Add new address for express delivery", fontSize = 12.textSdp)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AddressTextField(
                        modifier = Modifier.weight(1f),
                        placeholderText = "Full Name",
                        value = viewModel.fullName,
                        onValueChange = {
                            viewModel.fullName = it
                        }
                    )
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AddressTextField(
                        modifier = Modifier.weight(1f),
                        placeholderText = "Address",
                        value = viewModel.address,
                        onValueChange = {
                            viewModel.address = it
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AddressTextField(
                        modifier = Modifier.weight(1f),
                        placeholderText = "Landmark",
                        value = viewModel.landmark,
                        onValueChange = {
                            viewModel.landmark = it
                        }
                    )

                    AddressTextField(
                        modifier = Modifier.weight(1f),
                        placeholderText = "City",
                        value = viewModel.city,
                        onValueChange = {
                            viewModel.city = it
                        }
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AddressTextField(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { viewModel.changeCircle(this@ProductsListActivity) },
                        placeholderText = "State",
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "dropdown"
                            )
                        },
                        value = viewModel.homeState,
                        onValueChange = {

                        },
                    )

                    AddressTextField(
                        modifier = Modifier.weight(1f),
                        placeholderText = "Pin Code",
                        value = viewModel.pincode,
                        onValueChange = {
                            viewModel.pincode = it
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AddressTextField(
                        modifier = Modifier.weight(1f),
                        placeholderText = "M-PIN",
                        value = viewModel.tpin,
                        onValueChange = {
                            viewModel.tpin = it
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                ) {
                    Text(
                        text = "Main Wallet Balance: ${stringResource(id = R.string.rupee_sign)} ${viewModel.user?.mainbalance ?: ""}",
                        fontSize = 10.textSdp,
                        color = MyGreen
                    )
                    Text(
                        text = "Note: The courier charge will be deducted from your main wallet balance.",
                        fontSize = 10.textSdp,
                        color = MaterialTheme.colors.error
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 2.sdp),
                        onClick = {
                            viewModel.buyProduct(productDto, context = this@ProductsListActivity)
                        }
                    ) {
                        Text(text = "Submit")
                    }

                    OutlinedButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 2.sdp),
                        onClick = {
                            viewModel.selectedMember = null
                        }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            }
        }
    }


    @Composable
    private fun AddressTextField(
        modifier: Modifier = Modifier,
        value: String,
        onValueChange: (String) -> Unit,
        placeholderText: String = "",
        enabled: Boolean = true,
        trailingIcon: @Composable (() -> Unit)? = null,
        fontWeight: FontWeight = FontWeight.Normal,
        fontSize: TextUnit = MaterialTheme.typography.body2.fontSize,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default
    ) {

        LaunchedEffect(viewModel.tpin){
            viewModel.user = viewModel.dao.regularUser
        }

        OutlinedTextField(
            modifier = modifier.padding(vertical = 2.sdp, horizontal = 2.sdp),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            keyboardOptions = keyboardOptions,
            singleLine = true,
            enabled = enabled,
            trailingIcon = trailingIcon,
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colors.onSurface,
                fontSize = fontSize,
                fontWeight = fontWeight,
            ),
            placeholder = {
                Text(
                    text = placeholderText,
                )
            },
        )
    }
}