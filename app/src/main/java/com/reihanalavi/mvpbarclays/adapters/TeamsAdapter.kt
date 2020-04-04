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

class TeamsAdapter(private val context: Context, private var teams: List<Teams>, private val listener: (Teams) -> Unit) :
    RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {

        @SuppressLint("SetTextI18n")
        fun bindData(data: Teams, listener: (Teams) -> Unit) {

            itemView.items_teams_name.text = "${data.strTeamShort} - ${data.strTeam}"
            itemView.items_teams_stadium.text = data.strStadium

            itemView.onClick {
                listener(data)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.items_teams, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(teams[position], listener)
    }

}