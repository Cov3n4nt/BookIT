package com.covenant.bookit.Model

import java.time.LocalDate

data class Books (
    val id: String = "",
    val author: String? = null,
    val pages: Int? = null,
    val publishDate: Long? = null,
    val dateAdded: Long? = null,
    val dateModified: Long? = null,
    val favorite: Boolean = false,
    val archived: Boolean = false,
)