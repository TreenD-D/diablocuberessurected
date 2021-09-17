package com.achulkov.diablocuberessurected.data

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

interface DataRepo {
    fun getStorageReference(ref: String): StorageReference
    fun getFirebaseDbReference(): DatabaseReference
}