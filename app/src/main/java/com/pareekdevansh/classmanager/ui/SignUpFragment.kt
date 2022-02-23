package com.pareekdevansh.classmanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pareekdevansh.classmanager.databinding.FragmentSignUpBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


// Todo: sign up with Google
// Todo: sign up with email and password


private lateinit var auth : FirebaseAuth
class SignUpFragment : Fragment() {

    private lateinit var _binding : FragmentSignUpBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = Firebase.auth
        _binding = FragmentSignUpBinding.inflate(layoutInflater, container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvGoToLogInScreen.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            findNavController().navigate(action)
        }


        // sign up button pressed
        binding.btnSignUp.setOnClickListener {

            //get all the entered details
            val name = binding.etPersonName.text.toString().trim{ it <= ' '}
            val regNum = binding.etRegistrationNumber.text.toString().trim { it <= ' ' }
            val email = binding.etEmailAddress.text.toString().trim { it <= ' ' }
            val password = binding.etPassword.text.toString().trim { it <= ' ' }

            // check if all the details are filled or not
            if(isAnyFieldEmpty(name , regNum , email , password)){
                // Incomplete Detals. Please enter all the details
                showToast("Please Fill All The Details")
            }
            else
            {
                // check if the email is already there in the database or not
                CoroutineScope(Dispatchers.IO).launch {
                    if(userAlreadyExists(email)){
                        withContext(Dispatchers.Main) {
                            showToast("User Already Registered")
                            val action =
                                SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                            findNavController().navigate(action)
                        }
                    }
                    else
                    {
                        // create new user
                        registerUser(email , password)
                    }
                }

            }

        }


    }

    private fun registerUser(email: String, password: String) {

        CoroutineScope(Dispatchers.IO).launch{
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                if(isLoggedIn()){
                    withContext(Dispatchers.Main) {
                        showToast("Registered Successfully")
                        val action = SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()
                        findNavController().navigate(action)
                    }
                }
            }
            catch (e : Exception){
                withContext(Dispatchers.Main){
                        showToast(e.message.toString())
                }
            }
        }
    }

    private fun isLoggedIn() : Boolean{
        return auth.currentUser == null
    }

    private suspend fun userAlreadyExists(email: String): Boolean {
        val userCollectionRef = Firebase.firestore.collection("user")
        val userQuery = userCollectionRef.whereEqualTo("email", email).get().await()
        return !(userQuery.isEmpty)
    }

    private fun isAnyFieldEmpty(name: String, regNum: String, email: String, password: String): Boolean {
        return name.isEmpty() or regNum.isEmpty() or email.isEmpty() or password.isEmpty()
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun isFieldEmpty(name: String, regNum: String, email: String, password: String): Boolean {
        return name.isEmpty() or regNum.isEmpty() or email.isEmpty() or password.isEmpty()
    }


}