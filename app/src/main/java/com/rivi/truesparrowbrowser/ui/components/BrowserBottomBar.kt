package com.rivi.truesparrowbrowser.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import com.rivi.truesparrowbrowser.ui.theme.TextSecondary

/**
 * A composable that represents the bottom navigation bar of the browser.
 *
 * It provides quick access to core browser actions such as navigating home, opening a new tab,
 * switching between existing tabs, and accessing settings.
 *
 * @param tabCount The number of currently open tabs to display in the tab switcher icon.
 * @param onHomeClick Callback invoked when the home icon is clicked.
 * @param onNewTabClick Callback invoked when the add icon is clicked.
 * @param onSettingsClick Callback invoked when the settings/more icon is clicked.
 * @param onTabsClick Callback invoked when the tab switcher icon is clicked.
 * @param modifier The [Modifier] to be applied to the [BottomAppBar].
 */
@Composable
fun BrowserBottomBar(
    tabCount: Int = 1,
    onHomeClick: () -> Unit,
    onNewTabClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onTabsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(modifier = modifier.fillMaxWidth()) {
        IconButton(onClick = onHomeClick, modifier = Modifier.weight(1f)) {
            Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
        }

        IconButton(onClick = onNewTabClick, modifier = Modifier.weight(1f)) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Open new tab")
        }

        IconButton(onClick = onTabsClick, modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .border(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tabCount.toString(),
                    style = MaterialTheme.typography.labelLarge
                )

            }
        }

        IconButton(onClick = onSettingsClick, modifier = Modifier.weight(1f)) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More Settings")
        }
    }
}

private fun Modifier.border(): Modifier {
    return this.then(
        border(
            width = 1.5.dp,
            color = TextSecondary,
            shape = RoundedCornerShape(6.dp)
        )
    )
}
