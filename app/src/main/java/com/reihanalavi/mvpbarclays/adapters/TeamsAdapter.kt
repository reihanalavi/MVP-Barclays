package com.reihanalavi.mvpbarclays.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.reihanalavi.mvpbarclays.R
import com.reihanalavi.mvpbarclays.databinding.ItemsTeamsBinding
import com.reihanalavi.mvpbarclays.models.Teams
import org.jetbrains.anko.sdk25.coroutines.onClick

class TeamsAdapter(private val context: Context, private var teams: List<Teams>, private val listener: (Teams) -> Unit) :
    RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {

    class ViewHolder(private val itemViews: ItemsTeamsBinding): RecyclerView.ViewHolder(itemViews.root) {

        @SuppressLint("SetTextI18n")
        fun bindData(data: Teams, listener: (Teams) -> Unit) {
            itemViews.teams = data

            itemView.onClick {
                listener(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val views = DataBindingUtil.inflate<ItemsTeamsBinding>(inflater, R.layout.items_teams, parent, false)
        return ViewHolder(views)
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(teams[position], listener)
    }

}