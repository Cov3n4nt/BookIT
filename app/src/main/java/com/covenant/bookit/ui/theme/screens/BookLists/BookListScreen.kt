package com.covenant.bookit.ui.theme.screens.BookLists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.covenant.bookit.Model.Books
import com.covenant.bookit.Model.Sample
import com.covenant.bookit.ui.theme.BookITTheme
import com.covenant.bookit.ui.theme.Components.BookCard
import com.covenant.bookit.ui.theme.Components.FabItem
import com.covenant.bookit.ui.theme.Components.MultiFloatingActionButton
import com.covenant.bookit.ui.theme.Components.SearchTextField
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
        bottomBar = {
            MultiFloatingActionButton(
                fabIcon = Icons.Default.Add,
                items = arrayListOf(
                    FabItem(
                        icon= Icons.Default.Star,
                        label = "Favorites",
                        onFabItemClicked = {}
                    ),
                    FabItem(
                        icon = Icons.Default.Archive,
                        label = "Archive",
                        onFabItemClicked = {}
                    ),
                    FabItem(
                        icon = Icons.Default.Book,
                        label = "Add Book",
                        onFabItemClicked = addBooksDialogStateChangeListener.onShowAddBookDialog
                    ),
                )
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ){
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
                        onRemove = {addBooksDialogStateChangeListener.onDeleteBook(book)},
                        onView = {viewBookDialogStateChangeListener.initiateView(book)},
                    )
                }
            }
        }
    }

    AddBookDialog(
        state = addBooksDialogState,
        stateChangeListener = addBooksDialogStateChangeListener
    )

    ViewBookDialog(
        state = viewBookDialogState,
        stateChangeListener = viewBookDialogStateChangeListener
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
                onDeleteBook = {},
            ),
            viewBookDialogState = ViewBookDialogState.Hidden,
            viewBookDialogStateChangeListener = ViewBookDialogStateChangeListener(
                onShowDatePicker = {},
                onAuthorChange = {},
                onPublishDateChange = {},
                onPagesChange = {},
                onPagesReadChange = {},
                onTitleChange = {},
                onUpdate = {},
                onHideViewBook = {},
                onHideDatePicker = {},
                initiateView = {},
            )
        )
    }
}