package com.covenant.bookit.ui.theme.screens.Archives

import com.covenant.bookit.Model.Books

sealed class DeleteDialogState(){
    data object Hide : DeleteDialogState()
    data object Visible : DeleteDialogState()
}

class DeleteStateChangeListener(
    val onDelete: (Books)-> Unit,
    val initiateDeleteAll: () -> Unit,
    val hideDeleteAll: () ->Unit,
    val onDeleteAll: ()-> Unit,
)