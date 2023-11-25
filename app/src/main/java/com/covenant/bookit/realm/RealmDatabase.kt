package com.covenant.bookit.realm

import com.covenant.bookit.Model.Books
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId
import java.lang.IllegalStateException
import java.time.Instant
import java.time.LocalDate

class RealmDatabase {
    private val realm: Realm by lazy {
        val config = RealmConfiguration.Builder(
            schema = setOf(RealmBook::class)
        ).schemaVersion(1)
            .build()
        Realm.open(config)
    }
    fun getAllBooks(): Flow<List<RealmBook>> = realm.query<RealmBook>("favorite ==  $0 AND archived == $1",false,false).asFlow().map { it.list }


    suspend fun addBook(
        title: String,
        author: String,
        publishedDate: LocalDate,
        pages: Int,
    ) {
        withContext(Dispatchers.IO) {
            realm.write {
               val book = RealmBook().apply {
                    this.title = title
                    this.author = author
                    this.pages = pages
                    this.publishDate = publishedDate.toEpochDay()
                    this.dateAdded = LocalDate.now().toEpochDay()
                }
                copyToRealm(book)
            }
        }
    }

    suspend fun deleteBook(id: ObjectId) {
        withContext(Dispatchers.IO) {
            realm.write {
                query<RealmBook>("id == $0", id)
                    .first()
                    .find()
                    ?.let { delete(it) }
                    ?: throw IllegalStateException("Book not found!")
            }
        }
    }

    suspend fun favorite(book: Books){
        withContext(Dispatchers.IO){
            realm.write {
                val bookResult: RealmBook? = realm.query<RealmBook>("id == $0", ObjectId(book.id)).first().find()
                if (bookResult != null) {
                    val bookRealm = findLatest(bookResult)
                    bookRealm?.apply {
                        this.favorite = !book.favorite
                    }
                }
                else{
                    throw IllegalStateException("Book not found!")
                }
            }
        }
    }

    suspend fun archive(book: Books){
        withContext(Dispatchers.IO){
            realm.write {
                val bookResult: RealmBook? = realm.query<RealmBook>("id == $0", ObjectId(book.id)).first().find()
                if (bookResult != null) {
                    val bookRealm = findLatest(bookResult)
                    bookRealm?.apply {
                        this.archived = !book.archived
                    }
                }
                else{
                    throw IllegalStateException("Book not found!")
                }
            }
        }
    }

    suspend fun updateBook(
        book: Books,
        title: String,
        author: String,
        pages: Int,
        pagesRead: Int,
        publishDate: LocalDate,
    ) {
        withContext(Dispatchers.IO) {
            realm.write {
                val bookResult: RealmBook? = realm.query<RealmBook>("id == $0", ObjectId(book.id)).first().find()
                if(bookResult != null){
                    val bookRealm = findLatest(bookResult)
                    bookRealm?.apply {
                        this.title = title
                        this.author = author
                        this.pages = pages
                        this.pagesRead = pagesRead
                        this.dateModified = LocalDate.now().toEpochDay()
                        this.publishDate = publishDate.toEpochDay()
                    }
                }
                else{
                    throw IllegalStateException("Book not found!")
                }
            }
        }
    }

}


