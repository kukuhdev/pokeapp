package com.codelite.pokeapp.view.main.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codelite.pokeapp.R
import com.codelite.pokeapp.utils.RecentUtils
import com.codelite.pokeapp.view.detail.DetailActivity
import com.codelite.pokeapp.view.main.response.PokemonListResponse
import kotlinx.android.synthetic.main.item_pokemon.view.*

private const val TAG = "PokemonAdapter"
class PokemonAdapter(
    var context: Context,
    var items: List<PokemonListResponse.ResultsItem>
) : RecyclerView.Adapter<PokemonAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return ItemViewHolder(view)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = items[position]
        Log.e(TAG, "onBindViewHolder: ${RecentUtils.getImagePokemon(data.url!!)}")
        Glide.with(context)
            .load(RecentUtils.getImagePokemon(data.url!!))
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(holder.itemView.iv_pokemon)

        holder.itemView.tv_pokemon_name.text = "${data.name}"

        holder.itemView.lin_detail.setOnClickListener() {
            val firstString = data.url.replace("/v2","")
            val re = Regex("[^0-9]")
            val id = re.replace(firstString, "")
            Log.e(TAG, "onBindViewHolder: $id")

            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("image", RecentUtils.getImagePokemon(data.url!!))
            context.startActivity(intent)
        }
    }
}