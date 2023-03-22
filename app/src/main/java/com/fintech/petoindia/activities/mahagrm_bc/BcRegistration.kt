package com.fintech.petoindia.activities.mahagrm_bc

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintech.petoindia.clean.common.BaseComponentAct
import com.fintech.petoindia.clean.util.common.BasicSelectView
import com.fintech.petoindia.clean.util.common.BasicTextView
import com.fintech.petoindia.clean.util.sdp
import com.fintech.petoindia.ui.theme.MyGreen
import com.fintech.petoindia.ui.theme.MyRed
import com.fintech.petoindia.ui.theme.YespayTheme
import com.fintech.petoindia.util.Base64Encoder
import com.fintech.petoindia.util.DisplayMessageUtil
import com.fintech.petoindia.util.PathsInformation
import com.fintech.petoindia.util.ViewUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BcRegistration : BaseComponentAct() {

    val viewModel by viewModels<BcViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YespayTheme(darkTheme = false) {
                BcRegistrationScreen()
            }
        }
    }


    @Composable
    fun BcRegistrationScreen(
        context: Context = LocalContext.current
    ) {
        val kyc1Gallery = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            if (uri != null) {
                if (ViewUtils.whatIsFileSize(uri, context)) {
                    viewModel.kyc1.value = Base64Encoder.encodeImage(context, uri)
                    viewModel.kyc1Name.value = PathsInformation.getNameFromURI(context, uri)
                } else {
                    DisplayMessageUtil.error(context, "Image should be lesser than 200 KB")
                }
            }
        }
        val kyc2Gallery = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            if (uri != null) {
                if (ViewUtils.whatIsFileSize(uri, context)) {
                    viewModel.kyc2.value = Base64Encoder.encodeImage(context, uri)
                    viewModel.kyc2Name.value = PathsInformation.getNameFromURI(context, uri)
                } else {
                    DisplayMessageUtil.error(context, "Image should be lesser than 200 KB")
                }
            }
        }
        val kyc3Gallery = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            if (uri != null) {
                if (ViewUtils.whatIsFileSize(uri, context)) {
                    viewModel.kyc3.value = Base64Encoder.encodeImage(context, uri)
                    viewModel.kyc3Name.value = PathsInformation.getNameFromURI(context, uri)
                } else {
                    DisplayMessageUtil.error(context, "Image should be lesser than 200 KB")
                }
            }
        }
        val kyc4Gallery = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            if (uri != null) {
                if (ViewUtils.whatIsFileSize(uri, context)) {
                    viewModel.kyc4.value = Base64Encoder.encodeImage(context, uri)
                    viewModel.kyc4Name.value = PathsInformation.getNameFromURI(context, uri)
                } else {
                    DisplayMessageUtil.error(context, "Image should be lesser than 200 KB")
                }
            }
        }

        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Initial onboard")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressed()
                            }

                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    contentColor = Color.White,
                    backgroundColor = if (viewModel.isRegister_able()) MyGreen else MyRed
                ) {
                    Button(
                        modifier = Modifier.fillMaxSize(),
                        colors = ButtonDefaults
                            .buttonColors(backgroundColor = if (viewModel.isRegister_able()) MyGreen else MyRed),
                        onClick = {
                            if (viewModel.canBeRegistered()) {
                                viewModel.register(context)
                            }
                        }
                    ) {
                        Text(text = if (viewModel.isRegister_able()) "Onboard" else "Enter all field properly")

                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 2.sdp, horizontal = 30.sdp)
                    .verticalScroll(rememberScrollState())
            ) {

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "First Name",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicTextView(
                        viewModel.bc_f_name,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                        ), maxLength = 50
                    )
                }

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Middle Name",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicTextView(
                        viewModel.bc_m_name,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                        ), maxLength = 50
                    )
                }

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Last Name",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicTextView(
                        viewModel.bc_l_name,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                        ), maxLength = 50
                    )
                }

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Email ID",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicTextView(
                        viewModel.emailid,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                        ), maxLength = 50
                    )
                }

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Mobile number",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicTextView(
                        viewModel.phone1,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number,
                        ), maxLength = 10
                    )
                }

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Alternate Mobile number",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicTextView(
                        viewModel.phone2,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number,
                        ), maxLength = 10
                    )
                }

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Date of Birth",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicSelectView(
                        viewModel.bc_dob,
                        modifier = Modifier.clickable {
                            viewModel.selectDateOfBirth(context)
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.EditCalendar,
                                contentDescription = "calendar"
                            )
                        }
                    )
                }



                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Select State",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicSelectView(
                        viewModel.state, modifier = Modifier.clickable {
                            viewModel.selectSate(context)
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Select District",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicSelectView(viewModel.district, modifier = Modifier.clickable {
                        viewModel.selectDistrict(context)
                    }, trailingIcon = {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                    }
                    )
                }

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Address",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicTextView(
                        viewModel.bc_address,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                        ), maxLength = 100
                    )
                }

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Block",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicTextView(
                        viewModel.bc_block,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                        ), maxLength = 50
                    )
                }

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "City",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicTextView(
                        viewModel.bc_city,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                        ), maxLength = 50
                    )
                }

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Landmark",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicTextView(
                        viewModel.bc_landmark,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                        ), maxLength = 50
                    )
                }

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Location",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicTextView(
                        viewModel.bc_loc,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                        ), maxLength = 50
                    )
                }

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Mohalla",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicTextView(
                        viewModel.bc_mohhalla,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                        ), maxLength = 50
                    )
                }

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Pan Number",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicTextView(
                        viewModel.bc_pan,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Characters
                        ), maxLength = 20
                    )
                }

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Pin Code",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicTextView(
                        viewModel.bc_pincode,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number,
                        ), maxLength = 20
                    )
                }

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Location Type",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicSelectView(viewModel.locationType, modifier = Modifier.clickable {
                        viewModel.selectLocationType(context)
                    }, trailingIcon = {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                    })
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Select Shop Type",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicSelectView(viewModel.shopType, modifier = Modifier.clickable {
                        viewModel.selectShopType(context)
                    }, trailingIcon = {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                    })
                }

                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Shop Name",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicTextView(
                        viewModel.shopname,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                        ), maxLength = 40
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Select Shop Type",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicSelectView(viewModel.shopType, modifier = Modifier.clickable {
                        viewModel.selectShopType(context)
                    }, trailingIcon = {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                    })
                }

                if (viewModel.isShopTypeIsOther()) {
                    Column(
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 3.dp),
                            text = "Shop Type Name",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        BasicTextView(
                            viewModel.otherShopName,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Text,
                            ), maxLength = 40
                        )
                    }
                }


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Select Qualification",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicSelectView(viewModel.qualification, modifier = Modifier.clickable {
                        viewModel.selectQualification(context)
                    }, trailingIcon = {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                    })
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Select Population",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicSelectView(viewModel.population, modifier = Modifier.clickable {
                        viewModel.selectPopulation(context)
                    }, trailingIcon = {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                    })
                }


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Upload Identity Proof",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicSelectView(viewModel.kyc1Name, modifier = Modifier.clickable {
                        kyc1Gallery.launch("image/*")
                    })
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Upload Address Proof",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicSelectView(viewModel.kyc2Name, modifier = Modifier.clickable {
                        kyc2Gallery.launch("image/*")
                    })
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Upload Shop Photo",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicSelectView(viewModel.kyc3Name, modifier = Modifier.clickable {
                        kyc3Gallery.launch("image/*")
                    })
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 3.dp),
                        text = "Upload Passport Size Photo",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    BasicSelectView(viewModel.kyc4Name, modifier = Modifier.clickable {
                        kyc4Gallery.launch("image/*")
                    })
                }
            }
        }
    }

}