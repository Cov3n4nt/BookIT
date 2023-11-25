package com.covenant.bookit.ui.theme.screens.BookLists

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
                        value = state.title?: "",
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
                        value = state.author?:"",
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
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Text(
                            text = "Date Published: ${state.publishedDate?.toString()?: "Select a Date"}",
                            color = if(state.hasPublishedDateWarning)MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.inverseSurface
                        )
                        IconButton(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date Picker",
                            onClick = {stateChangeListener.onDatePickerShow()}
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
            onConfirm = {stateChangeListener.onPublishDateChange(it)}
        )
    }
}