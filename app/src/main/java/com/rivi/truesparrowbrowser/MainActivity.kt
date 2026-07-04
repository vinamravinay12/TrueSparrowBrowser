package com.rivi.truesparrowbrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rivi.truesparrowbrowser.ui.components.BrowserScreen
import com.rivi.truesparrowbrowser.ui.theme.TrueSparrowBrowserTheme
import com.rivi.truesparrowbrowser.ui.viewmodels.BrowserViewModel

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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TrueSparrowBrowserTheme {
        Greeting("Android")
    }
}