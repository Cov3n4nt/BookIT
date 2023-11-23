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

class RealmDatabase {
    private val realm: Realm by lazy {
        val config = RealmConfiguration.Builder(
            schema = setOf(RealmBook::class)
        ).schemaVersion(1)
            .build()
        Realm.open(config)
    }

    fun getAllBooks(): Flow<List<RealmBook>> = realm.query<RealmBook>().asFlow().map { it.list }

    suspend fun addBook(
        author: String,
        publishDate: Long,
        dateAdded: Long,
        dateModified: Long,
    ) {
        withContext(Dispatchers.IO) {
            realm.write {
                RealmBook().apply {
                    this.author = author
                    this.publishDate = publishDate
                    this.dateAdded = dateAdded
                    this.dateModified = dateModified
                }
            }
        }
    }

    suspend fun deletePet(id: ObjectId) {
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
        author: String,
        publishDate: Long,
        dateAdded: Long,
        dateModified: Long,
    ) {
        withContext(Dispatchers.IO) {
            realm.write {
                val bookResult: RealmBook? = realm.query<RealmBook>("id == $0", ObjectId(book.id)).first().find()
                if(bookResult != null){
                    bookResult.apply {
                        this.author = author
                        this.publishDate = publishDate
                        this.dateAdded = dateAdded
                        this.dateModified = dateModified
                    }
                }else{
                    throw IllegalStateException("Book not found!")
                }
            }
        }
    }





}


