package com.covenant.bookit.ui.theme.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.covenant.bookit.Model.Books
import com.covenant.bookit.realm.RealmDatabase
import com.covenant.bookit.ui.theme.screens.BookLists.AddBooksDialogState
import com.covenant.bookit.ui.theme.screens.BookLists.ViewBookDialogState
import com.hamthelegend.enchantmentorder.extensions.combineToStateFlow
import com.hamthelegend.enchantmentorder.extensions.search
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId
import java.security.acl.Owner
import java.time.LocalDate

class BooksViewModel: ViewModel(){
    private val database = RealmDatabase()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun updateSearchQuery(newQuery: String) {
        _searchQuery.update { newQuery }
    }

    val books = combineToStateFlow(
        database.getAllBooks(),
        searchQuery,
        scope = viewModelScope,
        initialValue = emptyList(),
    ) { realmPets, searchQuery ->
        realmPets.map { realmBook ->
            Books(
                id = realmBook.id.toHexString(),
                author = realmBook.author!!,
                title = realmBook.title!!,
                pages = realmBook.pages,
                pagesRead = realmBook.pagesRead,
                publishDate = LocalDate.ofEpochDay(realmBook.publishDate!!),
                dateAdded = LocalDate.ofEpochDay(realmBook.dateAdded!!),
                dateModified = LocalDate.ofEpochDay(realmBook.dateModified!!),
                favorite = realmBook.favorite,
                archived = realmBook.archived,
            )
        }.search(searchQuery) {it.title}
    }


    private val _addBooksDialogState = MutableStateFlow<AddBooksDialogState>(AddBooksDialogState.Hidden)
    val addBooksDialogState = _addBooksDialogState.asStateFlow()

    private val _viewBookDialogState = MutableStateFlow<ViewBookDialogState>(ViewBookDialogState.Hidden)
    val viewBookDialogState = _viewBookDialogState.asStateFlow()


    fun hideAddBooksDialogState(){
        _addBooksDialogState.update { AddBooksDialogState.Hidden }
    }

    fun showAddBooksDialogState(){
        _addBooksDialogState.update { AddBooksDialogState.Visible() }
    }

    fun showDatePicker(){
        _addBooksDialogState.update {
            if(it is AddBooksDialogState.Visible){
                it.copy(
                    datePickerState = true
                )
            } else it
        }
    }

    fun hideDatePicker(){
        _addBooksDialogState.update {
            if(it is AddBooksDialogState.Visible){
                it.copy(
                    datePickerState = false
                )
            } else it
        }
    }

    fun updateTitle(title: String) {
        _addBooksDialogState.update {
            if (it is AddBooksDialogState.Visible) {
                it.copy(
                    title = title,
                    hasTitleWarning = false
                )
            } else it
        }
    }

    fun updateAuthor(author: String) {
        _addBooksDialogState.update {
            if (it is AddBooksDialogState.Visible) {
                it.copy(
                    author = author,
                    hasAuthorWarning = false
                )
            } else it
        }
    }

    fun updatePages(pages: String) {
        _addBooksDialogState.update {
            if (it is AddBooksDialogState.Visible) {
                val numberOfPages = when (pages) {
                    "" -> null
                    else -> pages.toIntOrNull() ?: it.pages
                }
                it.copy(
                    pages = numberOfPages,
                    hasPagesWarning = false,
                )
            } else it
        }
    }

    fun updateDatePublished(datePublished: LocalDate) {
        _addBooksDialogState.update {
            if (it is AddBooksDialogState.Visible) {
                it.copy(
                    publishedDate = datePublished,
                    hasPublishedDateWarning = false
                )
            } else it
        }
    }

