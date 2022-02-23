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

const val tag = "LoginFramgment"

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

        val currentUser = auth.currentUser
        binding.btnLogIn.setOnClickListener {

            val email = binding.etEmailAddress.text.toString().trim{it <= ' '}
            val password = binding.etPassword.text.toString().trim{it <= ' ' }
            if(isAnyFieldEmpty(email, password)){
                val toastMessage = "Please Fill All Required Details"
                showToast(toastMessage)
            }
            else
            {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(tag, "signInWithEmail:success")
                            val user = auth.currentUser
                            updateUi(user)

                        }
                        else{
                            val msg = "signInWithEmail:failed"
                            showToast(msg)
                            Log.d(tag, "signInWithEmail:failed")
                            updateUi(null)
                        }
                    }
            }
        }


        binding.tvForgotPassword.setOnClickListener {
            val actoin = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
            findNavController().navigate(actoin)
        }

        binding.tvGotoSignUpScreen.setOnClickListener{
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        // TODO : implement login using google account

    }

    private fun updateUi(user: FirebaseUser?) {
        TODO("Not yet implemented")
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