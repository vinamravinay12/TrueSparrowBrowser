package com.rivi.truesparrowbrowser.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rivi.truesparrowbrowser.data.models.BrowserTab
import com.rivi.truesparrowbrowser.ui.components.NewTabCard
import com.rivi.truesparrowbrowser.ui.components.TabCard

@Composable
fun TabsScreen(
    tabs: List<BrowserTab>,
    activeTabId: String,
    thumbnails: Map<String, Bitmap>,
    onSelectTab: (String) -> Unit,
    onNewTab: () -> Unit,
    onCloseTab: (String) -> Unit,
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
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onNewTab) {
                Icon(Icons.Default.Add, contentDescription = "New tab")
            }
            IconButton(onClick = { }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More options")
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