package com.rivi.truesparrowbrowser.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

// ---- App text styles ----
object AppTextStyle {
    val appBarTitle = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    val screenTitle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
    val homeTitle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp)
    val sectionLabel = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
    val cardTitle = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    val errorTitle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
    val badge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp)
    val addressText = TextStyle(fontSize = 16.sp)
    val body =
        TextStyle(fontSize = 12.sp)
    val caption =
        TextStyle(fontSize = 12.sp)
    val captionSmall = TextStyle(fontSize = 12.sp)
    val tiny = TextStyle(fontSize = 10.sp)
    val badgeSmall =
        TextStyle(fontSize = 10.sp)
}
