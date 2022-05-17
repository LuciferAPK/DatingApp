package com.example.csplusapp.fcm.fbimg

import android.net.Uri
import android.util.Log
import com.example.csplusapp.fragment.user.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class UploadInforToFB {
//    fun uploadImageToFirebaseStorage(uri: Uri) {
//        val filename = UUID.randomUUID().toString()
//        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
//
//        ref.putFile(uri)
//            .addOnSuccessListener {
//                Log.d("aducucvjp", "Successfully uploaded image: ${it.metadata?.path}")
//                ref.downloadUrl.addOnSuccessListener {
//                    Log.d("aducucvjp", "File Location: $it")
//                    saveUserToFirebaseDatabase(it)
//                }
//            }
//            .addOnFailureListener {
//                //error
//            }
//    }

//    private fun saveUserToFirebaseDatabase(imageUrl: Uri) {
//        val uid = FirebaseAuth.getInstance().uid ?: ""
//        val mail = FirebaseAuth.getInstance().currentUser?.email
//        val user = User(uid, " " ,mail.toString(), imageUrl.toString())
//        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
//        ref.setValue(user)
//            .addOnSuccessListener {
//                Log.d("aducucvjp", "Finally we saved the user to Firebase Database")
//            }
//    }
}