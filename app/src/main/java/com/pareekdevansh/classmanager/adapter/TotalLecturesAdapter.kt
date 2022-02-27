package com.pareekdevansh.classmanager.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pareekdevansh.classmanager.R
import com.pareekdevansh.classmanager.model.Lecture
import java.text.SimpleDateFormat
import java.util.*

class TotalLecturesAdapter(
    private val lectures: MutableList<Lecture>,
    private val calendar: Calendar,
): RecyclerView.Adapter<TotalLecturesAdapter.LectureViewHolder>() {


    inner class LectureViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvCourseName: TextView = itemView.findViewById<TextView>(R.id.tvCourseName)
        val tvProfessorName: TextView = itemView.findViewById<TextView>(R.id.tvProfessorName)
        val tvCourseCode: TextView = itemView.findViewById<TextView>(R.id.tvCourseCode)
        val tvDay: TextView = itemView.findViewById<TextView>(R.id.tvDay)
        val tvStartingTime: TextView = itemView.findViewById<TextView>(R.id.tvStartingTime)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lecture, parent, false)
        return LectureViewHolder(view)
    }

    override fun onBindViewHolder(holder: LectureViewHolder, position: Int) {

        Log.d("StrangeTag", "Shuru to hua h")

        val lecture = lectures[position]
        holder.tvCourseName.text = lecture.courseName
        holder.tvProfessorName.text = lecture.professorName
        holder.tvCourseCode.text = lecture.courseCode
        holder.tvDay.text = getCurrentDay(lecture.day)

        holder.tvStartingTime.text = lecture.startingTime


    }



    override fun getItemCount() : Int = lectures.size

    private fun getCurrentDay(day: Int): String {
        return when(day){
            2 -> "Monday"
            3 -> "Tuesday"
            4-> "Wednesday"
            5 -> "Thursday"
            6 -> "Friday"
            7 -> "Saturday"
            else -> "Sunday"
        }
    }

}