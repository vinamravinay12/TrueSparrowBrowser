package com.rivi.truesparrowbrowser.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rivi.truesparrowbrowser.data.models.BrowserTab
import com.rivi.truesparrowbrowser.ui.components.NewTabCard
import com.rivi.truesparrowbrowser.ui.components.TabCard

/**
 * A screen that displays a grid overview of all open browser tabs.
 *
 * This component allows users to switch between tabs, close specific tabs,
 * clear all tabs, or open a new tab. It displays each tab as a card with
 * an optional thumbnail preview.
 *
 * @param tabs The list of [BrowserTab] objects currently open.
 * @param activeTabId The unique identifier of the currently active tab.
 * @param thumbnails A map containing [Bitmap] previews for each tab, keyed by tab ID.
 * @param onSelectTab Callback invoked when a tab is selected by the user.
 * @param onNewTab Callback invoked when the user requests to open a new tab.
 * @param onCloseTab Callback invoked when the user closes a specific tab.
 * @param onCloseAllTabs Callback invoked when the user selects the option to close all tabs.
 * @param modifier The [Modifier] to be applied to the screen layout.
 */
@Composable
fun TabsScreen(
    tabs: List<BrowserTab>,
    activeTabId: String,
    thumbnails: Map<String, Bitmap>,
    onSelectTab: (String) -> Unit,
    onNewTab: () -> Unit,
    onCloseTab: (String) -> Unit,
    onCloseAllTabs: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (tabs.size == 1) "1 tab" else "${tabs.size} tabs",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onNewTab) {
                Icon(Icons.Default.Add, contentDescription = "New tab")
            }
            Box {
                var menuExpanded by remember { mutableStateOf(false) }

                IconButton(onClick = { menuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More options")
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Close all tabs") },
                        onClick = {
                            menuExpanded = false
                            onCloseAllTabs()
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(tabs, key = { it.id }) { tab ->
                TabCard(
                    tab = tab,
                    isActive = tab.id == activeTabId,
                    thumbnail = thumbnails[tab.id],
                    onSelect = { onSelectTab(tab.id) },
                    onClose = { onCloseTab(tab.id) }
                )
            }
            item { NewTabCard(onClick = onNewTab) }
        }
    }
}