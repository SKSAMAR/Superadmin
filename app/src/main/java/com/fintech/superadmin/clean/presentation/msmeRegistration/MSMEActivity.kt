package com.fintech.superadmin.clean.presentation.msmeRegistration

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Report
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.fintech.superadmin.activities.transactionDetails.LegalsDetailsReceiptActivity
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.presentation.gstregistration.OutlinedTextFieldDOB
import com.fintech.superadmin.clean.presentation.gstregistration.OutlinedTextFieldImage
import com.fintech.superadmin.clean.presentation.gstregistration.OutlinedTextFieldList
import com.fintech.superadmin.clean.presentation.gstregistration.OutlinedTextFieldState
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MSMEActivity : BaseComponentAct() {

    private val viewModel by viewModels<MSMEViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(
                    color = MaterialTheme.colors.surface
                ) {
                    MSMEScreen()
                }
            }
        }
    }


    @Composable
    private fun MSMEScreen() {
        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("MSME Registration")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack, contentDescription = ""
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                val intent =
                                    Intent(
                                        this@MSMEActivity,
                                        LegalsDetailsReceiptActivity::class.java
                                    )
                                intent.putExtra("info", "msme_registration")
                                startActivity(intent)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Report,
                                contentDescription = ""
                            )
                        }
                    }
                )
            }, bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.validateTheForm(this@MSMEActivity)
                        }
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Apply",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }) {
            viewModel.apply {
                BasicScreenState(state = viewModel.state) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.sdp)
                            .verticalScroll(
                                rememberScrollState()
                            )
                    ) {
                        createOutlinedTextField("Customer Name", c_name) { c_name = it }
                        createOutlinedTextField("Customer Number", c_num) { c_num = it }
                        createOutlinedTextField("Company Name", cmnpy_name) { cmnpy_name = it }
                        createOutlinedTextField("Customer Email", cus_email) { cus_email = it }
                        createOutlinedTextField("City", city) { city = it }
                        createOutlinedTextFieldState("State", x_state) { x_state = it }
                        createOutlinedTextField("Customer Address", cus_address) {
                            cus_address = it
                        }

                        // Add more similar fields for Official variables...
                        createOutlinedTextField("Aadhar Number", aadhar_no) { aadhar_no = it }
                        createOutlinedTextField("Entrepreneur", entrepreneur) { entrepreneur = it }
                        createOutlinedTextFieldList(
                            "Category",
                            category,
                            listOf("General", "OBC", "SC", "ST")
                        ) { category = it }
                        createOutlinedTextFieldList(
                            "Gender",
                            gender,
                            listOf("Male", "Female")
                        ) { gender = it }
                        createOutlinedTextFieldList(
                            "Handicapped",
                            handicaped,
                            listOf("Yes", "No")
                        ) { handicaped = it }
                        createOutlinedTextField(
                            "Entrepreneur Business",
                            enterpr_buss
                        ) { enterpr_buss = it }
                        createOutlinedTextFieldList(
                            "Organisation", organisation, listOf(
                                "Proprietorship",
                                "HUF",
                                "Company",
                                "Partnership Firm",
                                "Private Limited Company",
                                "Public Limited Company",
                                "LLP"
                            )
                        ) { organisation = it }
                        createOutlinedTextField("PAN Number", pan_no) { pan_no = it }
                        createOutlinedTextField("Block", block) { block = it }
                        createOutlinedTextField("Building", building) { building = it }
                        createOutlinedTextField("Post Office", postoffice) { postoffice = it }
                        createOutlinedTextFieldState("State", o_state) { o_state = it }
                        createOutlinedTextField("Official Block", o_block) { o_block = it }
                        createOutlinedTextField("Official Building", o_building) { o_building = it }
                        createOutlinedTextField("Official Post Office", o_postoffice) {
                            o_postoffice = it
                        }
                        createOutlinedTextFieldState("Official State", o_state) { o_state = it }
                        createOutlinedTextField("Official City", o_city) { o_city = it }
                        createOutlinedTextField("Official Mobile", o_mobile) { o_mobile = it }
                        createOutlinedTextField("Official Email", o_email) { o_email = it }
                        createOutlinedTextField("Account Number", account_no) { account_no = it }
                        createOutlinedTextField("IFSC Code", ifsc_code) { ifsc_code = it }
                        createOutlinedTextFieldList(
                            "Business Type",
                            oth_buss_type,
                            listOf("Manufacturing", "Services", "Trading")
                        ) { oth_buss_type = it }
                        createOutlinedTextField(
                            "Commodities Supplied",
                            commoditiessupplied
                        ) { commoditiessupplied = it }
                        createOutlinedTextField("D-Service", d_service) { d_service = it }
                        createOutlinedTextField("Investment B", investment_b) { investment_b = it }

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.proprietor, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.proprietor = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Proprietor Photo")
                            }
                        )
                    }
                }
            }
        }
    }


    @Composable
    fun createOutlinedTextField(
        placeholderText: String,
        variable: String,
        onValueChange: (String) -> Unit,
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.sdp),
            value = variable,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = {
                Text(text = placeholderText)
            }
        )
    }

    @Composable
    fun createOutlinedTextFieldDob(
        placeholderText: String,
        variable: String,
        onValueChange: (String) -> Unit,
    ) {
        OutlinedTextFieldDOB(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.sdp),
            value = variable,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = {
                Text(text = placeholderText)
            }
        )
    }


    @Composable
    fun createOutlinedTextFieldState(
        placeholderText: String,
        variable: String,
        onValueChange: (String) -> Unit,
    ) {
        OutlinedTextFieldState(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.sdp),
            value = variable,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = {
                Text(text = placeholderText)
            }
        )
    }


    @Composable
    fun createOutlinedTextFieldGender(
        placeholderText: String,
        variable: String,
        onValueChange: (String) -> Unit,
    ) {
        OutlinedTextFieldList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.sdp),
            value = variable,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = {
                Text(text = placeholderText)
            },
            list = listOf("Male", "Female", "Other")
        )
    }

    @Composable
    fun createOutlinedTextFieldList(
        placeholderText: String,
        variable: String,
        list: List<String>,
        onValueChange: (String) -> Unit,
    ) {
        OutlinedTextFieldList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.sdp),
            value = variable,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = {
                Text(text = placeholderText)
            },
            list = list
        )
    }

}