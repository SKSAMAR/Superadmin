package com.fintech.superadmin.flight.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fintech.superadmin.flight.util.common.sdp

@Composable
fun <T> DropDownSystem(
    expanded: Boolean = false, list: List<T>, onSelect: (T?) -> Unit
) {

    Box(modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 0.dp, max = 200.sdp)) {
        DropdownMenu(expanded = expanded, onDismissRequest = {
            onSelect(null)
        }) {
            list.forEach {
                DropdownMenuItem(onClick = {
                    onSelect(it)
                }) {
                    Text(text = it.toString(), maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}