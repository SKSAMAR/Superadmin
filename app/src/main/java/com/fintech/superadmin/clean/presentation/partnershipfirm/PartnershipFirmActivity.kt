package com.fintech.superadmin.clean.presentation.partnershipfirm

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
class PartnershipFirmActivity : BaseComponentAct() {

    private val viewModel by viewModels<PartnershipFirmViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(
                    color = MaterialTheme.colors.surface
                ) {
                    PartnershipFrimScreen()
                }
            }
        }
    }


    @Composable
    private fun PartnershipFrimScreen() {
        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Partnership Firm")
                    }, navigationIcon = {
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
                                        this@PartnershipFirmActivity,
                                        LegalsDetailsReceiptActivity::class.java
                                    )
                                intent.putExtra("info", "partnership_firm")
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
                            viewModel.validateTheForm(this@PartnershipFirmActivity)
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
                        createOutlinedTextFieldState("State", x_state) { x_state = it }
                        createOutlinedTextField("Customer Address", cus_address) {
                            cus_address = it
                        }
                        createOutlinedTextField("Name of Firm", name_firm) { name_firm = it }
                        createOutlinedTextField(
                            "Register Address",
                            register_address
                        ) { register_address = it }
                        createOutlinedTextFieldDob(
                            "Date of Registration",
                            date_registration
                        ) { date_registration = it }
                        createOutlinedTextField("Ratio", ratio) { ratio = it }
                        createOutlinedTextField("Object of Firm", object_firm) { object_firm = it }

                        // Partner 1
                        createOutlinedTextField("Partner 1 First Name", p_fname) { p_fname = it }
                        createOutlinedTextField(
                            "Partner 1 Father Name",
                            p_fathername
                        ) { p_fathername = it }
                        createOutlinedTextFieldDob("Partner 1 Date of Birth", p_dob) { p_dob = it }
                        createOutlinedTextField(
                            "Partner 1 Residence Address",
                            p_residence_address
                        ) { p_residence_address = it }

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p_photo, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p_photo = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 1 Passport Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p_aadhar_pic, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p_aadhar_pic = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 1 Aadhaar Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p_sign, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p_sign = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 1 Signature Photo")
                            }
                        )


                        // Partner 2
                        createOutlinedTextField("Partner 2 First Name", p2_fname) { p2_fname = it }
                        createOutlinedTextField(
                            "Partner 2 Father Name",
                            p2_fathername
                        ) { p2_fathername = it }
                        createOutlinedTextFieldDob("Partner 2 Date of Birth", p2_dob) {
                            p2_dob = it
                        }
                        createOutlinedTextField(
                            "Partner 2 Residence Address",
                            p2_residence_address
                        ) { p2_residence_address = it }


                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p_photo2, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p_photo2 = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 2 Passport Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p_aadhar_pic2, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p_aadhar_pic2 = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 2 Aadhaar Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p2_sign, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p2_sign = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 2 Signature Photo")
                            }
                        )


                        // Partner 3
                        createOutlinedTextField("Partner 3 First Name", p3_fname) { p3_fname = it }
                        createOutlinedTextField(
                            "Partner 3 Father Name",
                            p3_fathername
                        ) { p3_fathername = it }
                        createOutlinedTextFieldDob("Partner 3 Date of Birth", p3_dob) {
                            p3_dob = it
                        }
                        createOutlinedTextField(
                            "Partner 3 Residence Address",
                            p3_residence_address
                        ) { p3_residence_address = it }


                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p_photo3, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p_photo3 = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 3 Passport Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p_aadhar_pic3, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p_aadhar_pic3 = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 3 Aadhaar Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p3_sign, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p3_sign = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 3 Signature Photo")
                            }
                        )

                        // Partner 4
                        createOutlinedTextField("Partner 4 First Name", p4_fname) { p4_fname = it }
                        createOutlinedTextField(
                            "Partner 4 Father Name",
                            p4_fathername
                        ) { p4_fathername = it }
                        createOutlinedTextFieldDob("Partner 4 Date of Birth", p4_dob) {
                            p4_dob = it
                        }
                        createOutlinedTextField(
                            "Partner 4 Residence Address",
                            p4_residence_address
                        ) { p4_residence_address = it }


                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p_photo4, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p_photo4 = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 4 Passport Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p_aadhar_pic4, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p_aadhar_pic4 = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 4 Aadhaar Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p4_sign, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p4_sign = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 4 Signature Photo")
                            }
                        )


                        // Partner 5
                        createOutlinedTextField("Partner 5 First Name", p5_fname) { p5_fname = it }
                        createOutlinedTextField(
                            "Partner 5 Father Name",
                            p5_fathername
                        ) { p5_fathername = it }
                        createOutlinedTextFieldDob("Partner 5 Date of Birth", p5_dob) {
                            p5_dob = it
                        }
                        createOutlinedTextField(
                            "Partner 5 Residence Address",
                            p5_residence_address
                        ) { p5_residence_address = it }

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p_photo5, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p_photo5 = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 5 Passport Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p_aadhar_pic5, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p_aadhar_pic5 = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 5 Aadhaar Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p5_sign, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p5_sign = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 5 Signature Photo")
                            }
                        )

                        // Partner 6
                        createOutlinedTextField("Partner 6 First Name", p6_fname) { p6_fname = it }
                        createOutlinedTextField(
                            "Partner 6 Father Name",
                            p6_fathername
                        ) { p6_fathername = it }
                        createOutlinedTextFieldDob("Partner 6 Date of Birth", p6_dob) {
                            p6_dob = it
                        }
                        createOutlinedTextField(
                            "Partner 6 Residence Address",
                            p6_residence_address
                        ) { p6_residence_address = it }

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p_photo6, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p_photo6 = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 6 Passport Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p_aadhar_pic6, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p_aadhar_pic6 = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 6 Aadhaar Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.p6_sign, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.p6_sign = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Partner 6 Signature Photo")
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