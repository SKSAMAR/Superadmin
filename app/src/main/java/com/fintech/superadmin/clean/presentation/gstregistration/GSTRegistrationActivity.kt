package com.fintech.superadmin.clean.presentation.gstregistration

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.fintech.superadmin.activities.transactionDetails.LegalsDetailsReceiptActivity
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.flight.presentation.common.DateDialogSys.showDateDialog
import com.fintech.superadmin.flight.presentation.home.component.toSpecificDepartDates
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import com.fintech.superadmin.util.ViewUtils
import com.fintech.superadmin.util.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GSTRegistrationActivity : BaseComponentAct() {

    val viewModel by viewModels<GSTRegistrationViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    GSTRegistrationScreen()
                }
            }
        }
    }


    @Composable
    private fun GSTRegistrationScreen() {
        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("GST Registration")
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
                                val intent = Intent(
                                    this@GSTRegistrationActivity,
                                    LegalsDetailsReceiptActivity::class.java
                                )
                                intent.putExtra("info", "gst_registration")
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
                        .clickable { viewModel.validateTheForm(this@GSTRegistrationActivity) }
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
                        createOutlinedTextFieldState("State", x_state) { x_state = it }
                        createOutlinedTextField("Customer Address", cus_address) {
                            cus_address = it
                        }
                        createOutlinedTextField("PAN Name", pan_name) { pan_name = it }
                        createOutlinedTextField("PAN Number", pan_no) { pan_no = it }
                        createOutlinedTextField("Contact Person", contact_p) { contact_p = it }
                        createOutlinedTextField("Mobile", mobile) { mobile = it }
                        createOutlinedTextField("Email", email) { email = it }
                        createOutlinedTextField("Business Type", bussiness_type) {
                            bussiness_type = it
                        }
                        createOutlinedTextFieldList(
                            "Constitution of Business",
                            consitituion_of_buss,
                            listOf(
                                "Proprietorship",
                                "HUF",
                                "Company",
                                "Partnership Firm",
                                "Private Limited Company",
                                "Public Limited Company",
                                "LLP"
                            )
                        ) { consitituion_of_buss = it }
                        createOutlinedTextFieldList(
                            "Option of Composition",
                            option_of_composition,
                            listOf("Yes", "No")
                        ) { option_of_composition = it }
                        createOutlinedTextField(
                            "Commencement Business",
                            commenceement_b
                        ) { commenceement_b = it }

                        // Create similar fields for Director 1
                        createOutlinedTextField("Director 1 First Name", d_fname) { d_fname = it }
                        createOutlinedTextField("Director 1 Middle Name", d_mname) { d_mname = it }
                        createOutlinedTextField("Director 1 Last Name", d_lname) { d_lname = it }
                        createOutlinedTextFieldGender("Director 1 Gender", d_gender) {
                            d_gender = it
                        }
                        createOutlinedTextFieldDob("Director 1 Date of Birth", d_dob) { d_dob = it }
                        createOutlinedTextField(
                            "Director 1 Contact Number",
                            d_contact_no
                        ) { d_contact_no = it }
                        createOutlinedTextField("Director 1 Email", d_email) { d_email = it }
                        createOutlinedTextField(
                            "Director 1 Aadhar Number",
                            d_aadhar_no
                        ) { d_aadhar_no = it }
                        createOutlinedTextField("Director 1 Address", d_address) { d_address = it }
                        createOutlinedTextField(
                            "Director 1 Building Number",
                            d_building_no
                        ) { d_building_no = it }
                        createOutlinedTextField(
                            "Director 1 Post Office",
                            d_postoffice
                        ) { d_postoffice = it }
                        createOutlinedTextField("Director 1 Locality", d_locality) {
                            d_locality = it
                        }
                        createOutlinedTextFieldState("Director 1 State", d_state) { d_state = it }
                        createOutlinedTextField("Director 1 City", d_city) { d_city = it }

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d_profile, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d_profile = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 1 Profile Photo")
                            }
                        )

                        // Director 2
                        createOutlinedTextField("Director 2 First Name", d2_fname) { d2_fname = it }
                        createOutlinedTextField("Director 2 Middle Name", d2_mname) {
                            d2_mname = it
                        }
                        createOutlinedTextField("Director 2 Last Name", d2_lname) { d2_lname = it }
                        createOutlinedTextFieldGender("Director 2 Gender", d2_gender) {
                            d2_gender = it
                        }
                        createOutlinedTextFieldDob("Director 2 Date of Birth", d2_dob) {
                            d2_dob = it
                        }
                        createOutlinedTextField(
                            "Director 2 Contact Number",
                            d2_contact_no
                        ) { d2_contact_no = it }
                        createOutlinedTextField("Director 2 Email", d2_email) { d2_email = it }
                        createOutlinedTextField(
                            "Director 2 Aadhar Number",
                            d2_aadhar_no
                        ) { d2_aadhar_no = it }
                        createOutlinedTextField("Director 2 Address", d2_address) {
                            d2_address = it
                        }
                        createOutlinedTextField(
                            "Director 2 Building Number",
                            d2_building_no
                        ) { d2_building_no = it }
                        createOutlinedTextField(
                            "Director 2 Post Office",
                            d2_postoffice
                        ) { d2_postoffice = it }
                        createOutlinedTextField("Director 2 Locality", d2_locality) {
                            d2_locality = it
                        }
                        createOutlinedTextFieldState("Director 2 State", d2_state) { d2_state = it }
                        createOutlinedTextField("Director 2 City", d2_city) { d2_city = it }

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d2_profile, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d2_profile = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 2 Profile Photo")
                            }
                        )


                        createOutlinedTextField("Director 3 First Name", d3_fname) { d3_fname = it }
                        createOutlinedTextField("Director 3 Middle Name", d3_mname) {
                            d3_mname = it
                        }
                        createOutlinedTextField("Director 3 Last Name", d3_lname) { d3_lname = it }
                        createOutlinedTextFieldGender("Director 3 Gender", d3_gender) {
                            d3_gender = it
                        }
                        createOutlinedTextFieldDob("Director 3 Date of Birth", d3_dob) {
                            d3_dob = it
                        }
                        createOutlinedTextField(
                            "Director 3 Contact Number",
                            d3_contact_no
                        ) { d3_contact_no = it }
                        createOutlinedTextField("Director 3 Email", d3_email) { d3_email = it }
                        createOutlinedTextField(
                            "Director 3 Aadhar Number",
                            d3_aadhar_no
                        ) { d3_aadhar_no = it }
                        createOutlinedTextField("Director 3 Address", d3_address) {
                            d3_address = it
                        }
                        createOutlinedTextField(
                            "Director 3 Building Number",
                            d3_building_no
                        ) { d3_building_no = it }
                        createOutlinedTextField(
                            "Director 3 Post Office",
                            d3_postoffice
                        ) { d3_postoffice = it }
                        createOutlinedTextField("Director 3 Locality", d3_locality) {
                            d3_locality = it
                        }
                        createOutlinedTextFieldState("Director 3 State", d3_state) { d3_state = it }
                        createOutlinedTextField("Director 3 City", d3_city) { d3_city = it }

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d3_profile, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d3_profile = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 3 Profile Photo")
                            }
                        )


                        // Director 4
                        createOutlinedTextField("Director 4 First Name", d4_fname) { d4_fname = it }
                        createOutlinedTextField("Director 4 Middle Name", d4_mname) {
                            d4_mname = it
                        }
                        createOutlinedTextField("Director 4 Last Name", d4_lname) { d4_lname = it }
                        createOutlinedTextFieldGender("Director 4 Gender", d4_gender) {
                            d4_gender = it
                        }
                        createOutlinedTextFieldDob("Director 4 Date of Birth", d4_dob) {
                            d4_dob = it
                        }
                        createOutlinedTextField(
                            "Director 4 Contact Number",
                            d4_contact_no
                        ) { d4_contact_no = it }
                        createOutlinedTextField("Director 4 Email", d4_email) { d4_email = it }
                        createOutlinedTextField(
                            "Director 4 Aadhar Number",
                            d4_aadhar_no
                        ) { d4_aadhar_no = it }
                        createOutlinedTextField("Director 4 Address", d4_address) {
                            d4_address = it
                        }
                        createOutlinedTextField(
                            "Director 4 Building Number",
                            d4_building_no
                        ) { d4_building_no = it }
                        createOutlinedTextField(
                            "Director 4 Post Office",
                            d4_postoffice
                        ) { d4_postoffice = it }
                        createOutlinedTextField("Director 4 Locality", d4_locality) {
                            d4_locality = it
                        }
                        createOutlinedTextFieldState("Director 4 State", d4_state) { d4_state = it }
                        createOutlinedTextField("Director 4 City", d4_city) { d4_city = it }

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d4_profile, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d4_profile = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 4 Profile Photo")
                            }
                        )

