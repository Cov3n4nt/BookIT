package com.covenant.bookit.ui.theme.screens.AddBookDialog

import com.covenant.bookit.Model.Books
import java.time.LocalDate

sealed class AddBooksDialogState {
    data object Hidden : AddBooksDialogState()
    data class Visible(
        val title: String = "",
        val author: String = "",
        val pages: Int? = null,
        val publishedDate: LocalDate? = null,
        val dateAdded: LocalDate? = null,
        val dateModified: LocalDate? = null,
        val datePickerState: Boolean = false,
        val hasTitleWarning: Boolean = false,
        val hasAuthorWarning: Boolean = false,
        val hasPagesWarning: Boolean = false,
        val hasPublishedDateWarning: Boolean = false,
    ) : AddBooksDialogState()

}

class AddBooksDialogStateChangeListener(
    val onArchiveBook: (Books) -> Unit,
    val onDatePickerShow: () -> Unit,
    val onDatePickerHide: () -> Unit,
    val onShowAddBookDialog: () -> Unit,
    val onHideAddBookDialog: () -> Unit,
    val onTitleChange: (String) -> Unit,
    val onAuthorChange: (String) -> Unit,
    val onPagesChange: (String) -> Unit,
    val onPublishDateChange: (String) -> Unit,
    val onAddBook: () -> Unit,
)