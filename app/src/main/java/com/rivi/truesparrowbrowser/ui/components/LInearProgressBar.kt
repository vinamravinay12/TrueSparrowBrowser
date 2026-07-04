package com.rivi.truesparrowbrowser.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A composable function that displays a linear progress bar.
 *
 * The progress bar is only visible when the [loading] state is true, showing
 * the advancement based on the [currentProgress] value.
 *
 * @param currentProgress The current progress value, ranging from 0.0 to 1.0.
 * @param loading A boolean flag that determines whether the progress indicator should be displayed.
 */
@Composable
fun LinearProgressBar(currentProgress: Float, loading: Boolean) {

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (loading) {
            LinearProgressIndicator(
                progress = { currentProgress },
                modifier = Modifier.fillMaxWidth(),
            )
        }


    }
}

