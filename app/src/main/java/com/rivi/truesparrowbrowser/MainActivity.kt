package com.rivi.truesparrowbrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.rivi.truesparrowbrowser.ui.screens.BrowserScreen
import com.rivi.truesparrowbrowser.ui.theme.TrueSparrowBrowserTheme
import com.rivi.truesparrowbrowser.ui.viewmodels.BrowserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<BrowserViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrueSparrowBrowserTheme {
                BrowserScreen(viewModel)
            }
        }
    }
}
