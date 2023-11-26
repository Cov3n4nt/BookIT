package com.covenant.bookit.ui.theme.screens.Favorites

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
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.LibraryBooks
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
import com.covenant.bookit.ui.theme.screens.AddBookDialog.AddBookDialog
import com.covenant.bookit.ui.theme.screens.AddBookDialog.AddBooksDialogState
import com.covenant.bookit.ui.theme.screens.AddBookDialog.AddBooksDialogStateChangeListener
import com.covenant.bookit.ui.theme.screens.EditDialog.EditBookDialog
import com.covenant.bookit.ui.theme.screens.EditDialog.EditBookDialogState
import com.covenant.bookit.ui.theme.screens.EditDialog.EditBookDialogStateChangeListener
import com.covenant.bookit.ui.theme.screens.ViewBookDialog.ViewBookDialog
import com.covenant.bookit.ui.theme.screens.ViewBookDialog.ViewBookDialogState
import com.covenant.bookit.ui.theme.screens.ViewBookDialog.ViewBookDialogStateChangeListener
import com.covenant.bookit.ui.theme.screens.destinations.ArchiveDestinationDestination
import com.covenant.bookit.ui.theme.screens.destinations.BookListsDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.components.extension.plus

@Composable
fun FavoriteScreen(
    books: List<Books>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    navigator: DestinationsNavigator?,
    viewBookDialogState: ViewBookDialogState,
    viewBookDialogStateChangeListener: ViewBookDialogStateChangeListener,
    editBookDialogState: EditBookDialogState,
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
                        onRemove = {editBookDialogStateChangeListener.onFavorite(book)},
                        onView = {viewBookDialogStateChangeListener.initiateView(book)},
                    )
                }
            }
        MultiFloatingActionButton(
            fabIcon = Icons.Default.Star,
            items = arrayListOf(
                FabItem(
                    icon= Icons.Default.LibraryBooks,
                    label = "Books",
                    onFabItemClicked = {navigator?.navigate(BookListsDestination)}
                ),
                FabItem(
                    icon = Icons.Default.Archive,
                    label = "Archive",
                    onFabItemClicked = {navigator?.navigate(ArchiveDestinationDestination)}
                ),
            )
        )
    }
    ViewBookDialog(
        state = viewBookDialogState,
        stateChangeListener = viewBookDialogStateChangeListener,
        navigator = navigator,
        editBookDialogStateChangeListener = editBookDialogStateChangeListener
    )
    EditBookDialog(
        state = editBookDialogState,
        stateChangeListener = editBookDialogStateChangeListener
    )
}