package com.gabo.condorsports.presentation.team_details

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gabo.condorsports.data.model.Event
import com.gabo.condorsports.data.model.Team
import com.gabo.condorsports.databinding.EventItemBinding
import com.gabo.condorsports.databinding.TeamItemBinding
import com.gabo.condorsports.presentation.team_list.TeamRecyclerViewAdapter

class TeamEventRecyclerViewAdapter(private var events: List<Event>) : RecyclerView.Adapter<TeamEventRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamEventRecyclerViewAdapter.ViewHolder {
        val binding = EventItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: TeamEventRecyclerViewAdapter.ViewHolder, position: Int) {
        val event = events[position]
        holder.eventName.text = event.eventName
        with(holder.itemView){
            tag = event
        }
    }

    inner class ViewHolder(binding: EventItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val eventName : TextView = binding.eventName
    }

    fun update(events: List<Event>){
        this.events = events
        notifyDataSetChanged()
    }
}