package com.fintech.superadmin.clean.presentation.referEarn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import coil.compose.AsyncImage
import com.fintech.superadmin.R
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.domain.model.sharable.listOfLogos
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.presentation.commonComponent.BasicOutlinedContainerView
import com.fintech.superadmin.clean.presentation.contacts.ContactsViewModel
import com.fintech.superadmin.clean.presentation.referEarn.component.ContactRow
import com.fintech.superadmin.clean.presentation.rewards.RewardActivity
import com.fintech.superadmin.clean.util.ViewUtils.copyReferralLink
import com.fintech.superadmin.clean.util.ViewUtils.shareWithAll
import com.fintech.superadmin.clean.util.ViewUtils.shareWithPackage
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.ui.theme.BackHorizontalGradient
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReferEarnActivity : BaseComponentAct() {

    val viewModel by viewModels<ReferEarnViewModel>()
    private val contactsViewModel by viewModels<ContactsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    ReferEarnContent()
                }
            }
        }
    }


    @Composable
    private fun ReferEarnContent() {

        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Refer and earn")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back",
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.HelpOutline,
                                contentDescription = "help",
                            )
                        }
                    }
                )
            }
        ) {

            BasicScreenState(state = viewModel.state) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.sdp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(18.sdp))
                    }

                    viewModel.state.value.receivedResponse?.let { dto ->
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(145.sdp)
                                    .background(
                                        BackHorizontalGradient,
                                        shape = RoundedCornerShape(6.sdp)
                                    )
                                    .clip(shape = RoundedCornerShape(6.sdp))
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(horizontal = 24.sdp, vertical = 14.sdp)
                                        .align(
                                            Alignment.TopStart
                                        ),
                                    text = "Total Rewards",
                                    color = Color.White,
                                    fontSize = 10.textSdp
                                )


                                Text(
                                    modifier = Modifier.align(
                                        Alignment.Center
                                    ),
                                    text = stringResource(id = R.string.rupee_sign) + dto.tOTALEARNED,
                                    color = Color.White,
                                    fontSize = 28.textSdp,
                                )

                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 18.sdp, vertical = 8.sdp)
                                        .align(
                                            Alignment.BottomEnd
                                        )
                                        .clickable {
                                            startActivity(
                                                Intent(
                                                    this@ReferEarnActivity,
                                                    RewardActivity::class.java
                                                )
                                            )
                                        }
                                ) {
                                    Text(
                                        text = "Uncollected Rewards",
                                        color = Color.White,
                                        fontSize = 8.textSdp
                                    )
                                    Icon(
                                        modifier = Modifier.height(height = 12.sdp),
                                        imageVector = Icons.Default.ArrowRightAlt,
                                        contentDescription = "arrow_back",
                                        tint = Color.White,
                                    )

                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(10.sdp))
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .height(height = 120.sdp)
                                        .weight(1f),
                                    //modifier = Modifier.size(height = 120.sdp, width = 132.sdp),
                                    model = R.drawable.refer_espum,
                                    contentDescription = "refer",
                                    contentScale = ContentScale.Fit
                                )
                                Column(
                                    modifier = Modifier
                                        .height(height = 120.sdp)
                                        .weight(1f)
                                        .padding(end = 2.sdp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(55.sdp)
                                            .border(
                                                width = 1.sdp,
                                                color = MaterialTheme.colors.onSurface,
                                                shape = RoundedCornerShape(6.sdp)
                                            )
                                            .clip(RoundedCornerShape(6.sdp))
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 10.sdp, horizontal = 8.sdp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            AsyncImage(
                                                model = R.drawable.currency_bag,
                                                contentDescription = "currency_bag",
                                                modifier = Modifier.size(
                                                    width = 15.sdp,
                                                    height = 28.sdp
                                                )
                                            )
                                            Spacer(modifier = Modifier.width(9.sdp))

                                            Text(
                                                text = dto.rEFERTEXT ?: "",
                                                fontSize = 7.textSdp,
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(55.sdp)
                                            .border(
                                                width = 1.sdp,
                                                color = MaterialTheme.colors.onSurface,
                                                shape = RoundedCornerShape(6.sdp)
                                            )
                                            .clip(RoundedCornerShape(6.sdp))
                                    ) {

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 10.sdp, horizontal = 8.sdp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            AsyncImage(
                                                model = R.drawable.dollar_coin,
                                                contentDescription = "currency_bag",
                                                modifier = Modifier.size(22.sdp)
                                            )
                                            Spacer(modifier = Modifier.width(9.sdp))

                                            Text(
                                                text = dto.cOMMISSIONTEXT ?: "",
                                                fontSize = 7.textSdp,
                                            )
                                        }

                                    }

                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(15.sdp))
                            BasicOutlinedContainerView(
                                value = dto.rEFERCODE ?: "",
                                onValueChange = { },
                                maxLength = 100,
                                isEditable = false,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Go,
                                    keyboardType = KeyboardType.Text,
                                    capitalization = KeyboardCapitalization.None
                                ),
                                trailingIcon = {
                                    AsyncImage(
                                        modifier = Modifier
                                            .size(14.sdp)
                                            .clickable {
                                                copyReferralLink(viewModel.link)
                                            },
                                        model = R.drawable.copyable,
                                        contentDescription = "copyable"
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(15.sdp))
                        }

                        item {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                items(listOfLogos) { item ->
                                    Image(painter = painterResource(id = item.logo),
                                        contentDescription = item.path,
                                        modifier = Modifier
                                            .size(28.sdp)
                                            .clickable(
                                                indication = null,
                                                interactionSource = MutableInteractionSource()
                                            ) {
                                                when (item.logo) {
                                                    R.drawable.options_circle -> {
                                                        shareWithAll(viewModel.link)
                                                    }

                                                    else -> {
                                                        shareWithPackage(viewModel.link, item.path)
                                                    }
                                                }
                                            })
                                }
                            }
                            Spacer(modifier = Modifier.height(15.sdp))
                        }
                        contactsViewModel.state.value.receivedResponse?.let { list->
                            items(list){contact->
                                ContactRow(
                                    invitationMessage = viewModel.link,
                                    contact = contact,
                                    location = if (contact == list.first()) 1 else if (contact == list.last()) 2 else 0
                                )
                            }
                        }

                        item {
                            BasicScreenState(
                                state = contactsViewModel.state
                            ) {}
                        }

                        item {
                            Spacer(modifier = Modifier.height(90.sdp))
                        }

                    }

                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.earnedAndData(initialFetch = false)
        contactsViewModel.fetchContacts(this@ReferEarnActivity)
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.earnedAndData(initialFetch = false)
    }

}