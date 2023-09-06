package com.fintech.superadmin.clean.presentation.companyIncoporation

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
class CompanyIncoActivity : BaseComponentAct() {

    val viewModel by viewModels<CompanyIncoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    CompanyInCooperation()
                }
            }
        }
    }


    @Composable
    private fun CompanyInCooperation() {
        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Company InCooperation")
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
                                    Intent(this@CompanyIncoActivity, LegalsDetailsReceiptActivity::class.java)
                                intent.putExtra("info", "company_incorporation")
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
                            viewModel.validateTheForm(this@CompanyIncoActivity)
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
                        createOutlinedTextField("Preference 1", prefernce1) { prefernce1 = it }
                        createOutlinedTextField("Preference 2", prefernce2) { prefernce2 = it }
                        createOutlinedTextField("Activity", activity) { activity = it }

                        // Repeat the pattern for Director1, Director2, Director3, Director4, Director5, Director6 variables...
                        createOutlinedTextField("Director1 First Name", d_fname) { d_fname = it }
                        createOutlinedTextField("Director1 Middle Name", d_mname) { d_mname = it }
                        createOutlinedTextField("Director1 Last Name", d_lname) { d_lname = it }
                        createOutlinedTextFieldGender("Director1 Gender", d_gender) {
                            d_gender = it
                        }
                        createOutlinedTextFieldDob("Director1 Date of Birth", d_dob) { d_dob = it }
                        createOutlinedTextField(
                            "Director1 Contact Number",
                            d_contact_no
                        ) { d_contact_no = it }
                        createOutlinedTextField("Director1 Email", d_email) { d_email = it }
                        createOutlinedTextField(
                            "Director1 Father's Name",
                            d_father_name
                        ) { d_father_name = it }
                        createOutlinedTextField(
                            "Director1 Father's Middle Name",
                            d_father_mname
                        ) { d_father_mname = it }
                        createOutlinedTextField(
                            "Director1 Father's Last Name",
                            d_father_lname
                        ) { d_father_lname = it }


                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d_passportphoto, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d_passportphoto = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 1 Passport Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d_aadhar_pic, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d_aadhar_pic = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 1 Aadhaar Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d_aadhar_pic, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d_aadhar_pic = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 1 Aadhaar Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d_pancard, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d_pancard = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 1 PAN Photo")
                            }
                        )


                        // Repeat for Director2 variables...
                        createOutlinedTextField("Director2 First Name", d2_fname) { d2_fname = it }
                        createOutlinedTextField("Director2 Middle Name", d2_mname) { d2_mname = it }
                        createOutlinedTextField("Director2 Last Name", d2_lname) { d2_lname = it }
                        createOutlinedTextFieldGender("Director2 Gender", d2_gender) {
                            d2_gender = it
                        }
                        createOutlinedTextFieldDob("Director2 Date of Birth", d2_dob) {
                            d2_dob = it
                        }
                        createOutlinedTextField(
                            "Director2 Contact Number",
                            d2_contact_no
                        ) { d2_contact_no = it }
                        createOutlinedTextField("Director2 Email", d2_email) { d2_email = it }
                        createOutlinedTextField(
                            "Director2 Father's Name",
                            d2_father_name
                        ) { d2_father_name = it }
                        createOutlinedTextField(
                            "Director2 Father's Middle Name",
                            d2_father_mname
                        ) { d2_father_mname = it }
                        createOutlinedTextField(
                            "Director2 Father's Last Name",
                            d2_father_lname
                        ) { d2_father_lname = it }


                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d2_passportphoto, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d2_passportphoto = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 2 Passport Photo")
                            }
                        )



                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d2_aadhar_pic, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d2_aadhar_pic = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 2 Aadhaar Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d2_pancard, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d2_pancard = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 2 PAN Photo")
                            }
                        )


                        // Repeat for Director3 variables...
                        createOutlinedTextField("Director3 First Name", d3_fname) { d3_fname = it }
                        createOutlinedTextField("Director3 Middle Name", d3_mname) { d3_mname = it }
                        createOutlinedTextField("Director3 Last Name", d3_lname) { d3_lname = it }
                        createOutlinedTextFieldGender("Director3 Gender", d3_gender) {
                            d3_gender = it
                        }
                        createOutlinedTextFieldDob("Director3 Date of Birth", d3_dob) {
                            d3_dob = it
                        }
                        createOutlinedTextField(
                            "Director3 Contact Number",
                            d3_contact_no
                        ) { d3_contact_no = it }
                        createOutlinedTextField("Director3 Email", d3_email) { d3_email = it }
                        createOutlinedTextField(
                            "Director3 Father's Name",
                            d3_father_name
                        ) { d3_father_name = it }
                        createOutlinedTextField(
                            "Director3 Father's Middle Name",
                            d3_father_mname
                        ) { d3_father_mname = it }
                        createOutlinedTextField(
                            "Director3 Father's Last Name",
                            d3_father_lname
                        ) { d3_father_lname = it }


                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d2_passportphoto, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d2_passportphoto = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 2 Passport Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d3_aadhar_pic, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d3_aadhar_pic = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 3 Aadhaar Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d3_pancard, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d3_pancard = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 3 PAN Photo")
                            }
                        )


                        // Repeat for Director4 variables...
                        createOutlinedTextField("Director4 First Name", d4_fname) { d4_fname = it }
                        createOutlinedTextField("Director4 Middle Name", d4_mname) { d4_mname = it }
                        createOutlinedTextField("Director4 Last Name", d4_lname) { d4_lname = it }
                        createOutlinedTextFieldGender("Director4 Gender", d4_gender) {
                            d4_gender = it
                        }
                        createOutlinedTextFieldDob("Director4 Date of Birth", d4_dob) {
                            d4_dob = it
                        }
                        createOutlinedTextField(
                            "Director4 Contact Number",
                            d4_contact_no
                        ) { d4_contact_no = it }
                        createOutlinedTextField("Director4 Email", d4_email) { d4_email = it }
                        createOutlinedTextField(
                            "Director4 Father's Name",
                            d4_father_name
                        ) { d4_father_name = it }
                        createOutlinedTextField(
                            "Director4 Father's Middle Name",
                            d4_father_mname
                        ) { d4_father_mname = it }
                        createOutlinedTextField(
                            "Director4 Father's Last Name",
                            d4_father_lname
                        ) { d4_father_lname = it }


                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d4_passportphoto, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d4_passportphoto = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 4 Passport Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d4_aadhar_pic, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d4_aadhar_pic = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 4 Aadhaar Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d4_pancard, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d4_pancard = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 4 PAN Photo")
                            }
                        )


                        // Repeat for Director5 variables...
                        createOutlinedTextField("Director5 First Name", d5_fname) { d5_fname = it }
                        createOutlinedTextField("Director5 Middle Name", d5_mname) { d5_mname = it }
                        createOutlinedTextField("Director5 Last Name", d5_lname) { d5_lname = it }
                        createOutlinedTextFieldGender("Director5 Gender", d5_gender) {
                            d5_gender = it
                        }
                        createOutlinedTextFieldDob("Director5 Date of Birth", d5_dob) {
                            d5_dob = it
                        }
                        createOutlinedTextField(
                            "Director5 Contact Number",
                            d5_contact_no
                        ) { d5_contact_no = it }
                        createOutlinedTextField("Director5 Email", d5_email) { d5_email = it }
                        createOutlinedTextField(
                            "Director5 Father's Name",
                            d5_father_name
                        ) { d5_father_name = it }
                        createOutlinedTextField(
                            "Director5 Father's Middle Name",
                            d5_father_mname
                        ) { d5_father_mname = it }
                        createOutlinedTextField(
                            "Director5 Father's Last Name",
                            d5_father_lname
                        ) { d5_father_lname = it }


                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d5_passportphoto, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d5_passportphoto = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 5 Passport Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d5_aadhar_pic, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d5_aadhar_pic = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 5 Aadhaar Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d5_pancard, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d5_pancard = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 5 PAN Photo")
                            }
                        )

                        // Repeat for Director6 variables...
                        createOutlinedTextField("Director6 First Name", d6_fname) { d6_fname = it }
                        createOutlinedTextField("Director6 Middle Name", d6_mname) { d6_mname = it }
                        createOutlinedTextField("Director6 Last Name", d6_lname) { d6_lname = it }
                        createOutlinedTextFieldGender("Director6 Gender", d6_gender) {
                            d6_gender = it
                        }
                        createOutlinedTextFieldDob("Director6 Date of Birth", d6_dob) {
                            d6_dob = it
                        }
                        createOutlinedTextField(
                            "Director6 Contact Number",
                            d6_contact_no
                        ) { d6_contact_no = it }
                        createOutlinedTextField("Director6 Email", d6_email) { d6_email = it }
                        createOutlinedTextField(
                            "Director6 Father's Name",
                            d6_father_name
                        ) { d6_father_name = it }
                        createOutlinedTextField(
                            "Director6 Father's Middle Name",
                            d6_father_mname
                        ) { d6_father_mname = it }
                        createOutlinedTextField(
                            "Director6 Father's Last Name",
                            d6_father_lname
                        ) { d6_father_lname = it }

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d6_passportphoto, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d6_passportphoto = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 6 Passport Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d6_aadhar_pic, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d6_aadhar_pic = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 6 Aadhaar Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d6_pancard, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d6_pancard = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 6 PAN Photo")
                            }
                        )

                        createOutlinedTextField(
                            "Company Registration",
                            copmany_registration
                        ) { copmany_registration = it }
                        createOutlinedTextFieldList(
                            "Company Type",
                            company_type,
                            list = listOf("PVT LTD", "OPC")
                        ) { company_type = it }
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