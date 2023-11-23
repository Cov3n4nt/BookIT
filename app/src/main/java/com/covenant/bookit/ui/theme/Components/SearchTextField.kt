package com.covenant.bookit.ui.theme.Components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.covenant.bookit.ui.theme.BookITTheme

@Composable
fun SearchTextField(
    search: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Box(modifier = modifier
            .clip(CircleShape)
        ){
            TextField(
                value = search,
                onValueChange = onValueChange,
                placeholder = { Text(
                    text = "Search",
                )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Preview
@Composable
fun SearchTextFieldPreview() {
    BookITTheme {
        SearchTextField(search = "", onValueChange = {})
    }
}