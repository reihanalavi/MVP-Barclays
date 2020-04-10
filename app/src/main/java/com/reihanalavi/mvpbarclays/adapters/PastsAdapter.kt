package com.reihanalavi.mvpbarclays.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.reihanalavi.mvpbarclays.R
import com.reihanalavi.mvpbarclays.databinding.ItemsPastBinding
import com.reihanalavi.mvpbarclays.models.Pasts
import org.jetbrains.anko.sdk25.coroutines.onClick

class PastsAdapter(
    private val context: Context,
    private var pasts: List<Pasts>,
    private val listener: (Pasts) -> Unit
) :
    RecyclerView.Adapter<PastsAdapter.ViewHolder>() {

    class ViewHolder(private val itemViews: ItemsPastBinding): RecyclerView.ViewHolder(itemViews.root) {

        fun bindData(data: Pasts, listener: (Pasts) -> Unit) {
            itemViews.pasts = data

            itemView.onClick {
                listener(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val views = DataBindingUtil.inflate<ItemsPastBinding>(inflater, R.layout.items_past, parent, false)
        return ViewHolder(views)
    }

    override fun getItemCount(): Int = pasts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(pasts[position], listener)
    }
}