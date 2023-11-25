package com.covenant.bookit.Model

import java.time.LocalDate

data class Books (
    val id: String = "",
    val title: String,
    val author: String,
    val pages: Int? = null,
    val pagesRead: Int = 0,
    val publishDate: LocalDate,
    val dateAdded: LocalDate,
    val dateModified: LocalDate,
    val favorite: Boolean = false,
    val archived: Boolean = false,
)