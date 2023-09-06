package com.fintech.superadmin.clean.presentation.gstfiling

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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
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
class GSTFilingActivity : BaseComponentAct() {

    val viewModel by viewModels<GSTFilingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    GSTFilingScreen()
                }
            }
        }
    }


    @Composable
    private fun GSTFilingScreen() {
        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("GST Filling")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = ""
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                val intent = Intent(this@GSTFilingActivity, LegalsDetailsReceiptActivity::class.java)
                                intent.putExtra("info", "gst_filling")
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
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.validateTheForm(this@GSTFilingActivity) }
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Apply",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        ) {
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
                        createOutlinedTextField("Customer Address", cus_address) {
                            cus_address = it
                        }
                        createOutlinedTextField("Trade Name", trade_name) { trade_name = it }
                        createOutlinedTextFieldState("State", x_state) { x_state = it }
                        createOutlinedTextField("GSTIN", gstin) { gstin = it }
                        createOutlinedTextField("Contact Person", contact_p) { contact_p = it }
                        createOutlinedTextField("Tax Payer Mobile Number", tax_mb_no) {
                            tax_mb_no = it
                        }
                        createOutlinedTextField("Tax Payer Email ID", tax_emailId) {
                            tax_emailId = it
                        }
                        createOutlinedTextFieldList(
                            "Tax Payer Type", tax_type, listOf(
                                "Individual",
                                "Company",
                                "Partnership Firm",
                                "Private Limited Company",
                                "Public Limited Company",
                                "LLP"
                            )
                        ) { tax_type = it }
                        createOutlinedTextField(
                            "Tax Payer Filling Period",
                            tax_filling_period
                        ) { tax_filling_period = it }
                        createOutlinedTextFieldList(
                            "GSTR", gstr, listOf(
                                "GSTR 1",
                                "GSTR 3B",
                                "CMP-08",
                                "OTHER"
                            )
                        ) { gstr = it }
                        createOutlinedTextField("Turn Over", turn_over) { turn_over = it }
                        createOutlinedTextField("GST User ID", gst_user_id) { gst_user_id = it }
                        createOutlinedTextField(
                            "GST Portal Password",
                            gst_portal_pass
                        ) { gst_portal_pass = it }
                        createOutlinedTextField("Sales Tax Payer Value", sales_tax_val) {
                            sales_tax_val = it
                        }
                        createOutlinedTextFieldList(
                            "IGST Percent", igst_percent, listOf(
                                "1",
                                "5",
                                "12",
                                "18",
                                "28"
                            )
                        ) { igst_percent = it }
                        createOutlinedTextFieldList(
                            "CGST Percent", cgst_percent, listOf(
                                "1",
                                "5",
                                "12",
                                "18",
                                "28"
                            )
                        ) { cgst_percent = it }
                        createOutlinedTextFieldList(
                            "SGST Percent", sgst_percent, listOf(
                                "1",
                                "5",
                                "12",
                                "18",
                                "28"
                            )
                        ) { sgst_percent = it }
                        createOutlinedTextField(
                            "Exempted Supply",
                            exempted_supply
                        ) { exempted_supply = it }

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.docs1, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.docs1 = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Document 1 Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.docs2, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.docs2 = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Document 2 Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.docs3, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.docs3 = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Document 3 Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.docs4, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.docs4 = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Document 4 Photo")
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