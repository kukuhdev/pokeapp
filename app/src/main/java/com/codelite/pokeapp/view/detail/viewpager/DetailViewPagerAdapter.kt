package com.codelite.pokeapp.view.detail.viewpager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codelite.pokeapp.view.detail.fragment.EvolutionsFragment
import com.codelite.pokeapp.view.detail.fragment.StatsFragment
import com.codelite.pokeapp.view.detail.response.DetailResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class DetailViewPagerAdapter(activity: AppCompatActivity, val detailData: DetailResponse, val itemsCount: Int) :
    FragmentStateAdapter(activity) {

    lateinit var gson: Gson

    var fragment = listOf(
        StatsFragment.newInstance("", 0),
        EvolutionsFragment.newInstance("", "")
    )

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        gson = GsonBuilder().serializeNulls().create()
        val data = gson.toJson(detailData)
        if(position == 0){
            return StatsFragment.newInstance(data, position)
        } else {
            return EvolutionsFragment.newInstance(data, position.toString())
        }
    }
}