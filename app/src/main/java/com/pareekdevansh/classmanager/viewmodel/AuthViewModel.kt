package com.pareekdevansh.classmanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pareekdevansh.classmanager.model.User

class AuthViewModel :ViewModel() {

    private var auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
}