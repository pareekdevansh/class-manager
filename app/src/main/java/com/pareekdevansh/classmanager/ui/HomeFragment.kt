package com.pareekdevansh.classmanager.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.pareekdevansh.classmanager.R
import com.pareekdevansh.classmanager.adapter.LecturesAdapter
import com.pareekdevansh.classmanager.databinding.FragmentHomeBinding
import com.pareekdevansh.classmanager.model.Lecture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

private lateinit var auth: FirebaseAuth
private var currentUser: FirebaseUser? = null
private val userCollectoinRef = FirebaseFirestore.getInstance().collection("user")
private val lectureCollectoinRef = FirebaseFirestore.getInstance().collection("lectures")
val calendar: Calendar = Calendar.getInstance()


class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = Firebase.auth

        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todaysClasses = mutableListOf<Lecture>()
        val currDay = calendar.get(Calendar.DAY_OF_WEEK)
        Log.d("StrangeTag", "Current day code -> $currDay")
        currentUser = auth.currentUser


        if (currentUser == null) {
            // user need to log in
            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            findNavController().navigate(action)
        } else {
            // user is already logged in

//            binding.tvUserName.text = currentUser!!.displayName.toString()
            binding.tvuserEmail.text = currentUser!!.email.toString()

            showTodaysLectures(todaysClasses, currDay)


        }

        val button = view.findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(requireContext(), "Loging Out", Toast.LENGTH_SHORT).show()
            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            findNavController().navigate(action)

        }

    }


    private fun showTodaysLectures(todaysClasses: MutableList<Lecture>, currDay: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val lectureQuery = lectureCollectoinRef.whereEqualTo("day", currDay).get().await()
            try {

                getTodaysLecutreList(lectureQuery, todaysClasses)

                withContext(Dispatchers.Main) {
                    // update the UI according to the lecture list
                    updateUI(todaysClasses)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        e.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


    }

    private suspend fun getTodaysLecutreList(
        lectureQuery: QuerySnapshot?,
        todaysClasses: MutableList<Lecture>
    ) {
        lectureQuery?.let {
            for (document in lectureQuery.documents) {
                val lecture = document.toObject<Lecture>()
                lecture?.let {
                    todaysClasses.add(it)
                    Log.d(
                        "StrangeTag",
                        "\nAdded $lecture\nList is empty -> ${todaysClasses.isEmpty()}\n$todaysClasses[0]"
                    )
                }

            }
        }
    }

    private suspend fun updateUI(todaysClasses: MutableList<Lecture>) {
        Log.d("StrangeTag", "started updating UI")

        // check for no classes for current day
        if (todaysClasses.isEmpty()) {
            // show no classes today
            Toast.makeText(requireContext(), "No Classes Today", Toast.LENGTH_SHORT).show()
        } else {
            // apply recycler view
            binding.rvLectures.apply {
                adapter = LecturesAdapter(todaysClasses, calendar, requireContext())
                layoutManager = LinearLayoutManager(requireContext())
                Log.d("StrangeTag", "setting up the RV")
            }
        }
        Log.d("StrangeTag", "UI Updated Successfully")
    }


}
