package com.fintech.superadmin.flight.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomSearchViewBasic(
    query: MutableState<String>,
){

    CustomTextField(
        text = query,
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                null,
                tint = LocalContentColor.current.copy(alpha = 0.4f),
                modifier = Modifier.padding(end = 20.dp)
            )
        },
        trailingIcon = null,
        modifier = Modifier
            .background(
                MaterialTheme.colors.surface,
                RoundedCornerShape(percent = 0)
            )
            .padding(vertical = 4.dp, horizontal = 18.dp)
            .height(40.dp),
        fontSize = 14.sp,
        placeholderText = "Search"
    )
}