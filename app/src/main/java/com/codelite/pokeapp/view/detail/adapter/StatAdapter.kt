package com.codelite.pokeapp.view.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelite.pokeapp.R
import com.codelite.pokeapp.view.detail.response.StatsItem
import kotlinx.android.synthetic.main.item_stat.view.*

private const val TAG = "StatAdapter"
class StatAdapter(
    var context: Context,
    var items: List<StatsItem>
) : RecyclerView.Adapter<StatAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_stat, parent, false)
        return ItemViewHolder(view)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = items[position]

        holder.itemView.tv_stat_title.text = data.stat?.name
        holder.itemView.tv_stat_number.text = data.baseStat.toString()
        holder.itemView.pb_number.progress = data.baseStat!!
    }
}