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

class TodaysLecturesAdapter(
    private val lectures: MutableList<Lecture>,
    private val calendar: Calendar,
    private val context: Context
): RecyclerView.Adapter<TodaysLecturesAdapter.LectureViewHolder>() {


    inner class LectureViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvCourseName: TextView = itemView.findViewById<TextView>(R.id.tvCourseName)
        val tvProfessorName: TextView = itemView.findViewById<TextView>(R.id.tvProfessorName)
        val tvCourseCode: TextView = itemView.findViewById<TextView>(R.id.tvCourseCode)
        val tvDay: TextView = itemView.findViewById<TextView>(R.id.tvDay)
        val tvDate: TextView = itemView.findViewById<TextView>(R.id.tvDate)
        val tvStartingTime: TextView = itemView.findViewById<TextView>(R.id.tvStartingTime)
        val btnSetAlarm: Button = itemView.findViewById<Button>(R.id.btnSetAlarm)
        val btnJoinClass: Button = itemView.findViewById<Button>(R.id.btnJoinClass)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lecture_today, parent, false)
        return LectureViewHolder(view)
    }

    override fun onBindViewHolder(holder: LectureViewHolder, position: Int) {

        Log.d("StrangeTag", "Shuru to hua h")

        val lecture = lectures[position]
        holder.tvCourseName.text = lecture.courseName
        holder.tvProfessorName.text = lecture.professorName
        holder.tvCourseCode.text = lecture.courseCode
        holder.tvDay.text = getCurrentDay(lecture.day)

        val date = SimpleDateFormat("dd MMM yy").format(calendar.time).toString()
        holder.tvDate.text = date

        //debug
        Log.d("StrangeTag", holder.tvDate.text.toString())
        Log.d("StrangeTag","Implementing RecyclerView")
        holder.tvStartingTime.text = lecture.startingTime


        // TODO: when button set alarm is pressed
        holder.btnSetAlarm.setOnClickListener {
            Toast.makeText(context, "Alarm is set for ${lecture.startingTime}", Toast.LENGTH_LONG).show()
        }


        holder.btnJoinClass.setOnClickListener {
            Toast.makeText(context, "Joining Class", Toast.LENGTH_LONG).show()
            joinClass(lecture.link)
        }



    }

    private fun joinClass(link: String) {
        val uri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.data = uri
        context.startActivity(intent)
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