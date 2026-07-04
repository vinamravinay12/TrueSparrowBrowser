package com.rivi.truesparrowbrowser.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rivi.truesparrowbrowser.R
import com.rivi.truesparrowbrowser.ui.theme.CardBackground
import com.rivi.truesparrowbrowser.ui.theme.TextSecondary

/**
 * A full-screen composable that displays an error state when a page fails to load.
 *
 * This screen features an error icon, a descriptive message suggesting a connection check,
 * and a retry button to trigger a reload attempt.
 *
 * @param onRetry Callback invoked when the user clicks the "Retry" button.
 * @param modifier The [Modifier] to be applied to the root layout of this screen.
 */
@Composable
fun ErrorScreen(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CardBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.img_error_state),
            contentDescription = "page load error icon",
            modifier = Modifier.size(112.dp)
        )
        Spacer(Modifier.height(16.dp))
        Text("This page couldn't load", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(4.dp))
        Text(
            "Check your connection and try again.",
            style = MaterialTheme.typography.bodySmall, color = TextSecondary
        )
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("Retry")
        }
    }
}
