package com.covenant.bookit.realm

import android.content.Context
import android.widget.Toast
import com.covenant.bookit.Model.Books
import com.covenant.bookit.Model.Sample.book
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
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
    fun getArchivedBooks(): Flow<List<RealmBook>> = realm.query<RealmBook>("favorite ==  $0 AND archived == $1",false,true).asFlow().map { it.list }
    fun getFavoriteBooks(): Flow<List<RealmBook>> = realm.query<RealmBook>("favorite ==  $0 AND archived == $1",true,false).asFlow().map { it.list }

    suspend fun restoreAll(){
        withContext(Dispatchers.IO){
            realm.write {
                val books = realm.query<RealmBook>("favorite ==  $0 AND archived == $1",false,true).find()
                for(book in books){
                    findLatest(book)?.archived = false
                }
            }
        }
    }

    suspend fun deleteAll(){
        withContext(Dispatchers.IO){
            realm.write {
                val books = realm.query<RealmBook>("favorite ==  $0 AND archived == $1",false,true).find()
                if(books.isNotEmpty()){
                    for(book in books){
                        delete(findLatest(book)!!)
                    }
                }
            }
        }

    }

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
                   this.dateModified = LocalDate.now().toEpochDay()
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

    suspend fun updatePagesRead(book: Books,pagesRead: Int){
        realm.write {
            val bookResult: RealmBook? = realm.query<RealmBook>("id == $0",
                org.mongodb.kbson.BsonObjectId(book.id)
            ).first().find()
            if(bookResult != null){
                val bookRealm = findLatest(bookResult)
                bookRealm?.apply {
                    this.pagesRead = pagesRead

                }
            }
            else{
                throw IllegalStateException("Book not found!")
            }
        }
    }

}


