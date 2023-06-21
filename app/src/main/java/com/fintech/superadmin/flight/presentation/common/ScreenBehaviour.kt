package com.fintech.superadmin.flight.presentation.common

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fintech.superadmin.flight.domain.AnimationModel
import com.fintech.superadmin.flight.domain.ScreenState
import com.fintech.superadmin.flight.util.common.ConnectionLiveData
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun<T> BaseScaffold(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable ()->Unit = {},
    parentPadding: Dp = 0.dp,
    connectionLiveData: ConnectionLiveData = ConnectionLiveData(context),
    state: State<ScreenState<T>>,
    content: @Composable ColumnScope.() -> Unit,
){
    val isNetworkAvailable = connectionLiveData.observeAsState( false)

    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(parentPadding)
                    .weight(1f)
            ) {
                Column(
                    content = {
                        BasicScreenState(state = state, modifier = modifier,  content = content)
                    }
                )
            }
            AnimatedVisibility(
                visible = !isNetworkAvailable.value,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.sdp),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.sdp),
                        text = "No Internet Available",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.textSdp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}


@Composable
fun<T> BaseScaffold(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable ()->Unit = {},
    drawerContent: @Composable ColumnScope.()->Unit = {},
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    parentPadding: Dp = 0.dp,
    drawerGesturesEnabled: Boolean = true,
    connectionLiveData: ConnectionLiveData = ConnectionLiveData(context),
    state: State<ScreenState<T>>,
    animationModel: MutableState<AnimationModel?>? = null,
    content: @Composable ColumnScope.() -> Unit,
){
    val isNetworkAvailable = connectionLiveData.observeAsState( false)

    androidx.compose.material.Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        drawerContent = drawerContent,
        scaffoldState = scaffoldState,
        drawerGesturesEnabled  = drawerGesturesEnabled,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(parentPadding)
                    .weight(1f)
            ) {
                Column(
                    content = {
                        BasicScreenState(state = state, modifier = modifier,  content = content)
                    }
                )
            }
            AnimatedVisibility(
                visible = !isNetworkAvailable.value,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.sdp),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.sdp),
                        text = "No Internet Available",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.textSdp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun<T> BaseScaffoldWithBottomSheet(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    topBar: @Composable () -> Unit = {},
    drawerContent: @Composable ColumnScope.()->Unit = {},
    bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    parentPadding: Dp = 0.dp,
    drawerGesturesEnabled: Boolean = true,
    connectionLiveData: ConnectionLiveData = ConnectionLiveData(context),
    state: State<ScreenState<T>>,
    sheetContent: @Composable ColumnScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit,
){
    val isNetworkAvailable = connectionLiveData.observeAsState( false)

    BottomSheetScaffold(
        topBar = topBar,
        drawerContent = drawerContent,
        scaffoldState = bottomSheetScaffoldState,
        drawerGesturesEnabled  = drawerGesturesEnabled,
        sheetPeekHeight = 0.dp,
        sheetContent = sheetContent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(parentPadding)
                    .weight(1f)
            ) {
                Column(
                    content = {
                        BasicScreenState(state = state, modifier = modifier,  content = content)
                    }
                )
            }
            AnimatedVisibility(
                visible = !isNetworkAvailable.value,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.sdp),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.sdp),
                        text = "No Internet Available",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.textSdp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}