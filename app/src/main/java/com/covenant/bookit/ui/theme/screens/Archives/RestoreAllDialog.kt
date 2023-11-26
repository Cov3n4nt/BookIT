package com.covenant.bookit.ui.theme.screens.Archives

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton

@Composable
fun RestoreAllDialog(
    state: RestoreDialogState,
    stateChangeListener: RestoreStateChangeListener,
    modifier: Modifier = Modifier,
) {
    if(state is RestoreDialogState.Visible){
        AlertDialog(
            onDismissRequest = {stateChangeListener.hideRestoreAll()},
            title = { Text(text = "Unarchive All") },
            text = { Text(text = "Are you sure you want to unarchive all archived books?") },

            dismissButton = {
                TextButton(
                    text = "No",
                    onClick = stateChangeListener.hideRestoreAll,
                )
            },
            confirmButton = {
                FilledButton(
                    text = "Yes",
                    onClick = {
                        stateChangeListener.onRestoreAll()
                        stateChangeListener.hideRestoreAll()
                    }
                )
            },
        )
    }
}