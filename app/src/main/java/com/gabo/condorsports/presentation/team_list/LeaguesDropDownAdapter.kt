package com.gabo.condorsports.presentation.team_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.gabo.condorsports.R
import com.gabo.condorsports.data.model.League

class LeaguesDropDownAdapter(val context: Context, var leagues: List<League>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val viewHolder: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.league_item, parent, false)
            viewHolder = ItemHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ItemHolder
        }
        viewHolder.label.text = leagues[position].name

        return view
    }

    override fun getItem(position: Int): League {
        return leagues[position]
    }

    override fun getCount(): Int {
        return leagues.size
    }

    override fun getItemId(position: Int): Long {
        return leagues[position].id.toLong()
    }

    fun update(leagues: List<League>) {
        this.leagues = leagues
        notifyDataSetChanged()
    }

    fun getPosition(id: String):Int {
        return leagues.indexOfFirst { it.id == id }
    }

    inner class ItemHolder(item: View?) {
        val label: TextView = item?.findViewById(R.id.league_name) as TextView
    }

}