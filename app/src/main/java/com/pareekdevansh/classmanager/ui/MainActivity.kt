package com.pareekdevansh.classmanager.ui

import android.os.Bundle
import android.widget.Button


import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController

import com.pareekdevansh.classmanager.R
import kotlin.math.sign


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        val signUpFragment = SignUpFragment()
//        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, signUpFragment).commit()


        // if you want to navigate at some destination from main activity
        // first find yout fragment container view and tell android that you will use this as NavHostFragment
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        // find navController for this fragment container view
//        val navController = navHostFragment.findNavController()

        //define the action which you want to perform
//        val action = SignUpFragmentDirections.actionSignUpFragment3ToLoginFragment()

        // choose when the action will be perfomed ex. on click of button2

    }
}