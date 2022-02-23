package com.pareekdevansh.classmanager.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pareekdevansh.classmanager.R
import com.pareekdevansh.classmanager.databinding.FragmentSignUpBinding

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


        binding.btnSignUp.setOnClickListener {
            val name = binding.etPersonName.text.toString().trim{ it <= ' '}
            val regNum = binding.etRegistrationNumber.text.toString().trim { it <= ' ' }
            val email = binding.etEmailAddress.text.toString().trim { it <= ' ' }
            val password = binding.etPassword.text.toString().trim { it <= ' ' }
            if(isFieldEmpty(name, regNum , email, password)){
                val msg = "Please Fill All The Required Field"
                showToast(msg)
            }
            else
            {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            showToast("SignUp : success")
                        }
                        else{
                            showToast("SignUp : failed")
                        }
                    }
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun isFieldEmpty(name: String, regNum: String, email: String, password: String): Boolean {
        return name.isEmpty() or regNum.isEmpty() or email.isEmpty() or password.isEmpty()
    }


}