    fun addBook(){
        var state = _addBooksDialogState.value
        with(state){
            if(this is AddBooksDialogState.Visible){
                if(author.isBlank() || title.isBlank() || publishedDate == null|| pages == null){
                    state = copy(
                        hasAuthorWarning = author.isBlank(),
                        hasTitleWarning = title.isBlank(),
                        hasPublishedDateWarning = publishedDate == null,
                        hasPagesWarning = pages == null,
                    )
                }
                else{
                    viewModelScope.launch {
                        database.addBook(
                            title = title,
                            author = author,
                            publishedDate = publishedDate,
                            pages = pages
                        )
                    }
                    state = AddBooksDialogState.Hidden
                }
                _addBooksDialogState.update { state }
            }
        }
    }

    fun deleteBook(book: Books){
        viewModelScope.launch {
            database.deleteBook(id = ObjectId(book.id))
        }
    }

    fun initiateViewBook(book:Books){
        _viewBookDialogState.update { ViewBookDialogState.Visible(book) }
    }

    fun hideViewBook(){
        _viewBookDialogState.update { ViewBookDialogState.Hidden }
    }


    fun showDatePickerOnViewBook(){
        _viewBookDialogState.update {
            if(it is ViewBookDialogState.Visible){
                it.copy(
                    datePickerState = true
                )
            } else it
        }
    }

    fun hideDatePickerOnViewBook(){
        _viewBookDialogState.update {
            if(it is ViewBookDialogState.Visible){
                it.copy(
                    datePickerState = false
                )
            } else it
        }
    }

    fun updateTitleOnViewBook(title: String) {
        _viewBookDialogState.update {
            if (it is ViewBookDialogState.Visible) {
                it.copy(
                    title = title,
                    hasTitleWarning = false
                )
            } else it
        }
    }

    fun updateAuthorOnViewBook(author: String) {
        _viewBookDialogState.update {
            if (it is ViewBookDialogState.Visible) {
                it.copy(
                    author = author,
                    hasAuthorWarning = false
                )
            } else it
        }
    }

    fun updatePagesOnViewBook(pages: String) {
        _viewBookDialogState.update {
            if (it is ViewBookDialogState.Visible) {
                val numberOfPages = when (pages) {
                    "" -> null
                    else -> pages.toIntOrNull() ?: it.pages
                }
                it.copy(
                    pages = numberOfPages,
                    hasPagesWarning = false,
                )
            } else it
        }
    }


    fun updatePagesReadOnViewBook(currentPage: String) {
        _viewBookDialogState.update {
            if (it is ViewBookDialogState.Visible) {
                val pagesRead = when (currentPage) {
                    "" -> 0
                    else -> currentPage.toIntOrNull() ?: it.pagesRead
                }
                it.copy(
                    pagesRead = pagesRead,
                    hasPagesWarning = false,
                )
            } else it
        }
    }

    fun updateDatePublishedOnViewBook(datePublished: LocalDate) {
        _viewBookDialogState.update {
            if (it is ViewBookDialogState.Visible) {
                it.copy(
                    publishedDate = datePublished,
                    hasPublishedDateWarning = false
                )
            } else it
        }
    }
    fun updateDatePublishedOnViewBookString(newDatePublished: String) {
        _viewBookDialogState.update {
            if (it is ViewBookDialogState.Visible) {
                val publishedDate = when (newDatePublished) {
                    "" -> null
                    else -> LocalDate.parse(newDatePublished) ?: it.publishedDate
                }
                it.copy(
                    publishedDate = publishedDate!!,
                    hasPagesWarning = false,
                )
            } else it
        }
    }

    fun updateBook(){
        var state = _viewBookDialogState.value
        with(state){
            if(this is ViewBookDialogState.Visible){
                if(author.isBlank() || title.isBlank() || pages == null){
                    state = copy(
                        hasAuthorWarning = author.isBlank(),
                        hasTitleWarning = title.isBlank(),
                        hasPagesWarning = pages == null,
                    )
                } else{
                    viewModelScope.launch {
                        database.updateBook(
                            book = book,
                            title = title,
                            author = author,
                            pages = pages,
                            pagesRead = pagesRead,
                            publishDate = publishedDate,
                        )
                    }
                    state = ViewBookDialogState.Hidden
                }
                _viewBookDialogState.update{state}
            }
        }
    }










}