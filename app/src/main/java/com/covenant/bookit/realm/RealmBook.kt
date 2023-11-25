package com.covenant.bookit.realm

import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class RealmBook: RealmObject {
    var id: ObjectId = ObjectId()
    var title: String? = null
    var author: String? = null
    var publishDate: Long? = null
    var pages: Int? = null
    var pagesRead: Int = 0
    var dateAdded: Long? = null
    var dateModified: Long? = null
    var favorite: Boolean = false
    var archived: Boolean = false
}