package com.covenant.bookit.ui.theme.screens.Archives

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton

@Composable
fun DeleteAllDialog(
    state: DeleteDialogState,
    stateChangeListener: DeleteStateChangeListener,
    modifier: Modifier = Modifier,
) {
    if(state is DeleteDialogState.Visible){
        AlertDialog(
            onDismissRequest = {stateChangeListener.hideDeleteAll()},
            title = { Text(text = "Delete All")},
            text = { Text(text = "Are you sure you want to delete all archived books?")},

            dismissButton = {
                TextButton(
                    text = "No",
                    onClick = stateChangeListener.hideDeleteAll,
                )
            },
            confirmButton = {
                FilledButton(
                    text = "Yes",
                    onClick = {
                        stateChangeListener.onDeleteAll()
                        stateChangeListener.hideDeleteAll()
                    }
                )
            },
        )
    }
}