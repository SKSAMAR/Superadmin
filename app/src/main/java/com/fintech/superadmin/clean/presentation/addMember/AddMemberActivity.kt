package com.fintech.superadmin.clean.presentation.addMember

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.core.text.isDigitsOnly
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.presentation.gstregistration.OutlinedTextFieldDOB
import com.fintech.superadmin.clean.presentation.gstregistration.OutlinedTextFieldImage
import com.fintech.superadmin.clean.presentation.gstregistration.OutlinedTextFieldList
import com.fintech.superadmin.clean.presentation.gstregistration.OutlinedTextFieldState
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.data.db.AppDatabase
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import com.fintech.superadmin.util.ViewUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMemberActivity : BaseComponentAct() {

    private val viewModel by viewModels<AddMemberViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.user = AppDatabase.getAppDatabase(applicationContext).userDao.regularUser
        setContent {
            SuperAdminTheme {
                Surface(
                    color = MaterialTheme.colors.surface
                ) {
                    AddMemeberScreen()
                }
            }
        }
    }


    @Composable
    private fun AddMemeberScreen() {
        var hasAddition by remember { mutableStateOf(false) }
        LaunchedEffect(viewModel.selectedUserType) {
            hasAddition = viewModel.additionalPerson()
        }

        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Add Member")
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
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.validateTheForm(this@AddMemberActivity)
                        }
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "ADD",
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

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp)
                                .clickable {
                                    ViewUtils.onSpinnerViewFinger(
                                        "Select Usertype",
                                        this@AddMemberActivity,
                                        viewModel.addableList()
                                    ) { index ->
                                        viewModel.selectedUserType = viewModel.addableList()[index]
                                        //viewModel.selectedMember = null
                                    }
                                },
                            value = viewModel.selectedUserType?.toString() ?: "Select Usertype",
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                disabledTextColor = MaterialTheme.colors.onSurface
                            ),
                            onValueChange = {

                            },
                            placeholder = {
                                Text(text = "Select Usertype")
                            },
                            maxLines = 1,
                            enabled = false,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "arrow"
                                )
                            }
                        )


                        createOutlinedTextField("First Name", fname) { newValue ->
                            fname = newValue
                        }

                        createOutlinedTextField("Last Name", lname) { newValue ->
                            lname = newValue
                        }


                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = mobile,
                            onValueChange = {
                                if (it.length <= 10 && it.isDigitsOnly()) {
                                    mobile = it
                                }
                            },
                            placeholder = {
                                Text(text = "Mobile Number")
                            },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )

                        createOutlinedTextField("Email", email) { newValue ->
                            email = newValue
                        }

                        createOutlinedTextField("Address", address) { newValue ->
                            address = newValue
                        }

                        createOutlinedTextField("City", city) { newValue ->
                            city = newValue
                        }

                        createOutlinedTextFieldState("State", xstate) { newValue ->
                            xstate = newValue
                        }

                        createOutlinedTextField("Pincode", pincode) { newValue ->
                            pincode = newValue
                        }

                        createOutlinedTextField("Password", password) { newValue ->
                            password = newValue
                        }

                        createOutlinedTextField("Firm Name", firm_name) { newValue ->
                            firm_name = newValue
                        }

                        createOutlinedTextFieldDob("Date of Birth", dob) { newValue ->
                            dob = newValue
                        }

                        createOutlinedTextFieldGender("Gender", gender) { newValue ->
                            gender = newValue
                        }

                        createOutlinedTextField("Landmark", Landmark) { newValue ->
                            Landmark = newValue
                        }

                        createOutlinedTextField("Aadhaar Number", aadhar_no) { newValue ->
                            aadhar_no = newValue
                        }

                        createOutlinedTextField("PAN Number", pan_no) { newValue ->
                            pan_no = newValue
                        }

                        createOutlinedTextField("GST Number", gst_no) { newValue ->
                            gst_no = newValue
                        }

                        createOutlinedTextField(
                            "DEBIT WALLET (LOCK AMOUNT)",
                            debit_wallet
                        ) { newValue ->
                            if (newValue.isDigitsOnly()) {
                                debit_wallet = newValue
                            }
                        }

                        createOutlinedTextField(
                            "CREDIT WALLET (LOCK AMOUNT)",
                            credit_wallet
                        ) { newValue ->
                            if (newValue.isDigitsOnly()) {
                                credit_wallet = newValue
                            }
                        }

                        createOutlinedTextFieldList(
                            "Status",
                            status,
                            list = listOf("Active", "Deactive")
                        ) { newValue ->
                            status = newValue
                        }

                        createOutlinedTextField("Password", password) { newValue ->
                            password = newValue
                        }


                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.aadhar_front, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.aadhar_front = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Aadhaar Card Front Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.aadhar_back, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.aadhar_back = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Aadhaar Card Back Photo")
                            }
                        )

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.pancard, // Replace "image" with the appropriate variable
                            onValueChange = { uri ->
                                viewModel.pancard = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Pan Card Photo")
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