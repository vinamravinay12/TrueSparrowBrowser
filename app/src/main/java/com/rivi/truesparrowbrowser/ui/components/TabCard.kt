package com.rivi.truesparrowbrowser.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rivi.truesparrowbrowser.data.models.BrowserTab
import com.rivi.truesparrowbrowser.ui.theme.AppTextStyle
import com.rivi.truesparrowbrowser.ui.theme.BrandBlue
import com.rivi.truesparrowbrowser.ui.theme.CardBackground
import com.rivi.truesparrowbrowser.ui.theme.OnBadge
import com.rivi.truesparrowbrowser.ui.theme.TextSecondary
import com.rivi.truesparrowbrowser.ui.theme.ThumbnailPlaceholder


/**
 * A composable that displays a visual representation of a browser tab in a card format.
 * Includes the tab's favicon/initial, URL or title, a close button, and a content preview.
 *
 * @param tab The [BrowserTab] data object containing information like title and URL.
 * @param isActive Boolean indicating if this tab is the currently selected active tab.
 * @param onSelect Callback invoked when the user clicks on the card to switch to this tab.
 * @param onClose Callback invoked when the user clicks the close icon to remove the tab.
 * @param thumbnail An optional [Bitmap] providing a visual preview of the tab's web content.
 */
@Composable
fun TabCard(
    tab: BrowserTab, isActive: Boolean,
    onSelect: () -> Unit,
    onClose: () -> Unit,
    thumbnail: Bitmap?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.72f)
            .clickable(onClick = onSelect),
        shape = RoundedCornerShape(12.dp),
        border = if (isActive) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(BrandBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        tab.title.firstOrNull()?.uppercase() ?: "T",
                        color = OnBadge, style = AppTextStyle.badgeSmall
                    )
                }
                Text(
                    text = tab.currentUrl.ifBlank { "New tab" },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 6.dp),
                    style = AppTextStyle.tiny, maxLines = 1,
                    overflow = TextOverflow.Ellipsis, color = TextSecondary
                )
                IconButton(onClick = onClose, modifier = Modifier.size(18.dp)) {
                    Icon(
                        Icons.Default.Close, contentDescription = "Close tab",
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(ThumbnailPlaceholder),
                contentAlignment = Alignment.Center
            ) {
                when {
                    tab.currentUrl.isNotBlank() && thumbnail != null ->
                        Image(
                            bitmap = thumbnail.asImageBitmap(),
                            contentDescription = "Tab page image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                    tab.currentUrl.isBlank() -> Text("Home", color = TextSecondary, style = AppTextStyle.caption)
                    else -> Text(tab.title, color = TextSecondary, style = AppTextStyle.captionSmall, maxLines = 1)
                }


            }


        }
    }
}