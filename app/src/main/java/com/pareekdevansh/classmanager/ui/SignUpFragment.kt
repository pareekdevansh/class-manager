package com.pareekdevansh.classmanager.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.pareekdevansh.classmanager.R
import com.pareekdevansh.classmanager.databinding.FragmentSignUpBinding

private lateinit var _binding : FragmentSignUpBinding
private val binding get() = _binding
class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val _binding = LayoutInflater.from(context).inflate(R.layout.fragment_sign_up, container, false)


        return _binding.rootView

//        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }



}