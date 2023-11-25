package com.covenant.bookit.ui.theme.screens.BookLists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.covenant.bookit.ui.theme.Components.DatePickerAlertDialog
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.IconButton
import com.thebrownfoxx.components.TextButton
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun ViewBookDialog(
    state: ViewBookDialogState,
    stateChangeListener: ViewBookDialogStateChangeListener,
    modifier: Modifier = Modifier,
) {
    if (state is ViewBookDialogState.Visible) {

        AlertDialog(
            modifier = modifier,
            onDismissRequest = stateChangeListener.onHideViewBook,
            title = {
                Column {
                Text(text = "Details")
                    Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Date Added: ${state.dateAdded}", fontSize = 10.sp)
                        Text(text = "Date Modified: ${state.dateModified}", fontSize = 10.sp)
                    }
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    TextField(
                        label = { Text(text = "Title") },
                        value = state.title,
                        onValueChange = stateChangeListener.onTitleChange,
                        isError = state.hasTitleWarning,
                        trailingIcon = {
                            if (state.hasTitleWarning)
                                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
                        },
                    )

                    TextField(
                        label = { Text(text = "Author") },
                        modifier = Modifier.wrapContentWidth(),
                        value = state.author,
                        onValueChange = stateChangeListener.onAuthorChange,
                        isError = state.hasAuthorWarning,
                        trailingIcon = {
                            if (state.hasAuthorWarning)
                                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
                        },
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        TextField(
                            label = { Text(text = "No. of Pages") },
                            modifier = Modifier.wrapContentWidth()
                                .weight(3f),
                            value = state.pages?.toString()?: "",
                            onValueChange = stateChangeListener.onPagesChange,
                            isError = state.hasPagesWarning,
                            trailingIcon = {
                                if (state.hasPagesWarning)
                                    Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
                            },
                        )

                        TextField(
                            label = { Text(text = "Pages Read") },
                            modifier = Modifier.wrapContentWidth()
                                .weight(2f),
                            value = state.pagesRead.toString(),
                            onValueChange = stateChangeListener.onPagesReadChange,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextField(
                            label = { Text(text = "Date Published") },
                            modifier = Modifier.wrapContentWidth()
                                .weight(2f),
                            value = state.publishedDate.year.toString(),
                            onValueChange = stateChangeListener.onPublishDateChange,

                        )
                        IconButton(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date Picker",
                            onClick = {stateChangeListener.onShowDatePicker()},
                            modifier = Modifier
                                .weight(1f)
                        )
                    }

                }
            },
            dismissButton = {
                TextButton(
                    text = "Cancel",
                    onClick = stateChangeListener.onHideViewBook,
                )
            },
            confirmButton = {
                FilledButton(
                    text = "Update",
                    onClick = stateChangeListener.onUpdate,
                )
            }
        )

        DatePickerAlertDialog(
            visible = state.datePickerState,
            onDismissRequest =  stateChangeListener.onHideDatePicker ,
            onConfirm = {stateChangeListener.onPublishDateChange(it.toString())}
        )
    }
}