package com.rivi.truesparrowbrowser.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rivi.truesparrowbrowser.R
import androidx.compose.material3.MaterialTheme
import com.rivi.truesparrowbrowser.ui.theme.CardBackground
import com.rivi.truesparrowbrowser.ui.theme.IconDisabled
import com.rivi.truesparrowbrowser.ui.theme.SearchBarBackground
import com.rivi.truesparrowbrowser.ui.theme.TextPrimary
import com.rivi.truesparrowbrowser.ui.theme.TextSecondary

/**
 * A custom search bar component that provides browser navigation controls and a text input field.
 *
 * This component includes buttons for navigating backward and forward in history, a text field
 * for entering search queries or URLs, and a dynamic button that switches between reload and
 * stop actions based on the loading state.
 *
 * @param searchValue The current [TextFieldValue] to be displayed in the search input.
 * @param onSearchValueChange Callback triggered when the input text or selection changes.
 * @param canGoBack Boolean flag indicating if the back navigation button should be enabled.
 * @param canGoForward Boolean flag indicating if the forward navigation button should be enabled.
 * @param hasUrl Boolean flag indicating if the current state allows for reload or stop actions.
 * @param isLoading Boolean flag indicating if a page is currently loading, toggling between the stop and reload icons.
 */
@Composable
fun SearchBarCard(
    searchValue: TextFieldValue,
    onSearchValueChange: (TextFieldValue) -> Unit,
    canGoBack: Boolean,
    canGoForward: Boolean,
    hasUrl: Boolean,
    isLoading: Boolean,
    moveBack: () -> Unit,
    moveForward: () -> Unit,
    onSearch: (String) -> Unit,
    onReload: () -> Unit,
    onStop: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = SearchBarBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { moveBack() },
                enabled = canGoBack,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    tint = if (canGoBack) TextPrimary else IconDisabled,
                    contentDescription = "Move back"
                )
            }

            IconButton(
                onClick = { moveForward() },
                enabled = canGoForward,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_next),
                    tint = if (canGoForward) TextPrimary else IconDisabled,
                    contentDescription = "Move forward"
                )
            }


            BasicTextField(
                value = searchValue,
                onValueChange = onSearchValueChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = TextPrimary),
                cursorBrush = SolidColor(TextPrimary),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(
                    onGo = {
                        onSearch(searchValue.text)
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(CardBackground)
                    .padding(horizontal = 14.dp)
                    .onFocusChanged {
                        if (it.isFocused) {
                            onSearchValueChange(searchValue.copy(selection = TextRange(searchValue.text.length)))
                        }
                    },
                decorationBox = { inner ->
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (searchValue.text.isBlank() && isFocused.not()) {
                            Text("Search or type URL", color = TextSecondary, style = MaterialTheme.typography.bodyLarge)
                        }
                        inner()
                    }
                }
            )


            IconButton(
                onClick = {
                    if (isLoading) {
                        onStop()
                    } else {
                        onReload()
                    }
                },
                enabled = hasUrl
            ) {
                Icon(
                    painter = painterResource(
                        if (isLoading) R.drawable.ic_stop else R.drawable.ic_reload
                    ),
                    tint = if (hasUrl) TextPrimary else IconDisabled,
                    contentDescription = if (isLoading) "Stop" else "Reload"
                )
            }
        }
    }
}