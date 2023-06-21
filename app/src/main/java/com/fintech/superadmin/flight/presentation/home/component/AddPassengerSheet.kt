package com.fintech.superadmin.flight.presentation.home.component


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.flowlayout.FlowRow
import com.fintech.superadmin.R
import com.fintech.superadmin.flight.domain.state.SharedState.adult
import com.fintech.superadmin.flight.domain.state.SharedState.child
import com.fintech.superadmin.flight.domain.state.SharedState.infant
import com.fintech.superadmin.flight.domain.state.SharedState.selectedSeatClass
import com.fintech.superadmin.flight.domain.state.seatClassesList
import com.fintech.superadmin.flight.presentation.common.ChipCard
import com.fintech.superadmin.flight.presentation.home.HomeViewModel
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddPassengerSheet(
    viewModel: HomeViewModel = viewModel(),
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.sdp)
    ) {
        Divider()
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.sdp, horizontal = 15.sdp)){
            item {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Delete")
                }
                Spacer(modifier = Modifier.padding(vertical = 2.sdp))
            }
            item {
                Text(
                    modifier = Modifier.padding(vertical = 5.sdp),
                    text = "Select Passengers",
                    style = TextStyle(fontSize = 18.textSdp, fontWeight = FontWeight.Bold)
                )

                //ADULT
                Column(
                    modifier = Modifier.padding(vertical = 2.sdp),
                ) {
                    Row(
                       modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(text = "Adult", fontSize = 10.textSdp)
                            Text(text = "(12+ Yrs)", fontSize = 8.textSdp)
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                                Box(
                                    modifier = Modifier
                                ) {
                                    Row(
                                        modifier = Modifier.padding(5.sdp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
                                        ) {
                                            androidx.compose.material.Icon(
                                                modifier = Modifier
                                                    .size(16.sdp)
                                                    .padding(horizontal = 2.sdp)
                                                    .clickable(
                                                        interactionSource = MutableInteractionSource(),
                                                        indication = null
                                                    ) {
                                                        viewModel.deleteAdult()
                                                    },
                                                painter = painterResource(id = R.drawable.minus),
                                                contentDescription = "minus",
                                                tint = MaterialTheme.colorScheme.surface
                                            )
                                        }

                                        Text(
                                            modifier = Modifier
                                                .width(30.sdp)
                                                .padding(horizontal = 5.sdp),
                                            text = adult.toString(),
                                            fontSize = 10.textSdp,
                                            maxLines = 1,
                                            textAlign = TextAlign.Center,
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.SemiBold
                                        )

                                        Box(
                                            modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
                                        ) {
                                            androidx.compose.material.Icon(
                                                modifier = Modifier
                                                    .size(16.sdp)
                                                    .padding(horizontal = 2.sdp)
                                                    .clickable(
                                                        interactionSource = MutableInteractionSource(),
                                                        indication = null
                                                    ) {
                                                        viewModel.addAdult()
                                                    },
                                                imageVector = Icons.Default.Add,
                                                contentDescription = "add",
                                                tint = MaterialTheme.colorScheme.surface
                                            )
                                        }


                                    }
                                }

                        }

                    }
                }

                //CHILD
                Column(
                    modifier = Modifier.padding(vertical = 2.sdp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(text = "Child", fontSize = 10.textSdp)
                            Text(text = "(2-12 Yrs)", fontSize = 8.textSdp)
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                            ) {
                                Row(
                                    modifier = Modifier.padding(5.sdp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
                                    ) {
                                        androidx.compose.material.Icon(
                                            modifier = Modifier
                                                .size(16.sdp)
                                                .padding(horizontal = 2.sdp)
                                                .clickable(
                                                    interactionSource = MutableInteractionSource(),
                                                    indication = null
                                                ) {
                                                    viewModel.deleteChild()
                                                },
                                            painter = painterResource(id = R.drawable.minus),
                                            contentDescription = "minus",
                                            tint = MaterialTheme.colorScheme.surface
                                        )
                                    }

                                    Text(
                                        modifier = Modifier
                                            .width(30.sdp)
                                            .padding(horizontal = 5.sdp),
                                        text = child.toString(),
                                        fontSize = 10.textSdp,
                                        maxLines = 1,
                                        textAlign = TextAlign.Center,
                                        overflow = TextOverflow.Ellipsis,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Box(
                                        modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
                                    ) {
                                        androidx.compose.material.Icon(
                                            modifier = Modifier
                                                .size(16.sdp)
                                                .padding(horizontal = 2.sdp)
                                                .clickable(
                                                    interactionSource = MutableInteractionSource(),
                                                    indication = null
                                                ) {
                                                    viewModel.addChild()
                                                },
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "add",
                                            tint = MaterialTheme.colorScheme.surface
                                        )
                                    }


                                }
                            }

                        }

                    }
                }

                //INFANT
                Column(
                    modifier = Modifier.padding(vertical = 2.sdp),
                )  {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(text = "Infant", fontSize = 10.textSdp)
                            Text(text = "(<2 yrs)", fontSize = 8.textSdp)
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                            ) {
                                Row(
                                    modifier = Modifier.padding(5.sdp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
                                    ) {
                                        androidx.compose.material.Icon(
                                            modifier = Modifier
                                                .size(16.sdp)
                                                .padding(horizontal = 2.sdp)
                                                .clickable(
                                                    interactionSource = MutableInteractionSource(),
                                                    indication = null
                                                ) {
                                                    viewModel.deleteInfant()
                                                },
                                            painter = painterResource(id = R.drawable.minus),
                                            contentDescription = "minus",
                                            tint = MaterialTheme.colorScheme.surface
                                        )
                                    }

                                    Text(
                                        modifier = Modifier
                                            .width(30.sdp)
                                            .padding(horizontal = 5.sdp),
                                        text = infant.toString(),
                                        fontSize = 10.textSdp,
                                        maxLines = 1,
                                        textAlign = TextAlign.Center,
                                        overflow = TextOverflow.Ellipsis,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Box(
                                        modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
                                    ) {
                                        androidx.compose.material.Icon(
                                            modifier = Modifier
                                                .size(16.sdp)
                                                .padding(horizontal = 2.sdp)
                                                .clickable(
                                                    interactionSource = MutableInteractionSource(),
                                                    indication = null
                                                ) {
                                                    viewModel.addInfant()
                                                },
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "add",
                                            tint = MaterialTheme.colorScheme.surface
                                        )
                                    }


                                }
                            }

                        }

                    }
                }

                FlowRow(
                    modifier = Modifier.padding(vertical = 2.sdp),
                    mainAxisSpacing = 5.sdp,
                    crossAxisSpacing = 5.sdp
                ) {
                    seatClassesList.forEach {seat->
                        ChipCard(
                            text = seat.title,
                            onChangeState = {
                                selectedSeatClass = seat
                            },
                            isSelectedItem = {selectedSeatClass == seat}
                        )
                    }
                }
            }
        }
    }
}