// Director 5
                        createOutlinedTextField("Director 5 First Name", d5_fname) { d5_fname = it }
                        createOutlinedTextField("Director 5 Middle Name", d5_mname) {
                            d5_mname = it
                        }
                        createOutlinedTextField("Director 5 Last Name", d5_lname) { d5_lname = it }
                        createOutlinedTextFieldGender("Director 5 Gender", d5_gender) {
                            d5_gender = it
                        }
                        createOutlinedTextFieldDob("Director 5 Date of Birth", d5_dob) {
                            d5_dob = it
                        }
                        createOutlinedTextField(
                            "Director 5 Contact Number",
                            d5_contact_no
                        ) { d5_contact_no = it }
                        createOutlinedTextField("Director 5 Email", d5_email) { d5_email = it }
                        createOutlinedTextField(
                            "Director 5 Aadhar Number",
                            d5_aadhar_no
                        ) { d5_aadhar_no = it }
                        createOutlinedTextField("Director 5 Address", d5_address) {
                            d5_address = it
                        }
                        createOutlinedTextField(
                            "Director 5 Building Number",
                            d5_building_no
                        ) { d5_building_no = it }
                        createOutlinedTextField(
                            "Director 5 Post Office",
                            d5_postoffice
                        ) { d5_postoffice = it }
                        createOutlinedTextField("Director 5 Locality", d5_locality) {
                            d5_locality = it
                        }
                        createOutlinedTextFieldState("Director 5 State", d5_state) { d5_state = it }
                        createOutlinedTextField("Director 5 City", d5_city) { d5_city = it }

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d5_profile, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d5_profile = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 5 Profile Photo")
                            }
                        )

