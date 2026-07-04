package com.rivi.truesparrowbrowser.ui.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rivi.truesparrowbrowser.domain.models.Shortcut

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
                .background(Color(0xFF4285F4)),
            contentAlignment = Alignment.Center
        ) {
            Text("S", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(12.dp))
        Text("Sparrow Search", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(20.dp))


        HomeSearchField(onSearch = onSearch)

        Spacer(Modifier.height(28.dp))

        Text(
            "SHORTCUTS",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray
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
            fontSize = 12.sp,
            color = Color(0xFF4285F4),
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
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                Text(shortcut.badge, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(8.dp))
            Text(shortcut.title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Text(shortcut.subtitle, fontSize = 10.sp, color = Color.Gray)
        }
    }
}