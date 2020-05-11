package com.gabo.condorsports.presentation.team_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gabo.condorsports.databinding.TeamItemBinding
import com.gabo.condorsports.data.model.Team

class TeamRecyclerViewAdapter(private var teams : List<Team>, private val itemClickListener: OnListInteractionListener ) :RecyclerView.Adapter<TeamRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Team
            itemClickListener.onListItemSelected(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TeamItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val team = teams[position]
        holder.name.text = team.name
        holder.stadiumName.text = team.stadium
        with(holder.itemView){
            tag = team
            setOnClickListener(onClickListener)
        }
        Glide.with(holder.itemView)
            .load(team.urlBadgeImage)
            .thumbnail(0.1f)
            .into(holder.badge)
    }

    fun updateTeams(teams: List<Team>){
        this.teams = teams
        notifyDataSetChanged()
    }



    inner class ViewHolder(binding: TeamItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val badge: ImageView = binding.teamBadge
        val name : TextView = binding.teamName
        val stadiumName : TextView = binding.teamStadiumName
    }

    interface OnListInteractionListener {
        fun onListItemSelected(item: Team)
    }

}