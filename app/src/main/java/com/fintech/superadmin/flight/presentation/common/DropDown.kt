package com.fintech.superadmin.flight.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown(
    modifier: Modifier = Modifier,
    titles: List<String>,
    hint: String,
    value: MutableState<String>,
    onChange: (String) -> Unit
) {

    var mExpanded by remember { mutableStateOf(false) }


    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { mExpanded = !mExpanded }
    ) {
        TextField(
            value = value.value,
            onValueChange = { value.value = it },
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = { Text(hint) },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            },
            readOnly = true,
            singleLine = true,
            maxLines = 1,
        )

        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .fillMaxWidth(.7f)
        ) {
            titles.forEach { label ->
                DropdownMenuItem(onClick = {
                    value.value = label
                    mExpanded = false
                    onChange(label)
                }, text = {
                    Text(text = label)
                })
            }
        }
    }
}
