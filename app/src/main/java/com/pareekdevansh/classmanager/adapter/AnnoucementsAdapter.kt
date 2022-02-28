package com.pareekdevansh.classmanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pareekdevansh.classmanager.R
import com.pareekdevansh.classmanager.model.Announcement
import java.util.*

class AnnoucementsAdapter(
    private val annoucements: MutableList<Announcement>,
    private val context: Context
) : RecyclerView.Adapter<AnnoucementsAdapter.AnnoucementViewHolder>() {


    inner class AnnoucementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //find xml refrence of all views
        val date: TextView = itemView.findViewById<TextView>(R.id.annoucementDate)
        val body: TextView = itemView.findViewById<TextView>(R.id.tvAnnoucementBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnoucementViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_annoucement, parent, false)
        return AnnoucementViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnnoucementViewHolder, position: Int) {

        val announcement = annoucements[position]
        holder.date.text = announcement.date
        holder.body.text = announcement.annoucementBody
    }

    override fun getItemCount(): Int = annoucements.size
}