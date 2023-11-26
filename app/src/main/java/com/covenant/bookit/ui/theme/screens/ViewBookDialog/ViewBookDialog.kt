package com.covenant.bookit.ui.theme.screens.ViewBookDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.covenant.bookit.ui.theme.screens.EditDialog.EditBookDialogStateChangeListener
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.IconButton
import com.thebrownfoxx.components.TextButton


@Composable
fun ViewBookDialog(
    navigator: DestinationsNavigator?,
    state: ViewBookDialogState,
    stateChangeListener: ViewBookDialogStateChangeListener,
    editBookDialogStateChangeListener: EditBookDialogStateChangeListener,
    modifier: Modifier = Modifier,
) {
    if (state is ViewBookDialogState.Visible) {

        AlertDialog(
            modifier = modifier,
            onDismissRequest = stateChangeListener.onHideViewBook,
            title = {
                Column {
                    Row( verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                        ) {
                        Column{
                            Text(text = state.title.replaceRange(15, state.title.length, "..."))
                            Text(text = "Date Added: ${state.dateAdded}", fontSize = 14.sp)
                            Text(text = "Date Modified: ${state.dateModified}", fontSize = 14.sp)
                        }
                        if(state.book.archived){
                            IconButton(
                                imageVector = Icons.Default.Restore,
                                contentDescription = "Restore",
                                onClick = {
                                    stateChangeListener.onHideViewBook()
                                    editBookDialogStateChangeListener.onRestore(state.book)
                                }
                            )
                        }else{
                            Box(){
                                Row {
                                    IconButton(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit",
                                        onClick = {
                                            stateChangeListener.onHideViewBook()
                                            editBookDialogStateChangeListener.initiateEdit(state.book)
                                        }
                                    )
                                    IconButton(
                                        imageVector = if(state.book.favorite)Icons.Default.Star else Icons.Default.StarBorder,
                                        contentDescription = "Favorite",
                                        onClick = {
                                            stateChangeListener.onHideViewBook()
                                            editBookDialogStateChangeListener.onFavorite(state.book)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextField(
                        label = { Text(text = "Pages Read") },
                        readOnly = state.book.archived,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.wrapContentSize(),
                        value = state.pagesRead.toString(),
                        onValueChange = stateChangeListener.onPagesReadChange,
                    )
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
                    onClick = stateChangeListener.onUpdatePagesRead,
                )
            }
        )
    }

}