package com.covenant.bookit.ui.theme.screens.BookLists

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.covenant.bookit.ui.theme.screens.AddBookDialog.AddBooksDialogStateChangeListener
import com.covenant.bookit.ui.theme.screens.BooksViewModel
import com.covenant.bookit.ui.theme.screens.EditDialog.EditBookDialogStateChangeListener
import com.covenant.bookit.ui.theme.screens.ViewBookDialog.ViewBookDialogStateChangeListener
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
        val editBookDialogState by editBookDialogState.collectAsStateWithLifecycle()
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
                onArchiveBook = ::archiveBook,
            ),
            viewBookDialogState = viewBookDialogState,
            viewBookDialogStateChangeListener = ViewBookDialogStateChangeListener(

                onPagesReadChange = ::updatePagesReadOnViewBook,
                onHideViewBook = ::hideViewBook,
                initiateView = ::initiateViewBook,
                onUpdatePagesRead = ::updatePagesRead,
            ),
            editBookDialogState = editBookDialogState,
            editBookDialogStateChangeListener = EditBookDialogStateChangeListener(
                onUpdate = ::updateBook,//
                onHideDatePicker = ::hideDatePickerOnViewBook, //
                onTitleChange = ::updateTitleOnViewBook, //
                onPagesChange = ::updatePagesOnViewBook, //
                onPublishDateChange = ::updateDatePublishedOnEdit,//
                onAuthorChange = ::updateAuthorOnViewBook,//
                onShowDatePicker = ::showDatePickerOnViewBook, //
                onHideEditBook = ::hideEdit,
                initiateEdit = ::initiateEdit,
                onRestore = ::archiveBook,
                onFavorite = ::favoriteBook,
            )
        )
    }

}