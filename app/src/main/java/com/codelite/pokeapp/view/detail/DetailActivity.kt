package com.codelite.pokeapp.view.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.codelite.pokeapp.R
import com.codelite.pokeapp.api.ApiCallback
import com.codelite.pokeapp.databinding.ActivityDetailBinding
import com.codelite.pokeapp.utils.RecentUtils
import com.codelite.pokeapp.view.detail.response.DetailResponse
import com.codelite.pokeapp.view.detail.viewmodel.DetailRepository
import com.codelite.pokeapp.view.detail.viewmodel.DetailViewModel
import com.codelite.pokeapp.view.detail.viewpager.DetailViewPagerAdapter
import com.codelite.pokeapp.view.dialog.LoadingDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private const val TAG = "DetailActivity"
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var detailData: DetailResponse

    @Suppress("UNCHECKED_CAST")
    private val detailViewModel: DetailViewModel by viewModels {
        val repository = DetailRepository.getInstance()
        object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>) = DetailViewModel(
                repository
            ) as T
        }
    }

    var names:Array<String> = arrayOf("Stats","Evolutions")

    var pokemonId: String = ""
    var pokemonImage: String = ""

    private lateinit var mPager: ViewPager
    private lateinit var tabLayout : TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        initView()
    }

    private fun initView(){
        initLoadingDialog()

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            pokemonId = bundle.getString("id")!!
            pokemonImage = bundle.getString("image")!!
            Log.e(TAG, "initView: $pokemonId")

            getDetail()
        }
    }

    private fun initLoadingDialog() {
        loadingDialog = LoadingDialog.newInstance()
    }

    private fun showLoadingDialog() {
        loadingDialog.show(supportFragmentManager, loadingDialog.toString())
    }

    private fun hideLoadingDialog() {
        loadingDialog.dismiss()
    }

    private fun getDetail() {
        Log.e(TAG, "getDetail: execute")
        showLoadingDialog()

        detailViewModel.getDetail(pokemonId).observe(this) {
            when (it) {
                is ApiCallback.OnSuccess -> {
                    hideLoadingDialog()
                    processDetailData(it.data!!)
                }
                is ApiCallback.OnError -> {
                    hideLoadingDialog()
                    RecentUtils.dialogDefault(this, it.message)
                }
            }
        }
    }

    private fun processDetailData(detailResponse: DetailResponse){
        detailData = detailResponse

        Glide.with(this)
            .load(pokemonImage)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(binding.ivPokemon)
        binding.tvPokemonName.text = detailData.name
        if(detailData.types!!.isNotEmpty()) {
            binding.tvPokemonElemen.text = detailData.types?.get(0)?.type?.name
        }

        //viewpager
        binding.viewPager.adapter = DetailViewPagerAdapter(this, detailData, 2)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = names[position]
        }.attach()
    }
}

