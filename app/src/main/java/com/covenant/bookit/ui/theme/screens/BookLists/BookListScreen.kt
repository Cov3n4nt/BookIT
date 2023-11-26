package com.covenant.bookit.ui.theme.screens.BookLists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.covenant.bookit.Model.Books
import com.covenant.bookit.Model.Sample
import com.covenant.bookit.ui.theme.BookITTheme
import com.covenant.bookit.ui.theme.Components.BookCard
import com.covenant.bookit.ui.theme.Components.EmptyScreen
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
import com.covenant.bookit.ui.theme.screens.destinations.FavoritesDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.components.extension.plus

@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun BooksListsScreen(
    books: List<Books>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    navigator: DestinationsNavigator?,
    addBooksDialogState: AddBooksDialogState,
    addBooksDialogStateChangeListener: AddBooksDialogStateChangeListener,
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
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ){
            if(books.isEmpty()){
                EmptyScreen(
                    icon = Icons.Default.LibraryBooks,
                    text = "There are no books here",
                    modifier = Modifier.align(Alignment.Center)
                )
            }else{
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
                            onRemove = {addBooksDialogStateChangeListener.onArchiveBook(book)},
                            onView = {viewBookDialogStateChangeListener.initiateView(book)},
                        )
                    }
                }
            }
        }

        MultiFloatingActionButton(
            fabIcon = Icons.Default.LibraryBooks,
            items = arrayListOf(
                FabItem(
                    icon= Icons.Default.Star,
                    label = "Favorites",
                    onFabItemClicked = {navigator?.navigate(FavoritesDestination)}
                ),
                FabItem(
                    icon = Icons.Default.Archive,
                    label = "Archive",
                    onFabItemClicked = {navigator?.navigate(ArchiveDestinationDestination)}
                ),
                FabItem(
                    icon = Icons.Default.Book,
                    label = "Add Book",
                    onFabItemClicked = addBooksDialogStateChangeListener.onShowAddBookDialog
                ),
            )
        )
    }

    AddBookDialog(
        state = addBooksDialogState,
        stateChangeListener = addBooksDialogStateChangeListener
    )

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

@Preview
@Composable
fun BookListsScreenPrev() {
    BookITTheme {
        BooksListsScreen(
            books = Sample.books,
            navigator = null,
            onSearchQueryChange = {},
            searchQuery = "",
            addBooksDialogState = AddBooksDialogState.Hidden,
            addBooksDialogStateChangeListener = AddBooksDialogStateChangeListener(
                onAddBook = {},
                onAuthorChange = {},
                onHideAddBookDialog = {},
                onPagesChange = {},
                onPublishDateChange = {},
                onShowAddBookDialog = {},
                onTitleChange = {},
                onDatePickerHide = {},
                onDatePickerShow = {},
                onArchiveBook = {},
            ),
            viewBookDialogState = ViewBookDialogState.Hidden,
            viewBookDialogStateChangeListener = ViewBookDialogStateChangeListener(
                onPagesReadChange = {},
                onHideViewBook = {},
                initiateView = {},
                onUpdatePagesRead = {},
            ),
            editBookDialogState = EditBookDialogState.Hidden,
            editBookDialogStateChangeListener = EditBookDialogStateChangeListener(
                onUpdate = {},
                onHideDatePicker = {},
                onTitleChange = {},
                onPagesChange = {},
                onPublishDateChange = {},
                onAuthorChange = {},
                onShowDatePicker = {},
                onHideEditBook = {},
                initiateEdit = {},
                onRestore = {},
                onFavorite = {},
            )
        )
    }
}