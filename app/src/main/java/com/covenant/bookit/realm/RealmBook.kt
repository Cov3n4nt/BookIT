package com.covenant.bookit.realm

import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class RealmBook: RealmObject {
    var id: ObjectId = ObjectId()
    var author: String? = null
    var publishDate: Long? = null
    var dateAdded: Long? = null
    var dateModified: Long? = null
    var favorite: Boolean = false
    var archived: Boolean = false
}