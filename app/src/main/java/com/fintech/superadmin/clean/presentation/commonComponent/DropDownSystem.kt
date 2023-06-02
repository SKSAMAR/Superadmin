package com.fintech.superadmin.clean.presentation.commonComponent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun <T> DropDownSystem(
    expanded: Boolean = false, list: List<T>, onSelect: (T?) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
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