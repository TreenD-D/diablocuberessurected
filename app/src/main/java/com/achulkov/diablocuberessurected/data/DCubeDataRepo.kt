package com.achulkov.diablocuberessurected.data

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DCubeDataRepo @Inject constructor(
    private val storage : FirebaseStorage,
    private val firebaseDb : FirebaseDatabase
) : DataRepo {


    /**
     * gets firebase storage reference
     * ref - full fb storage link to file
     */
    override fun getStorageReference(ref : String) : StorageReference {
        return storage.getReferenceFromUrl(ref)
    }

    /**
     * gets firebase DB reference
     */
    override fun getFirebaseDbReference() : DatabaseReference {
        return firebaseDb.reference
    }



}