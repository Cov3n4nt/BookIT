package com.covenant.bookit.ui.theme.screens.Archives

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.SettingsBackupRestore
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.covenant.bookit.Model.Books
import com.covenant.bookit.ui.theme.Components.BookCard
import com.covenant.bookit.ui.theme.Components.FabItem
import com.covenant.bookit.ui.theme.Components.MultiFloatingActionButton
import com.covenant.bookit.ui.theme.Components.SearchTextField
import com.covenant.bookit.ui.theme.screens.EditDialog.EditBookDialogStateChangeListener
import com.covenant.bookit.ui.theme.screens.ViewBookDialog.ViewBookDialog
import com.covenant.bookit.ui.theme.screens.ViewBookDialog.ViewBookDialogState
import com.covenant.bookit.ui.theme.screens.ViewBookDialog.ViewBookDialogStateChangeListener
import com.covenant.bookit.ui.theme.screens.destinations.BookListsDestination
import com.covenant.bookit.ui.theme.screens.destinations.FavoritesDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.components.extension.plus

@Composable
fun ArchiveScreen(
    books: List<Books>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    navigator: DestinationsNavigator?,
    deleteDialogsState: DeleteDialogState,
    deleteStateChangeListener: DeleteStateChangeListener,
    restoreDialogState: RestoreDialogState,
    restoreStateChangeListener: RestoreStateChangeListener,
    viewBookDialogState: ViewBookDialogState,
    viewBookDialogStateChangeListener: ViewBookDialogStateChangeListener,
    editBookDialogStateChangeListener: EditBookDialogStateChangeListener,
) {
    Scaffold(
        topBar = {
            SearchTextField(
                search = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )
        },
    ) { contentPadding ->
            LazyColumn(
                contentPadding = contentPadding + PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(
                    items = books,
                    key = {book -> book.id}
                ) { book ->
                    BookCard(
                        book = book,
                        onRemove = {deleteStateChangeListener.onDelete(book)},
                        onView = {viewBookDialogStateChangeListener.initiateView(book)},
                    )
                }
            }
        val fabItems = if (books.isNotEmpty()) {
            listOf(
                FabItem(
                    icon = Icons.Default.DeleteSweep,
                    label = "Delete All",
                    onFabItemClicked =  deleteStateChangeListener.initiateDeleteAll
                ),
                FabItem(
                    icon = Icons.Default.SettingsBackupRestore,
                    label = "Restore All",
                    onFabItemClicked = restoreStateChangeListener.initiateRestoreAll
                )
            )
        } else {
            emptyList()
        }
        MultiFloatingActionButton(
            fabIcon = Icons.Default.Archive,
            items = arrayListOf(
                FabItem(
                    icon= Icons.Default.Star,
                    label = "Favorites",
                    onFabItemClicked = {navigator?.navigate(FavoritesDestination)}
                ),
                FabItem(
                    icon = Icons.Default.LibraryBooks,
                    label = "Books",
                    onFabItemClicked = {navigator?.navigate(BookListsDestination)}
                ),
            ) + fabItems
        )
    }


    ViewBookDialog(
        state = viewBookDialogState,
        stateChangeListener = viewBookDialogStateChangeListener,
        navigator = navigator,
        editBookDialogStateChangeListener = editBookDialogStateChangeListener
    )
    DeleteAllDialog(
        state = deleteDialogsState,
        stateChangeListener = deleteStateChangeListener
    )
    RestoreAllDialog(
        state = restoreDialogState,
        stateChangeListener = restoreStateChangeListener
    )
}