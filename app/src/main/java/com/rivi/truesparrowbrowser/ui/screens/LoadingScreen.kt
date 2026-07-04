package com.rivi.truesparrowbrowser.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.rivi.truesparrowbrowser.ui.theme.CardBackground
import com.rivi.truesparrowbrowser.ui.theme.SkeletonColor
import com.rivi.truesparrowbrowser.ui.theme.TextSecondary

/**
 * A composable that displays a loading state placeholder (Skeleton UI) while a web page is being fetched.
 *
 * It extracts the host name from the provided [url] to display a "Loading host..." message
 * alongside a progress indicator and skeleton content blocks.
 *
 * @param url The target URL being loaded, used to determine the host name displayed in the UI.
 * @param modifier The [Modifier] to be applied to the root layout of the loading screen.
 */
@Composable
fun LoadingScreen(url: String, modifier: Modifier = Modifier) {
    val host = remember(url) {
        runCatching { java.net.URI(url).host ?: url }.getOrDefault(url)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CardBackground)
            .padding(16.dp)
    ) {
        SkeletonLine(0.9f)
        Spacer(Modifier.height(10.dp))
        SkeletonLine(0.6f)
        Spacer(Modifier.height(10.dp))
        SkeletonLine(0.75f)
        Spacer(Modifier.height(16.dp))


        Box(
            Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(SkeletonColor)
        )

        Spacer(Modifier.height(48.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(strokeWidth = 3.dp, modifier = Modifier.size(36.dp))
            Spacer(Modifier.height(8.dp))
            Text(
                "Loading $host…",
                color = TextSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(24.dp))
        SkeletonLine(0.5f)
    }
}

@Composable
private fun SkeletonLine(widthFraction: Float) {
    Box(
        Modifier
            .fillMaxWidth(widthFraction)
            .height(14.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(SkeletonColor)
    )
}