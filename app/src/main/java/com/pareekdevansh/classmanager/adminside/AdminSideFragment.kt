package com.pareekdevansh.classmanager.adminside

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.pareekdevansh.classmanager.databinding.FragmentAdminSideBinding
import com.pareekdevansh.classmanager.model.Announcement
import com.pareekdevansh.classmanager.model.Lecture
import com.pareekdevansh.classmanager.repository.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

private lateinit var _binding : FragmentAdminSideBinding
private val binding get() = _binding
private lateinit var lectureCollectionReference: CollectionReference
class AdminSideFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        lectureCollectionReference = FirebaseFirestore.getInstance().collection("lectures")

        _binding = FragmentAdminSideBinding.inflate(layoutInflater, container, false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_admin_side, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddClass.setOnClickListener {
            val lecture : Lecture? = getCurrentLecture()
            if(lecture != null ){
                addLectureEntryToDatabase(lecture)
            }
            else{
                val msg = "Please Fill All The Details"
                showToast(msg)
            }
        }

        binding.btnMakeAnnoucement.setOnClickListener {
            val announcement : Announcement? = getAnnoucement()
            if(announcement != null ){
                addAnnoucementToDatabase(announcement)
            }
            else{
                val msg = "Please Fill All The Details"
                showToast(msg)
            }
        }
    }

    private fun addLectureEntryToDatabase(lecture: Lecture) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                lectureCollectionReference.add(lecture).await()
                withContext(Dispatchers.Main){
                    val msg = "Added Successfully"
                    showToast(msg)
                }
            }
            catch (e : Exception){
                withContext(Dispatchers.Main) {
                    showToast(e.message.toString())
                }
            }

        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg , Toast.LENGTH_SHORT).show()
    }

    private fun getCurrentLecture(): Lecture? {
        val courseName = binding.etCourseName.text.toString()
        val proffessorName = binding.etProffessorName.text.toString()
        val courseCode = binding.etCourseCode.text.toString()
        val day = binding.etDay.text.toString()
        val startAt = binding.etStartingTime.text.toString()
        val joiningLink = binding.etJoiningLink.text.toString()
        if(isAnyFieldEmpty(courseName,proffessorName, courseCode, day, startAt, joiningLink)) return null
        return Lecture(courseName, proffessorName, courseCode, day.toInt(), startAt, joiningLink)
    }

    private fun isAnyFieldEmpty(courseName: String, proffessorName: String, courseCode: String, day: String, startAt: String, joiningLink: String): Boolean {
    return courseName.isEmpty() or proffessorName.isEmpty() or courseCode.isEmpty() or day.isEmpty() or startAt.isEmpty() or joiningLink.isEmpty()
    }


    private fun addAnnoucementToDatabase(announcement: Announcement) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                AppRepository.annoucementCollectionReference.add(announcement).await()
                withContext(Dispatchers.Main){
                    val msg = "Annoucement Added"
                    showToast(msg)
                }
            }
            catch (e : Exception){
                withContext(Dispatchers.Main) {
                    showToast(e.message.toString())
                }
            }

        }
    }


    private fun getAnnoucement(): Announcement? {
        val date = binding.etAnnoucementDate.text.toString()
        val body = binding.etAnnoucement.text.toString()
        if(isAnyFieldEmpty2(date, body)) return null
        return Announcement(date , body)
    }

    private fun isAnyFieldEmpty2(date : String , body : String): Boolean {
        return date.isEmpty() || body.isEmpty()
    }


}