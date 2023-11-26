package com.covenant.bookit.ui.theme.screens.Archives

import com.covenant.bookit.Model.Books

sealed class RestoreDialogState(){
    data object Hide : RestoreDialogState()
    data object Visible : RestoreDialogState()
}

class RestoreStateChangeListener(
    val initiateRestoreAll: () -> Unit,
    val hideRestoreAll: () ->Unit,
    val onRestoreAll: ()-> Unit,
)