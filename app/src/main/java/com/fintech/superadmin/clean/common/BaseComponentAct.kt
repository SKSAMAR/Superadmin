package com.fintech.superadmin.clean.common

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.FabPosition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintech.superadmin.clean.util.ConnectionLiveData
import com.fintech.superadmin.ui.theme.IconColor
import com.fintech.superadmin.util.ThemeColors
import dagger.hilt.android.AndroidEntryPoint
import me.onebone.toolbar.*

@AndroidEntryPoint
open class BaseComponentAct : ComponentActivity() {

    lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeColors(this)
        connectionLiveData = ConnectionLiveData(this@BaseComponentAct)
    }


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    open fun BaseScaffold(
        topBar: @Composable () -> Unit = {},
        bottomBar: @Composable () -> Unit = {},
        content: @Composable BoxScope.() -> Unit
    ) {
        val isNetworkAvailable = connectionLiveData.observeAsState(true)
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            topBar = topBar,
            bottomBar = bottomBar,
            scaffoldState = scaffoldState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                AnimatedVisibility(
                    visible = !isNetworkAvailable.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.05f)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize(),
                        backgroundColor = Color.White,
                        elevation = 3.dp,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "No Internet Available",
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    content = content
                )
            }
        }


    }


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    open fun BaseScaffold(
        title: String = "",
        navigationIcon: Boolean,
        givenNavigation: ImageVector = Icons.Default.ArrowBack,
        color: Color = IconColor,
        floatingActionButton: @Composable () -> Unit = {},
        floatingActionButtonPosition: FabPosition = FabPosition.End,
        isFloatingActionButtonDocked: Boolean = false,
        action: () -> Unit,
        actions: @Composable RowScope.() -> Unit = {},
        content: @Composable BoxScope.() -> Unit
    ) {
        val isNetworkAvailable = connectionLiveData.observeAsState(true)
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = title)
                    },
                    navigationIcon = {
                        if (navigationIcon) {
                            IconButton(onClick = {
                                action()
                            }) {
                                Icon(
                                    imageVector = givenNavigation,
                                    contentDescription = "navigation"
                                )
                            }
                        }
                    },
                    backgroundColor = color,
                    contentColor = Color.White,
                    actions = actions
                )
            },
            scaffoldState = scaffoldState,
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                AnimatedVisibility(
                    visible = !isNetworkAvailable.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.05f)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize(),
                        backgroundColor = Color.White,
                        elevation = 3.dp,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "No Internet Available",
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    content = content
                )
            }
        }


    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    open fun BaseScaffold(
        title: String = "",
        color: Color = IconColor,
        floatingActionButton: @Composable () -> Unit = {},
        floatingActionButtonPosition: FabPosition = FabPosition.End,
        isFloatingActionButtonDocked: Boolean = false,
        action: () -> Unit,
        noTopBar: @Composable () -> Unit = {},
        topBar: @Composable () -> Unit = {
            TopAppBar(
                title = {
                    Text(text = title)
                },
                backgroundColor = color,
                contentColor = Color.White,
                actions = actions
            )
        },
        actions: @Composable RowScope.() -> Unit = {},
        content: @Composable BoxScope.() -> Unit
    ) {
        val isNetworkAvailable = connectionLiveData.observeAsState(true)
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            topBar = if (title.isNotEmpty()) topBar else noTopBar,
            scaffoldState = scaffoldState,
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                AnimatedVisibility(
                    visible = !isNetworkAvailable.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.05f)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize(),
                        backgroundColor = Color.White,
                        elevation = 3.dp,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "No Internet Available",
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    content = content
                )
            }
        }


    }


    @Composable
    fun BaseCollapsingToolbarScaffold(
        toolbar: @Composable CollapsingToolbarScope.() -> Unit,
        content: @Composable BoxScope.() -> Unit,
    ) {
        val isNetworkAvailable = connectionLiveData.observeAsState(true)
        val toolbarState = rememberCollapsingToolbarScaffoldState()
        CollapsingToolbarScaffold(
            modifier = Modifier,
            state = toolbarState,
            scrollStrategy = ScrollStrategy.EnterAlways,
            toolbar = toolbar) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                AnimatedVisibility(
                    visible = !isNetworkAvailable.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.05f)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize(),
                        backgroundColor = Color.White,
                        elevation = 3.dp,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "No Internet Available",
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    content = content
                )
            }

        }

    }


}