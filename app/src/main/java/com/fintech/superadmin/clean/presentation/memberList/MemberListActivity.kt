package com.fintech.superadmin.clean.presentation.memberList

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import coil.compose.AsyncImage
import com.fintech.superadmin.R
import com.fintech.superadmin.clean.data.remote.dto.addMember.MemberDataDto
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.data.db.AppDatabase
import com.fintech.superadmin.data.db.entities.User
import com.fintech.superadmin.ui.theme.FarLightestGrey
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import com.fintech.superadmin.util.ViewUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MemberListActivity : ComponentActivity() {

    val viewModel by viewModels<MemberListViewModel>()
    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = AppDatabase.getAppDatabase(this@MemberListActivity).userDao.regularUser
        viewModel.user = user
        setContent {
            SuperAdminTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    AddedMemberContent()
                }
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun AddedMemberContent() {
        var selectedMember by remember {
            mutableStateOf<MemberDataDto?>(null)
        }
        LaunchedEffect(viewModel.search, viewModel.selectedUserType) {
            viewModel.getMembersList()
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Member List")
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressedDispatcher.onBackPressed() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                        }
                    }
                )
            }) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(color = FarLightestGrey),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(modifier = Modifier.padding(vertical = 7.dp),
                    value = viewModel.search,
                    onValueChange = { viewModel.search = it })

                /**
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            ViewUtils.onSpinnerViewFinger(
                                "Select Usertype", this@MemberListActivity, viewModel.addableList()
                            ) { index ->
                                viewModel.selectedUserType = viewModel.addableList()[index]
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
                )**/
                BasicScreenState(
                    state = viewModel.state
                ) {
                    viewModel.state.value.receivedResponse?.let {
                        if (it.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(it) {
                                    MembersData(
                                        modifier = Modifier.clickable {
                                            selectedMember = it
                                        }, memberDataDto = it
                                    )
                                }
                            }
                        }
                    }
                }

                if (selectedMember != null) {
                    AlertDialog(
                        modifier = Modifier.padding(horizontal = 10.sdp),
                        onDismissRequest = {
                            selectedMember = null
                        },
                        properties = DialogProperties(
                            dismissOnBackPress = true,
                            dismissOnClickOutside = true,
                            usePlatformDefaultWidth = false,
                            //decorFitsSystemWindows = true,
                            securePolicy = SecureFlagPolicy.SecureOff
                        ),
                        title = null,
                        text = null,
                        buttons = {
                            selectedMember?.let {
                                MemberDialogContent(it)
                            }
                        },
                    )
                }
            }
        }
    }


    @Composable
    private fun MembersData(
        modifier: Modifier = Modifier, memberDataDto: MemberDataDto
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
                            text = "Full Name",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${memberDataDto.fIRSTNAME ?: ""} ${memberDataDto.lASTNAME ?: ""}",
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
                            text = "Member Id",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = memberDataDto.pARTNERID ?: "",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
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
                            text = "Mobile",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = memberDataDto.mOBILE ?: "",
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
                            text = "Type",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = memberDataDto.userTypeName(),
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
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
                            text = "Owner",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = user?.name ?: "",
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
                            text = "Status",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = memberDataDto.uSSTATUS ?: "",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 2.sdp)
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(end = 5.sdp)
//                    ) {
//                        Text(
//                            text = "Monthly Sales",
//                            fontSize = 10.textSdp,
//                            maxLines = 1,
//                            fontWeight = FontWeight.Normal,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                        Text(
//                            text = "${stringResource(id = R.string.rupee_sign)} ${memberDataDto.tHISMONTHAMOUNT ?: ""}",
//                            fontSize = 10.textSdp,
//                            maxLines = 1,
//                            fontWeight = FontWeight.Bold,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    }
//                    Column(
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(start = 5.sdp),
//                        horizontalAlignment = Alignment.End
//                    ) {
//                        Text(
//                            text = "Last Month Sales",
//                            fontSize = 10.textSdp,
//                            maxLines = 1,
//                            fontWeight = FontWeight.Normal,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                        Text(
//                            text = "${stringResource(id = R.string.rupee_sign)} ${memberDataDto.lASTMONTHAMOUNT ?: ""}",
//                            fontSize = 10.textSdp,
//                            maxLines = 1,
//                            fontWeight = FontWeight.Bold,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    }
//                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
//                    Column(
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(end = 5.sdp)
//                    ) {
//                        Text(
//                            text = "Total Sales",
//                            fontSize = 10.textSdp,
//                            maxLines = 1,
//                            fontWeight = FontWeight.Normal,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                        Text(
//                            text = "${stringResource(id = R.string.rupee_sign)} ${memberDataDto.lIFETIMEAMOUNT ?: ""}",
//                            fontSize = 10.textSdp,
//                            maxLines = 1,
//                            fontWeight = FontWeight.Bold,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    }
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:${memberDataDto.mOBILE?.trim() ?: ""}")
                        startActivity(intent)

                    }) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "call",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }

                /**
                Row(
                modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.sdp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
                ) {
                if (memberDataDto.uSERTYPE == "47") {
                OutlinedButton(
                onClick = {
                val intent = Intent(
                this@MemberListActivity,
                MyMemberListActivity::class.java
                )
                intent.putExtra("user", memberDataDto)
                startActivity(intent)
                }
                ) {
                Text(text = "Users")
                }
                }

                OutlinedButton(
                onClick = {
                val intent =
                Intent(this@MemberListActivity, MembersHistoryActivity::class.java)
                intent.putExtra("user", memberDataDto)
                startActivity(intent)
                }
                ) {
                Text(text = "Transactions")
                }

                }
                 **/
            }
        }
    }

    @Composable
    private fun MemberDialogContent(memberDataDto: MemberDataDto) {
        /**
        var statusChange by remember {
        mutableStateOf(0)
        }
        var currentStatus by remember {
        mutableStateOf(memberDataDto.uSSTATUS ?: "deactive")
        }
        LaunchedEffect(key1 = statusChange) {
        if (statusChange != 0) {
        viewModel.changeMemberStatus(
        context = this@MemberListActivity,
        status = currentStatus,
        memberDataDto = memberDataDto
        )
        }
        }
         **/
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.sdp, vertical = 4.sdp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.sdp)
            ) {

                AsyncImage(
                    modifier = Modifier
                        .size(35.sdp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                        .border(.7.dp, color = MaterialTheme.colors.onSurface, shape = CircleShape),
                    contentScale = ContentScale.Fit,
                    model = memberDataDto.pROFILEIMG,
                    placeholder = painterResource(id = R.drawable.cf_card_person),
                    error = painterResource(id = R.drawable.cf_card_person),
                    contentDescription = "image"
                )
                /**
                Row(
                modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.sdp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
                ) {
                RadioButton(
                selected = !currentStatus.contains("deactive", true),
                onClick = {
                currentStatus = "Active"
                statusChange++
                }
                )
                Text(text = "Active", modifier = Modifier.padding(horizontal = 5.sdp))

                RadioButton(
                selected = currentStatus.contains("deactive", true),
                onClick = {
                currentStatus = "Deactive"
                statusChange++
                }
                )
                Text(text = "Deactive", modifier = Modifier.padding(horizontal = 5.sdp))
                }
                 **/
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
                            text = "Full Name",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = memberDataDto.fIRSTNAME ?: "",
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
                            text = "Member Id",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = memberDataDto.pARTNERID ?: "",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
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
                            text = "Mobile",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = memberDataDto.mOBILE ?: "",
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
                            text = "Type",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = memberDataDto.userTypeName(),
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
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
                            text = "Email",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = memberDataDto.eMAIL ?: "",
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
                            text = "Joining Date",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = memberDataDto.dATE ?: "",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
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
                            text = "State",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = memberDataDto.sTATE ?: "",
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
                            text = "Pin Code",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = memberDataDto.pIN ?: "",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
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
                            text = "Owner",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = user?.name ?: "",
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
                            text = "Status",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = memberDataDto.uSSTATUS ?: "",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
//
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 2.sdp)
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(end = 5.sdp)
//                    ) {
//                        Text(
//                            text = "Date of Birth",
//                            fontSize = 10.textSdp,
//                            maxLines = 1,
//                            fontWeight = FontWeight.Normal,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                        Text(
//                            text = memberDataDto.dOB ?: "",
//                            fontSize = 10.textSdp,
//                            maxLines = 1,
//                            fontWeight = FontWeight.Bold,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    }
//                    Column(
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(start = 5.sdp),
//                        horizontalAlignment = Alignment.End
//                    ) {
//                        Text(
//                            text = "Subscription",
//                            fontSize = 10.textSdp,
//                            maxLines = 1,
//                            fontWeight = FontWeight.Normal,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                        Text(
//                            text = memberDataDto.sUBDATE ?: "",
//                            fontSize = 10.textSdp,
//                            maxLines = 1,
//                            fontWeight = FontWeight.Bold,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    }
//                }
            }
        }
    }


    @Composable
    fun SearchBar(
        modifier: Modifier,
        hint: String = "Search Accounts",
        value: String,
        trailingIcon: @Composable (() -> Unit)? = null,
        onValueChange: (String) -> Unit
    ) {
        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            var textState by remember { mutableStateOf("") }
            val maxLength = 110
            val lightBlue = Color(0xffd8e6ff)
            val blue = Color(0xff76a9ff)
            TextField(
                modifier = Modifier.fillMaxWidth(.9f),
                value = value,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = lightBlue,
                    cursorColor = Color.Black,
                    disabledLabelColor = lightBlue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(text = hint, color = Color.Gray)
                },
                onValueChange = {
                    if (it.length <= maxLength) {
                        onValueChange(it)
                    }
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                trailingIcon = trailingIcon,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search, contentDescription = null
                    )
                })
        }
    }
}