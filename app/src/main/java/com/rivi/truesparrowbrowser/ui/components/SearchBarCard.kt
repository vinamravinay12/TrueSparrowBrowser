package com.rivi.truesparrowbrowser.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rivi.truesparrowbrowser.R

@Composable
fun SearchBarCard(
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    canGoBack: Boolean,
    canGoForward: Boolean,
    hasUrl: Boolean,
    isLoading: Boolean,
    moveBack: () -> Unit,
    moveForward: () -> Unit,
    onSearch: (String) -> Unit,
    onReload: () -> Unit,
    onStop: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EAF0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { moveBack },
                enabled = canGoBack
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    tint = if (canGoBack) Color.Black else Color.LightGray,
                    contentDescription = "Move back"
                )
            }

            IconButton(
                onClick = { moveForward() },
                enabled = canGoForward
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_next),
                    tint = if (canGoForward) Color.Black else Color.LightGray,
                    contentDescription = "Move forward"
                )
            }


            BasicTextField(
                value = searchValue,
                onValueChange = onSearchValueChange,
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp
                ),
                cursorBrush = SolidColor(Color.Black),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(
                    onGo = { onSearch(searchValue) }
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(horizontal = 14.dp),
                decorationBox = { inner ->
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (searchValue.isEmpty()) {
                            Text("Search or type URL", color = Color.Gray, fontSize = 15.sp)
                        }
                        inner()
                    }
                }
            )


            IconButton(
                onClick = {
                    if (isLoading) {
                        onStop()
                    } else {
                        onReload()
                    }
                },
                enabled = hasUrl
            ) {
                Icon(
                    painter = painterResource(
                        if (isLoading) R.drawable.ic_stop else R.drawable.ic_reload
                    ),
                    tint = if (hasUrl) Color.Black else Color.LightGray,
                    contentDescription = if (isLoading) "Stop" else "Reload"
                )
            }
        }
    }
}