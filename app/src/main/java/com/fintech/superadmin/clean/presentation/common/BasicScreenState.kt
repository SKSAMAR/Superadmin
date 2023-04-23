package com.fintech.superadmin.clean.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.fintech.superadmin.clean.domain.model.AnimationModel
import com.fintech.superadmin.clean.domain.model.ScreenState

@Composable
fun<T> BasicScreenState(
    modifier: Modifier = Modifier,
    state: State<ScreenState<T>>,
    animationModel:MutableState<AnimationModel?>? = null,
    content: @Composable ColumnScope.() -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if(state.value.isLoading){
            ScreenAnimation(
                resources = com.fintech.superadmin.R.raw.loading
            )
        }
        else if(state.value.error.trim().isNotEmpty()){

           Column(
               modifier = Modifier.fillMaxSize(),
               verticalArrangement = Arrangement.Top,
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               ScreenAnimation(
                   modifier = Modifier.fillMaxSize(.3f)
                       .clip(RectangleShape),
                   resources = com.fintech.superadmin.R.raw.uh_oh
               )
               Text(
                   modifier = Modifier.fillMaxWidth(),
                   text = state.value.error,
                   color = Color.Red,
                   fontSize = 18.sp,
                   style = TextStyle(textAlign = TextAlign.Center),
                   maxLines = 2,
                   overflow = TextOverflow.Ellipsis
               )
           }
        }
        else{
            Column(
                modifier = modifier,
                content = content
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            animationModel?.value?.animation?.let { anim ->
                ScreenAnimation(
                    modifier = Modifier.fillMaxSize(.3f)
                        .clip(RectangleShape),
                    resources = anim
                )
            }
            animationModel?.value?.message?.let { error ->
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = error,
                    color = Color.Red,
                    fontSize = 14.sp,
                    style = TextStyle(textAlign = TextAlign.Center),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (state.value.baseDialogVisible.value) {
            Column(content = state.value.content)
        }

    }
}