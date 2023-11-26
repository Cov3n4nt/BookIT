package com.covenant.bookit.ui.theme.screens.Favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.covenant.bookit.ui.theme.screens.AddBookDialog.AddBooksDialogStateChangeListener
import com.covenant.bookit.ui.theme.screens.BookLists.BooksListsScreen
import com.covenant.bookit.ui.theme.screens.BooksViewModel
import com.covenant.bookit.ui.theme.screens.EditDialog.EditBookDialogStateChangeListener
import com.covenant.bookit.ui.theme.screens.ViewBookDialog.ViewBookDialogStateChangeListener
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun Favorites(navigator: DestinationsNavigator) {
    val viewModel: BooksViewModel = viewModel()
    with(viewModel){
        val books by favoriteBooks.collectAsStateWithLifecycle()
        val searchQuery by searchQuery.collectAsStateWithLifecycle()
        val viewBookDialogState by viewBookDialogState.collectAsStateWithLifecycle()
        val editBookDialogState by editBookDialogState.collectAsStateWithLifecycle()
        FavoriteScreen(
            books = books,
            searchQuery = searchQuery,
            onSearchQueryChange = ::updateSearchQuery,
            navigator = navigator,
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