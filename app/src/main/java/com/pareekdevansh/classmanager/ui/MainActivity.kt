package com.pareekdevansh.classmanager.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
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


//        binding.btnSubmit.setOnClickListener {
////            Toast.makeText(this, "Button Pressed", Toast.LENGTH_SHORT).show()
//
//            val name = binding.etPersonName.text.toString()
//            val regNo = binding.etRegistrationNumber.text.toString()
//            val email = binding.etEmailAddress.text.toString()
//            val mobileNo = binding.etMobileNumber.text.toString()
//
//            if (name.isEmpty() or mobileNo.isEmpty() or regNo.isEmpty() or email.isEmpty()) {
//                Toast.makeText(this, "Please Fill all the Asked Details", Toast.LENGTH_LONG).show()
//            } else {
//                val person = Person(name, regNo, email, mobileNo)
//                savePersons(person)
//            }
//        }
//
//
//        binding.btnUpdateData.setOnClickListener {
//            val oldPerson = getOldPerson()
//            val newPersonMap = getNewPersonMap()
//            updateQuery(oldPerson, newPersonMap)
//        }

//        subscribeToRealTimeUpdates()


    }
}

//    private fun updateQuery(oldPerson: Person, newPersonMap: Map<String, Any>) =
//        CoroutineScope(Dispatchers.IO).launch {
//            val personQuery = personCollectionRef
//                .whereEqualTo("name", oldPerson.name)
//                .whereEqualTo("registrationNumber", oldPerson.registrationNumber)
//                .whereEqualTo("email", oldPerson.email)
//                .whereEqualTo("mobileNumber", oldPerson.mobileNumber)
//                .get()
//                .await()
//
//            if (personQuery.isEmpty) {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(this@MainActivity, "No query matched", Toast.LENGTH_LONG).show()
//                }
//            } else {
//
//                for (document in personQuery) {
//                    try {
//                        personCollectionRef.document(document.id).set(
//                            newPersonMap,
//                            SetOptions.merge()
//                        ).await()
//                    } catch (e: Exception) {
//                        withContext(Dispatchers.Main) {
//                            Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
//                        }
//                    }
//                }
//            }
//
//
//        }
//
//}
//    private fun getNewPersonMap(): Map<String, Any> {
//        val map = mutableMapOf<String, Any>()
//        val name = binding.etNewPersonName.text.toString()
//        val regNo = binding.etNewRegistrationNumber.text.toString()
//        val email = binding.etNewEmailAddress.text.toString()
//        val mobNo = binding.etNewMobileNumber.text.toString()
//        if (name.isNotEmpty())
//            map["name"] = name
//        if (regNo.isNotEmpty())
//            map["registrationNumber"] = regNo
//        if (email.isNotEmpty())
//            map["email"] = email
//        if (mobNo.isNotEmpty())
//            map["mobileNumber"] = mobNo
//
//        return map
//    }
//
//    private fun getOldPerson(): Person {
//        return Person(
//            binding.etPersonName.text.toString(),
//            binding.etRegistrationNumber.text.toString(),
//            binding.etEmailAddress.text.toString(),
//            binding.etMobileNumber.text.toString()
//        )
//    }
//
//    private fun subscribeToRealTimeUpdates() {
//        personCollectionRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//            firebaseFirestoreException?.let {
//                Toast.makeText(
//                    this@MainActivity,
//                    firebaseFirestoreException.message,
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//            querySnapshot?.let {
//                val sb = StringBuilder()
//                for (document in querySnapshot.documents) {
//                    val person = document.toObject<Person>()
//                    sb.append("$person \n")
//
//                }
//                binding.tvDataBase.text = sb.toString()
//            }
//        }
//    }
//
//    private fun retrieveData() = CoroutineScope(Dispatchers.IO).launch {
//        try {
//            val querySnapshot = personCollectionRef.get().await()
//            val sb = StringBuilder()
//            for (document in querySnapshot.documents) {
//                val person = document.toObject<Person>()
//                sb.append("${person}\n")
//            }
//            withContext(Dispatchers.Main) {
//                binding.tvDataBase.text = sb.toString()
//            }
//
//        } catch (e: Exception) {
//            withContext(Dispatchers.Main) {
//                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
//            }
//        }
//    }
//
//    private fun savePersons(person: Person) = CoroutineScope(Dispatchers.IO).launch {
//        try {
//            personCollectionRef.add(person).await()
//            withContext(Dispatchers.Main) {
//                Toast.makeText(this@MainActivity, "Sign Up Successful", Toast.LENGTH_LONG)
//                    .show()
//            }
//        } catch (e: Exception) {
//            withContext(Dispatchers.Main) {
//                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
//            }
//        }
//    }
//}