// Director 6
                        createOutlinedTextField("Director 6 First Name", d6_fname) { d6_fname = it }
                        createOutlinedTextField("Director 6 Middle Name", d6_mname) {
                            d6_mname = it
                        }
                        createOutlinedTextField("Director 6 Last Name", d6_lname) { d6_lname = it }
                        createOutlinedTextFieldGender("Director 6 Gender", d6_gender) {
                            d6_gender = it
                        }
                        createOutlinedTextFieldDob("Director 6 Date of Birth", d6_dob) {
                            d6_dob = it
                        }
                        createOutlinedTextField(
                            "Director 6 Contact Number",
                            d6_contact_no
                        ) { d6_contact_no = it }
                        createOutlinedTextField("Director 6 Email", d6_email) { d6_email = it }
                        createOutlinedTextField(
                            "Director 6 Aadhar Number",
                            d6_aadhar_no
                        ) { d6_aadhar_no = it }
                        createOutlinedTextField("Director 6 Address", d6_address) {
                            d6_address = it
                        }
                        createOutlinedTextField(
                            "Director 6 Building Number",
                            d6_building_no
                        ) { d6_building_no = it }
                        createOutlinedTextField(
                            "Director 6 Post Office",
                            d6_postoffice
                        ) { d6_postoffice = it }
                        createOutlinedTextField("Director 6 Locality", d6_locality) {
                            d6_locality = it
                        }
                        createOutlinedTextFieldState("Director 6 State", d6_state) { d6_state = it }
                        createOutlinedTextField("Director 6 City", d6_city) { d6_city = it }



                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.d6_profile, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.d6_profile = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Director 6 Profile Photo")
                            }
                        )



                        createOutlinedTextField("PPB Address", ppb_address) { ppb_address = it }
                        createOutlinedTextField("PPB Building", ppb_building) { ppb_building = it }
                        createOutlinedTextField(
                            "PPB Post Office",
                            ppb_postoffice
                        ) { ppb_postoffice = it }
                        createOutlinedTextField("PPB Locality", ppb_locality) { ppb_locality = it }
                        createOutlinedTextFieldState("PPB State", ppb_state) { ppb_state = it }
                        createOutlinedTextField("Bank Account", bank_account) { bank_account = it }
                        createOutlinedTextField("IFSC Code", ifsc_code) { ifsc_code = it }
                        createOutlinedTextField("Account Type", acc_type) { acc_type = it }

                        createOutlinedTextField("Product 1", product1) { product1 = it }
                        createOutlinedTextField("Product 2", product2) { product2 = it }
                        createOutlinedTextField("Product 3", product3) { product3 = it }
                        createOutlinedTextField("Product 4", product4) { product4 = it }
                        createOutlinedTextField("Product 5", product5) { product5 = it }

                        createOutlinedTextField("Possession", prossession) { prossession = it }
                        createOutlinedTextField(
                            "Business Activity",
                            buss_activity
                        ) { buss_activity = it }
                        createOutlinedTextField(
                            "Authorized Signatory",
                            authorised_sign
                        ) { authorised_sign = it }
                        createOutlinedTextField("Place", place) { place = it }


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

