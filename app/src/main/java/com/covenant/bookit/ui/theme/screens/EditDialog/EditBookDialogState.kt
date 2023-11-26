package com.covenant.bookit.ui.theme.screens.EditDialog

import com.covenant.bookit.Model.Books
import java.time.LocalDate

sealed class EditBookDialogState {
    data object Hidden : EditBookDialogState()
    data class Visible(
        val book: Books,
        val title: String = book.title,
        val author: String = book.author,
        val pages: Int? = book.pages,
        val publishedDate: LocalDate = book.publishDate,
        val datePickerState: Boolean = false,
        val hasTitleWarning: Boolean = false,
        val hasAuthorWarning: Boolean = false,
        val hasPagesWarning: Boolean = false,
        val hasPublishedDateWarning: Boolean = false,
    ) : EditBookDialogState()

}

class EditBookDialogStateChangeListener(
    val onFavorite:(Books) ->Unit,
    val onRestore: (Books) ->Unit,
    val initiateEdit: (Books) -> Unit,
    val onHideEditBook: () -> Unit,
    val onUpdate:() -> Unit,
    val onTitleChange: (String) -> Unit,
    val onAuthorChange: (String) -> Unit,
    val onPagesChange: (String) -> Unit,
    val onPublishDateChange: (String) -> Unit,
    val onShowDatePicker: () -> Unit,
    val onHideDatePicker: () -> Unit,
)