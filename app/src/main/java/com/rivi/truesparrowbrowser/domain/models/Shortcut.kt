package com.rivi.truesparrowbrowser.domain.models

import androidx.compose.ui.graphics.Color

data class Shortcut(
    val title: String,
    val subtitle: String,
    val url: String,
    val badge: String,
    val badgeColor: Color
)