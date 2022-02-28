package com.pareekdevansh.classmanager.ui

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.pareekdevansh.classmanager.R
import com.pareekdevansh.classmanager.adapter.AnnoucementsAdapter
import com.pareekdevansh.classmanager.databinding.FragmentAnnoucementBinding
import com.pareekdevansh.classmanager.model.Announcement
import com.pareekdevansh.classmanager.repository.AppRepository
import com.pareekdevansh.classmanager.ui.AnnoucementsFragment.Companion.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext



private lateinit var _binding: FragmentAnnoucementBinding
private val binding get() = _binding

class AnnoucementsFragment : Fragment() {

    companion object {
        const val TAG = "#AnnoucementFragment"
    }

    var annoucements = mutableListOf<Announcement>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAnnoucementBinding.inflate(layoutInflater, container, false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_annoucement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            showAnnoucements(annoucements)

        }

    }


    private suspend fun showAnnoucements(annoucements: MutableList<Announcement>) {
        val annoucementQuery = AppRepository.annoucementCollectionReference
            .orderBy("date")
            .get().await()
        try {
            getAnnoucementsList(annoucementQuery, annoucements)
            withContext(Dispatchers.Main) {
                // update the UI according to the lecture list
                updateUI(annoucements)
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

private suspend fun getAnnoucementsList(
    lectureQuery: QuerySnapshot?,
    annoucements: MutableList<Announcement>
) {
    lectureQuery?.let {
        for (document in lectureQuery.documents) {
            val annoucement = document.toObject<Announcement>()
            annoucement?.let {
                annoucements.add(it)
                Log.d(
                    TAG,
                    "\nAdded $annoucement\nList is empty -> ${annoucements.isEmpty()}\n$annoucements[0]"
                )
            }

        }
    }
}

private suspend fun updateUI(annoucements: MutableList<Announcement>) {
    Log.d("StrangeTag", "started updating UI")

    // loading

    if (annoucements.isEmpty()) {
        // show no classes today
        binding.tvNoAnnoucements.visibility = View.VISIBLE


    } else {
        // apply recycler view
        binding.tvNoAnnoucements.visibility = View.GONE

        binding.rvAnnoucements.apply {
            adapter = AnnoucementsAdapter(annoucements, context)
            layoutManager = LinearLayoutManager(context)
            Log.d(TAG, "setting up the RV")
        }
    }
    Log.d(TAG, "UI Updated Successfully")

}


