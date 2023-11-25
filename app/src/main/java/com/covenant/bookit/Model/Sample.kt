package com.covenant.bookit.Model

import java.time.Instant
import java.time.LocalDate

object Sample {
    val book = Books(
        title = "The age of evils",
        author = "Jhaeros",
        pages = 200,
        pagesRead = 130,
        publishDate = LocalDate.now(),
        dateAdded = LocalDate.now(),
        dateModified = LocalDate.now(),
        favorite = false,
        archived = false
    )

    val books = listOf(
        Books(
            id = "asdasd",
            title = "The age of evils",
            author = "Jhaeros",
            pages = 200,
            pagesRead = 40,
            publishDate = LocalDate.now(),
            dateAdded = LocalDate.now(),
            dateModified = LocalDate.now(),
            favorite = false,
            archived = false,
        ),
        Books(
            id = "afafaf",
            title = "The Sinner",
            author = "Bob Ross",
            pages = 200,
            pagesRead = 190,
            publishDate = LocalDate.now(),
            dateAdded = LocalDate.now(),
            dateModified = LocalDate.now(),
            favorite = true,
            archived = false,
        )
    )
}