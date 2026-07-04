package com.rivi.truesparrowbrowser.core.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.webkit.WebView

fun WebView.captureThumbnail(targetWidth: Int = 400): Bitmap? {
    if (width <= 0 || height <= 0) return null
    val full = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    draw(Canvas(full))
    val scale = targetWidth.toFloat() / width
    val thumb = Bitmap.createScaledBitmap(full, targetWidth, (height * scale).toInt(), true)
    if (thumb != full) full.recycle()
    return thumb
}