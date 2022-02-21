package com.pareekdevansh.classmanager.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.rpc.context.AttributeContext
import com.pareekdevansh.classmanager.R
import com.pareekdevansh.classmanager.databinding.ActivityMainBinding
import com.pareekdevansh.classmanager.databinding.FragmentLoginBinding
import com.pareekdevansh.classmanager.model.Person
import com.pareekdevansh.classmanager.ui.LoginFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private val personCollectionRef = Firebase.firestore.collection("persons")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // open login fragment
        val loginFragment = LoginFragment()
        val signUpFragment = SignUpFragment()
        supportFragmentManager.beginTransaction().replace(R.id.flFragmentContainer, loginFragment)
            .commit()
    }
}