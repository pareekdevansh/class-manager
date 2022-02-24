package com.pareekdevansh.classmanager.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.pareekdevansh.classmanager.R
import com.pareekdevansh.classmanager.adapter.TodaysLecturesAdapter
import com.pareekdevansh.classmanager.adapter.TotalLecturesAdapter
import com.pareekdevansh.classmanager.databinding.FragmentScheduleBinding
import com.pareekdevansh.classmanager.model.Lecture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

private val lectureCollectoinRef = FirebaseFirestore.getInstance().collection("lectures")

class ScheduleFragment : Fragment() {

    val calendar = Calendar.getInstance()

    private lateinit var _binding : FragmentScheduleBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScheduleBinding.inflate(layoutInflater, container, false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val totalClasses = mutableListOf<Lecture>()
        val currDay = calendar.get(Calendar.DAY_OF_WEEK)
        Log.d("StrangeTag", "Current day code -> $currDay")
            showTotalLectures(totalClasses, currDay)


        }

    private fun showTotalLectures(totalClasses: MutableList<Lecture>, currDay: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            val lectureQuery = lectureCollectoinRef.orderBy("day")
                .get().await()
            try {
                getTotalLecutreList(lectureQuery, totalClasses)
                withContext(Dispatchers.Main) {
                    // update the UI according to the lecture list
                    updateUI(totalClasses)
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

    private fun updateUI(totalClasses: MutableList<Lecture>) {

        Log.d("StrangeTag", "started updating UI")

        // loading circle should not be shown any more
//        binding.loadingCircle.apply {
//            loop(false)
//            visibility = View.GONE
//        }
        // check for no classes for current day
        if (totalClasses.isEmpty()) {
            // show no classes today
            Toast.makeText(requireContext(), "No Classes", Toast.LENGTH_SHORT).show()
        } else {
            // apply recycler view

            binding.rvTotalLectures.apply {
                adapter = TotalLecturesAdapter(totalClasses, calendar)
                layoutManager = LinearLayoutManager(requireContext())
                Log.d("StrangeTag", "setting up the RV")
            }
        }
        Log.d("StrangeTag", "UI Updated Successfully")
    }


}

    private fun getTotalLecutreList(lectureQuery: QuerySnapshot?, totalClasses: MutableList<Lecture>) {
        lectureQuery?.let {
            for (document in lectureQuery.documents) {
                val lecture = document.toObject<Lecture>()
                lecture?.let {
                    totalClasses.add(it)
                    Log.d(
                        "StrangeTag",
                        "\nAdded $lecture\nList is empty -> ${totalClasses.isEmpty()}\n$totalClasses[0]"
                    )
                }

            }
        }
    }

