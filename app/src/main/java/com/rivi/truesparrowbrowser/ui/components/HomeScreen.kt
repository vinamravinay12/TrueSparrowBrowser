package com.rivi.truesparrowbrowser.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rivi.truesparrowbrowser.R
import com.rivi.truesparrowbrowser.domain.models.Shortcut
import com.rivi.truesparrowbrowser.ui.theme.AppTextStyle
import com.rivi.truesparrowbrowser.ui.theme.BrandBlue
import com.rivi.truesparrowbrowser.ui.theme.CardBackground
import com.rivi.truesparrowbrowser.ui.theme.OnBadge
import com.rivi.truesparrowbrowser.ui.theme.TextSecondary

/**
 * Represents the home screen of the browser, providing a search interface and quick-access shortcuts.
 *
 * This screen displays the app branding, a central search field, and a row of shortcut cards
 * that allow users to quickly navigate to predefined URLs or search terms.
 *
 * @param onSearch Callback invoked when a search is initiated or a shortcut is clicked,
 */
@Composable
fun HomeScreen(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    shortcuts: List<Shortcut>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))


        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(BrandBlue),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(Modifier.height(12.dp))
        Text("Sparrow Search", style = AppTextStyle.homeTitle)

        Spacer(Modifier.height(20.dp))


        HomeSearchField(onSearch = onSearch)

        Spacer(Modifier.height(28.dp))

        Text(
            "SHORTCUTS",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            style = AppTextStyle.sectionLabel,
            color = TextSecondary
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            shortcuts.forEach { shortcut ->
                ShortcutCard(
                    shortcut = shortcut,
                    modifier = Modifier.weight(1f),
                    onClick = { onSearch(shortcut.url) }
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Text(
            "Any URL or search term can be entered — the browser is not locked to a single site.",
            style = AppTextStyle.caption,
            color = BrandBlue,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
private fun ShortcutCard(
    shortcut: Shortcut,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(shortcut.badgeColor),
                contentAlignment = Alignment.Center
            ) {
                Text(shortcut.badge, color = OnBadge, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(8.dp))
            Text(shortcut.title, style = AppTextStyle.cardTitle)
            Text(shortcut.subtitle, style = AppTextStyle.tiny, color = TextSecondary)
        }
    }
}