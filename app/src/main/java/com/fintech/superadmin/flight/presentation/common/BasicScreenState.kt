package com.fintech.superadmin.flight.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import com.fintech.superadmin.R
import com.fintech.superadmin.flight.domain.ScreenState
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp

@Composable
fun <T> BasicScreenState(
    modifier: Modifier = Modifier,
    outerModifier: Modifier = Modifier,
    state: State<ScreenState<T>>,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = outerModifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        if (state.value.isLoading) {
            ScreenAnimation(
                modifier = Modifier.fillMaxSize(),
                resources = R.raw.loading
            )
        } else if (state.value.error.trim().isNotEmpty()) {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.sdp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.padding(5.sdp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        ScreenAnimation(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RectangleShape),
                            resources = R.raw.error,
                        )
                        androidx.compose.material.Text(
                            text = state.value.error ?: "",
                            fontSize = 18.textSdp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            Column(
                modifier = modifier,
                content = content
            )
        }

        if (state.value.baseDialogVisible.value) {
            Column(content = state.value.content)
        }

    }
}