@Composable
fun ComponentActivity.OutlinedTextFieldState(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean,
    placeholder: @Composable (() -> Unit),
) {
    OutlinedTextField(
        modifier = modifier
            .clickable(indication = null, interactionSource = MutableInteractionSource()) {
                ViewUtils.onSpinnerViewBring("Select State", this, stateList) {
                    onValueChange.invoke(it)
                }
            },
        enabled = false,
        value = value,
        onValueChange = { onValueChange.invoke(it) },
        singleLine = singleLine,
        placeholder = placeholder,
        trailingIcon = {
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(disabledTextColor = MaterialTheme.colors.onSurface)
    )

}

@Composable
fun ComponentActivity.OutlinedTextFieldList(
    modifier: Modifier,
    value: String,
    list: List<String>,
    onValueChange: (String) -> Unit,
    singleLine: Boolean,
    placeholder: @Composable (() -> Unit),
) {
    OutlinedTextField(
        modifier = modifier
            .clickable(indication = null, interactionSource = MutableInteractionSource()) {
                ViewUtils.onSpinnerViewBring("Select", this, list) {
                    onValueChange.invoke(it)
                }
            },
        enabled = false,
        value = value,
        onValueChange = { onValueChange.invoke(it) },
        singleLine = singleLine,
        placeholder = placeholder,
        trailingIcon = {
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(disabledTextColor = MaterialTheme.colors.onSurface)
    )

}


@Composable
fun ComponentActivity.OutlinedTextFieldDOB(
    modifier: Modifier,
    value: String,
    onValueChange: (it: String) -> Unit,
    singleLine: Boolean,
    placeholder: @Composable (() -> Unit),
) {
    OutlinedTextField(
        modifier = modifier
            .clickable(indication = null, interactionSource = MutableInteractionSource()) {
                showDateDialog {
                    toSpecificDepartDates(it, "dd-mm-yyyy") {
                        onValueChange.invoke(it)
                    }
                }
            },
        enabled = false,
        value = value,
        onValueChange = { onValueChange.invoke(it) },
        singleLine = singleLine,
        placeholder = placeholder,
        trailingIcon = {
            Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(disabledTextColor = MaterialTheme.colors.onSurface)
    )



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


@Composable
fun ComponentActivity.OutlinedTextFieldImage(
    modifier: Modifier,
    value: String,
    onValueChange: (it: String, uri: Uri) -> Unit,
    singleLine: Boolean,
    placeholder: @Composable (() -> Unit),
) {

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                ComponentActivity.RESULT_OK -> {
                    data?.data?.let { uri ->
                        onValueChange("Image Selected", uri)
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    showToast(ImagePicker.getError(data))
                }
                else -> {
                    showToast("Task Cancelled")
                }
            }
        }


    OutlinedTextField(
        modifier = modifier
            .clickable(indication = null, interactionSource = MutableInteractionSource()) {
                ImagePicker
                    .with(this)
                    //.crop()
                    .compress(512)
                    .maxResultSize(
                        720,
                        720
                    )
                    .createIntent { intent ->
                        launcher.launch(intent)
                    }
            },
        enabled = false,
        value = if (value.trim().isEmpty()) "Select a valid Image" else value,
        onValueChange = {

        },
        singleLine = singleLine,
        placeholder = placeholder,
        trailingIcon = {
            Icon(imageVector = Icons.Default.Image, contentDescription = null)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(disabledTextColor = MaterialTheme.colors.onSurface)
    )

}

@Composable
fun ComponentActivity.OutlinedTextFieldImage(
    modifier: Modifier,
    value: Uri?,
    onValueChange: (uri: Uri) -> Unit,
    singleLine: Boolean,
    placeholder: @Composable (() -> Unit),
) {

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                ComponentActivity.RESULT_OK -> {
                    data?.data?.let { uri ->
                        onValueChange(uri)
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    showToast(ImagePicker.getError(data))
                }
                else -> {
                    showToast("Task Cancelled")
                }
            }
        }


    OutlinedTextField(
        modifier = modifier
            .clickable(indication = null, interactionSource = MutableInteractionSource()) {
                ImagePicker
                    .with(this)
                    //.crop()
                    .compress(512)
                    .maxResultSize(
                        720,
                        720
                    )
                    .createIntent { intent ->
                        launcher.launch(intent)
                    }
            },
        enabled = false,
        value = if (value == null) "Select a valid Image" else "Selected",
        onValueChange = {

        },
        singleLine = singleLine,
        placeholder = placeholder,
        trailingIcon = {
            Icon(imageVector = Icons.Default.Image, contentDescription = null)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(disabledTextColor = MaterialTheme.colors.onSurface)
    )

}



