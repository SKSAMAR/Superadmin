package com.fintech.superadmin.clean.presentation.itrfilingsalraied

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
import com.fintech.superadmin.clean.presentation.gstregistration.OutlinedTextFieldState
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ITRFillingSalariedActivity : BaseComponentAct() {

    val viewModel by viewModels<ITRFillingSalariedViewModel>()

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
        BaseScaffold(topBar = {
            TopAppBar(title = {
                Text("ITR Filing Salaried")
            }, navigationIcon = {
                IconButton(onClick = {
                    onBackPressedDispatcher.onBackPressed()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = ""
                    )
                }
            },

                actions = {
                IconButton(
                    onClick = {
                        val intent =
                            Intent(this@ITRFillingSalariedActivity, LegalsDetailsReceiptActivity::class.java)
                        intent.putExtra("info", "itr_filling")
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
            BottomAppBar(modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.validateTheForm(this@ITRFillingSalariedActivity) }) {
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
                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = c_name,
                            onValueChange = { c_name = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Customer Name")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = c_num,
                            onValueChange = { c_num = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Customer Number")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = cmnpy_name,
                            onValueChange = { cmnpy_name = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Company Name")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = cus_email,
                            onValueChange = { cus_email = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Customer Email")
                            })

                        OutlinedTextFieldState(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = x_state,
                            onValueChange = { x_state = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "State")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = cus_address,
                            onValueChange = { cus_address = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Customer Address")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = applicant_name,
                            onValueChange = { applicant_name = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Applicant Name")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = father_name,
                            onValueChange = { father_name = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Father's Name")
                            })

                        OutlinedTextFieldDOB(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = dob,
                            onValueChange = { dob = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Date of Birth")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = pan_no,
                            onValueChange = { pan_no = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "PAN Number")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = aadhar_no,
                            onValueChange = { aadhar_no = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Aadhar Number")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = applicant_address,
                            onValueChange = { applicant_address = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Applicant Address")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = pin_code,
                            onValueChange = { pin_code = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Pin Code")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = applicant_email,
                            onValueChange = { applicant_email = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Applicant Email")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = applicant_mobile_no,
                            onValueChange = { applicant_mobile_no = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Applicant Mobile Number")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = finacial_year,
                            onValueChange = { finacial_year = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Financial Year")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = acc_no,
                            onValueChange = { acc_no = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Account Number")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = ifsc_code,
                            onValueChange = { ifsc_code = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "IFSC Code")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = gross_salary,
                            onValueChange = { gross_salary = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Gross Salary")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = decduction,
                            onValueChange = { decduction = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Deduction")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = net_salary,
                            onValueChange = { net_salary = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Net Salary")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = turn_over,
                            onValueChange = { turn_over = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Turn Over")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = income,
                            onValueChange = { income = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Income")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = house_prop,
                            onValueChange = { house_prop = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "House Property")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = bank_account,
                            onValueChange = { bank_account = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Bank Account")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = tution,
                            onValueChange = { tution = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Tuition")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = health_insurance,
                            onValueChange = { health_insurance = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Health Insurance")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = other_deduction,
                            onValueChange = { other_deduction = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Other Deduction")
                            })

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = deduction_amount,
                            onValueChange = { deduction_amount = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Deduction Amount")
                            })
                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                            value = deduction_amount,
                            onValueChange = { deduction_amount = it },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Deduction Amount")
                            })

                        OutlinedTextFieldImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.sdp),
                            value = viewModel.image, // Replace "image" with the appropriate variable
                            onValueChange = { it, uri ->
                                viewModel.image = it
                                viewModel.imageUri = uri
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Select Image")
                            }
                        )
                    }

                }
            }
        }
    }


}