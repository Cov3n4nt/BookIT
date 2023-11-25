package com.covenant.bookit.ui.theme.screens.BookLists

import com.covenant.bookit.Model.Books
import java.time.LocalDate

sealed class ViewBookDialogState {
    data object Hidden : ViewBookDialogState()
    data class Visible(
        val book: Books,
        val title: String = book.title,
        val author: String = book.author,
        val pages: Int? = book.pages,
        val pagesRead: Int = book.pagesRead,
        val publishedDate: LocalDate = book.publishDate,
        val dateAdded: LocalDate = book.dateAdded,
        val dateModified: LocalDate = book.dateModified,
        val datePickerState: Boolean = false,
        val hasTitleWarning: Boolean = false,
        val hasAuthorWarning: Boolean = false,
        val hasPagesWarning: Boolean = false,
        val hasPublishedDateWarning: Boolean = false,
    ) : ViewBookDialogState()

}

class ViewBookDialogStateChangeListener(
    val initiateView: (Books)-> Unit,
    val onHideViewBook: ()-> Unit,
    val onUpdate:()-> Unit,
    val onTitleChange: (String) -> Unit,
    val onAuthorChange: (String) -> Unit,
    val onPagesChange: (String) -> Unit,
    val onPagesReadChange: (String) -> Unit,
    val onPublishDateChange: (String) -> Unit,
    val onShowDatePicker: () -> Unit,
    val onHideDatePicker: () -> Unit,
)