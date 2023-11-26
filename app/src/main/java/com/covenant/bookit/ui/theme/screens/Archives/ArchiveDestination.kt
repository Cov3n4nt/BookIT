package com.covenant.bookit.ui.theme.screens.Archives

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.covenant.bookit.ui.theme.screens.BooksViewModel
import com.covenant.bookit.ui.theme.screens.EditDialog.EditBookDialogStateChangeListener
import com.covenant.bookit.ui.theme.screens.ViewBookDialog.ViewBookDialogStateChangeListener
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ArchiveDestination(navigator: DestinationsNavigator) {
    val viewModel: BooksViewModel = viewModel()
    with(viewModel){
        val archivedBooks by archivedBooks.collectAsStateWithLifecycle()
        val searchQuery by searchQuery.collectAsStateWithLifecycle()
        val viewBookDialogState by viewBookDialogState.collectAsStateWithLifecycle()
        val deleteDialogState by deleteDialogState.collectAsStateWithLifecycle()
        val restoreDialogState by restoreDialogState.collectAsStateWithLifecycle()
        ArchiveScreen(
            books = archivedBooks,
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
            ),
            deleteDialogsState = deleteDialogState,
            deleteStateChangeListener = DeleteStateChangeListener(
                onDeleteAll = ::deleteAll,
                onDelete = ::deleteBook,
                initiateDeleteAll = ::initiateDeleteAll,
                hideDeleteAll = ::hideDeleteAll,
            ),
            restoreDialogState = restoreDialogState,
            restoreStateChangeListener = RestoreStateChangeListener(
                onRestoreAll = ::restoreAll,
                initiateRestoreAll = ::initiateRestoreAll,
                hideRestoreAll = ::hideRestoreAll,
            )

        )
    }
}