package com.covenant.bookit.ui.theme.screens.AddBookDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.covenant.bookit.ui.theme.Components.DatePickerAlertDialog
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.IconButton
import com.thebrownfoxx.components.TextButton
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookDialog(
    state: AddBooksDialogState,
    stateChangeListener: AddBooksDialogStateChangeListener,
    modifier: Modifier = Modifier,
) {
    if (state is AddBooksDialogState.Visible) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = stateChangeListener.onHideAddBookDialog,
            title = { Text(text = "Add Book") },
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

                    TextField(
                        label = { Text(text = "Number of Pages") },
                        modifier = Modifier.wrapContentWidth(),
                        value = state.pages?.toString()?: "",
                        onValueChange = stateChangeListener.onPagesChange,
                        isError = state.hasPagesWarning,
                        trailingIcon = {
                            if (state.hasPagesWarning)
                                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
                        },
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextField(
                            label = { Text(text = "Date Published") },
                            modifier = Modifier.wrapContentWidth()
                                .weight(2f),
                            readOnly = true,
                            value = if(state.publishedDate == null) LocalDate.now().toString() else state.publishedDate.toString(),
                            onValueChange = stateChangeListener.onPublishDateChange,
                            )
                        IconButton(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date Picker",
                            onClick = stateChangeListener.onDatePickerShow,
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                }
            },
            dismissButton = {
                TextButton(
                    text = "Cancel",
                    onClick = stateChangeListener.onHideAddBookDialog,
                )
            },
            confirmButton = {
                FilledButton(
                    text = "Add",
                    onClick = stateChangeListener.onAddBook,
                )
            }
        )

        DatePickerAlertDialog(
            visible = state.datePickerState,
            onDismissRequest =  stateChangeListener.onDatePickerHide ,
            onConfirm = {stateChangeListener.onPublishDateChange(it.toString())}
        )
    }
}