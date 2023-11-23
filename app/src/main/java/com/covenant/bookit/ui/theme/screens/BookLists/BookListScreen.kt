package com.covenant.bookit.ui.theme.screens.BookLists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.covenant.bookit.ui.theme.BookITTheme
import com.covenant.bookit.ui.theme.Components.FabItem
import com.covenant.bookit.ui.theme.Components.MultiFloatingActionButton
import com.covenant.bookit.ui.theme.Components.SearchTextField
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun BooksListsScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    navigator: DestinationsNavigator?,

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
                        label = "Owner",
                        onFabItemClicked = {}
                    ),
                    FabItem(
                        icon = Icons.Default.Archive,
                        label = "Add Pet",
                        onFabItemClicked = {}
                    ),
                    FabItem(
                        icon = Icons.Default.Book,
                        label = "Add Pet",
                        onFabItemClicked = {}
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
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
            }
        }
    }


//    AddPetOwnerDialog(
//        state = addPetOwnerDialogState,
//        stateChangeListener = addPetOwnerDialogStateChangeListener,
//    )
//
//    AddPetDialog(
//        state = addPetDialogState,
//        stateChangeListener = addPetDialogStateChangeListener,
//        petTypes = petTypes,
//    )
//    EditPetDialog(
//        petTypes = petTypes,
//        state = editPetDialogState,
//        stateChangeListener = editPetDialogStateChangeListener
//    )
}

@Preview
@Composable
fun BookListsScreenPrev() {
    BookITTheme {
        BooksListsScreen(
            navigator = null,
            onSearchQueryChange = {},
            searchQuery = "")
    }
}