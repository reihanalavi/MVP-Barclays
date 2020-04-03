package com.reihanalavi.mvpbarclays.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reihanalavi.mvpbarclays.R
import com.reihanalavi.mvpbarclays.models.Teams
import kotlinx.android.synthetic.main.items_teams.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class TeamsAdapter(val context: Context, val listener: (Teams) -> Unit) :
    RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {

        @SuppressLint("SetTextI18n")
        fun bindData(data: Teams, listener: (Teams) -> Unit) {

            itemView.items_teams_name.text = "${data.teamShort} - ${data.teamLong}"
            itemView.items_teams_stadium.text = data.teamStadium

            itemView.onClick {
                listener(data)
            }

        }

    }

    var teams: MutableList<Teams> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.items_teams, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(teams[position], listener)
    }

}