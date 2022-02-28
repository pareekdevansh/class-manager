package com.pareekdevansh.classmanager.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class AppRepository {

    companion object{

        var auth = Firebase.auth
        var calendar : Calendar = Calendar.getInstance()
        val userCollectoinReference = FirebaseFirestore.getInstance().collection("user")
        val lectureCollectoinReference = FirebaseFirestore.getInstance().collection("lectures")
        val currDay = calendar.get(Calendar.DAY_OF_WEEK)
        val annoucementCollectionReference = FirebaseFirestore.getInstance().collection("announcements")
    }

}