package com.covenant.bookit.ui.theme.screens.BookLists

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.covenant.bookit.ui.theme.screens.BooksViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun BookLists(navigator: DestinationsNavigator) {
    val viewModel: BooksViewModel = viewModel()
    with(viewModel){
        val books by books.collectAsStateWithLifecycle()
        val searchQuery by searchQuery.collectAsStateWithLifecycle()
        val addBooksDialogState by addBooksDialogState.collectAsStateWithLifecycle()
        val viewBookDialogState by viewBookDialogState.collectAsStateWithLifecycle()
        BooksListsScreen(
            books = books,
            searchQuery = searchQuery,
            onSearchQueryChange = ::updateSearchQuery,
            navigator = navigator,
            addBooksDialogState = addBooksDialogState,
            addBooksDialogStateChangeListener = AddBooksDialogStateChangeListener(
                onTitleChange = ::updateTitle,
                onShowAddBookDialog = ::showAddBooksDialogState,
                onPagesChange = ::updatePages,
                onPublishDateChange = ::updateDatePublished,
                onHideAddBookDialog = ::hideAddBooksDialogState,
                onAuthorChange = ::updateAuthor,
                onAddBook = ::addBook,
                onDatePickerHide = ::hideDatePicker,
                onDatePickerShow = ::showDatePicker,
                onDeleteBook = ::deleteBook,
            ),
            viewBookDialogState = viewBookDialogState,
            viewBookDialogStateChangeListener = ViewBookDialogStateChangeListener(
                onShowDatePicker = ::showDatePickerOnViewBook,
                onAuthorChange = ::updateAuthorOnViewBook,
                onPublishDateChange = ::updateDatePublishedOnViewBookString,
                onPagesChange = ::updatePagesOnViewBook,
                onPagesReadChange = ::updatePagesReadOnViewBook,
                onTitleChange = ::updateTitleOnViewBook,
                onUpdate = ::updateBook,
                onHideViewBook = ::hideViewBook,
                onHideDatePicker = ::hideDatePickerOnViewBook,
                initiateView = ::initiateViewBook,
            )
        )
    }

}