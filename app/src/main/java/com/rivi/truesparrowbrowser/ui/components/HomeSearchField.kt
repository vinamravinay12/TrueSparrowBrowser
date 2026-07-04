package com.rivi.truesparrowbrowser.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.rivi.truesparrowbrowser.ui.theme.TextPrimary


/**
 * A search input field designed for the home screen that allows users to enter search queries or URLs.
 *
 * This component features a rounded design, a search icon, and manages its own internal text state.
 * It automatically handles keyboard visibility and focus clearing when a search is triggered.
 *
 * @param onSearch Callback invoked when the user triggers the search action (e.g., by pressing the
 * Search button on the software keyboard), providing the current text in the field.
 */
@Composable
fun HomeSearchField(onSearch: (String) -> Unit) {
    var value by remember { mutableStateOf(TextFieldValue("")) }
    var isFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        onValueChange = { value = it },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focus ->
                isFocused = focus.isFocused
                if (focus.isFocused) {
                    value = value.copy(selection = TextRange(value.text.length))
                }
            },
        placeholder = {
            if (!isFocused) Text(
                "Search the web or enter an address",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        textStyle = MaterialTheme.typography.titleMedium,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        shape = RoundedCornerShape(24.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            cursorColor = TextPrimary
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(value.text)
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        )
    )
}