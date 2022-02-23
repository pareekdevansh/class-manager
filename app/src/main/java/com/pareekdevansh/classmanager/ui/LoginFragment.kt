package com.pareekdevansh.classmanager.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pareekdevansh.classmanager.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

const val tag = "LoginFragmment"

class LoginFragment : Fragment() {

    private  var _binding : FragmentLoginBinding? = null
    // this property is valid only b/w onCreateView to onDestroyView
    private val binding get() = _binding!!
    private lateinit var auth :FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = Firebase.auth
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // on pressing forgot password, navigate to forgot password fragment

        binding.btnLogIn.setOnClickListener {

            val email = binding.etEmailAddress.text.toString().trim{it <= ' '}
            val password = binding.etPassword.text.toString().trim{it <= ' ' }
            if(isAnyFieldEmpty(email, password)){
                val toastMessage = "Please Fill All Required Details"
                showToast(toastMessage)
            }
            else
            {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        auth.signInWithEmailAndPassword(email, password).await()
                        if(auth.currentUser != null ){
                            withContext(Dispatchers.Main){
                                showToast("Login Successful")
                                val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
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
        }

        // navigate to forgot password
        binding.tvForgotPassword.setOnClickListener {
            val actoin = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
            findNavController().navigate(actoin)
        }

        //navigate to sign up screen
        binding.tvGotoSignUpScreen.setOnClickListener{
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        // TODO : implement login using google account

    }



    private fun isAnyFieldEmpty(email : String , password : String ):Boolean {
        return email.isEmpty() or password.isEmpty()
    }

    private fun showToast(toastMessage: String) {
        Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}